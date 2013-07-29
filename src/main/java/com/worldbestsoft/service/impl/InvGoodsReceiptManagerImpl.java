package com.worldbestsoft.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.InvGoodsReceiptDao;
import com.worldbestsoft.dao.InvGoodsReceiptItemDao;
import com.worldbestsoft.dao.InvItemLevelDao;
import com.worldbestsoft.model.InvGoodsReceipt;
import com.worldbestsoft.model.InvGoodsReceiptItem;
import com.worldbestsoft.model.InvItemLevel;
import com.worldbestsoft.model.RefType;
import com.worldbestsoft.model.criteria.InvGoodsReceiptCriteria;
import com.worldbestsoft.service.DocumentNumberGenerator;
import com.worldbestsoft.service.DocumentNumberGeneratorException;
import com.worldbestsoft.service.InvGoodsReceiptManager;
import com.worldbestsoft.service.InvItemLevelChangedEvent;

@Service("invGoodsReceiptManager")
public class InvGoodsReceiptManagerImpl implements InvGoodsReceiptManager, ApplicationContextAware {
	private InvGoodsReceiptDao invGoodsReceiptDao;
	private InvGoodsReceiptItemDao invGoodsReceiptItemDao;
	private InvItemLevelDao invItemLevelDao;
	private DocumentNumberGenerator documentNumberGenerator;
	private ApplicationContext context;

	private String documentNumberFormat = "GR{0,number,00000}";

	public InvGoodsReceiptDao getInvGoodsReceiptDao() {
		return invGoodsReceiptDao;
	}

	@Autowired
	public void setInvGoodsReceiptDao(InvGoodsReceiptDao invGoodsReceiptDao) {
		this.invGoodsReceiptDao = invGoodsReceiptDao;
	}

	public InvGoodsReceiptItemDao getInvGoodsReceiptItemDao() {
		return invGoodsReceiptItemDao;
	}

	@Autowired
	public void setInvGoodsReceiptItemDao(InvGoodsReceiptItemDao invGoodsReceiptItemDao) {
		this.invGoodsReceiptItemDao = invGoodsReceiptItemDao;
	}

	public InvItemLevelDao getInvItemLevelDao() {
		return invItemLevelDao;
	}

	@Autowired
	public void setInvItemLevelDao(InvItemLevelDao invItemLevelDao) {
		this.invItemLevelDao = invItemLevelDao;
	}

	public DocumentNumberGenerator getDocumentNumberGenerator() {
		return documentNumberGenerator;
	}

	@Autowired
	public void setDocumentNumberGenerator(DocumentNumberGenerator documentNumberGenerator) {
		this.documentNumberGenerator = documentNumberGenerator;
	}

	public String getDocumentNumberFormat() {
		return documentNumberFormat;
	}

	public void setDocumentNumberFormat(String documentNumberFormat) {
		this.documentNumberFormat = documentNumberFormat;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.context = arg0;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.worldbestsoft.service.impl.InvGoodsReceiptManager#query(com.worldbestsoft
	 * .model.criteria.InvGoodsReceiptCriteria, int, int, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<InvGoodsReceipt> query(InvGoodsReceiptCriteria criteria, int page, int pageSize, String sortColumn, String order) {
		return invGoodsReceiptDao.query(criteria, page, pageSize, sortColumn, order);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.worldbestsoft.service.impl.InvGoodsReceiptManager#querySize(com.
	 * worldbestsoft.model.criteria.InvGoodsReceiptCriteria)
	 */
	@Override
	public Integer querySize(InvGoodsReceiptCriteria criteria) {
		return invGoodsReceiptDao.querySize(criteria);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.worldbestsoft.service.impl.InvGoodsReceiptManager#getAll()
	 */
	@Override
	public List<InvGoodsReceipt> getAll() {
		return invGoodsReceiptDao.getAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.worldbestsoft.service.impl.InvGoodsReceiptManager#get(java.lang.Long)
	 */
	@Override
	public InvGoodsReceipt get(Long id) {
		return invGoodsReceiptDao.get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.worldbestsoft.service.impl.InvGoodsReceiptManager#save(com.worldbestsoft
	 * .model.InvGoodReceipt)
	 */
	@Override
	public InvGoodsReceipt save(InvGoodsReceipt invGoodsReceipt, Collection<InvGoodsReceiptItem> newInvGoodsReceiptItemList) {
		// save to get key
		// newInvGoodsReceiptItemList to remove hibernate proxy, otherwise
		// invGoodsReceipt.getInvGoodsReceiptItems(); will query the database
		// again and remove the newly object
		// Set<InvGoodsReceiptItem> newInvGoodsReceiptItemList =
		// invGoodsReceipt.getInvGoodsReceiptItems();
		InvGoodsReceipt invGoodsReceiptSave = invGoodsReceiptDao.save(invGoodsReceipt);

		if (null != invGoodsReceipt.getId()) {
			List<InvGoodsReceiptItem> oldInvGoodsReceiptItemList = invGoodsReceiptItemDao.findByInvGoodReceipt(invGoodsReceipt.getId());
			// delete if not in the new list.
			for (InvGoodsReceiptItem invGoodsReceiptItem : oldInvGoodsReceiptItemList) {
				InvGoodsReceiptItem foundinvGoodsReceiptItem = (InvGoodsReceiptItem) CollectionUtils.find(newInvGoodsReceiptItemList, new BeanPropertyValueEqualsPredicate("invItem.code", invGoodsReceiptItem.getInvItem().getCode()));
				if (null == foundinvGoodsReceiptItem) {
					// delete
					invGoodsReceiptItemDao.remove(invGoodsReceiptItem.getId());
				}
			}
			// calculate cost
			BigDecimal totalCost = BigDecimal.ZERO;

			// add or update new list
			for (InvGoodsReceiptItem invGoodsReceiptItem : newInvGoodsReceiptItemList) {
				InvGoodsReceiptItem foundInvGoodsReceiptItem = (InvGoodsReceiptItem) CollectionUtils.find(oldInvGoodsReceiptItemList, new BeanPropertyValueEqualsPredicate("invItem.code", invGoodsReceiptItem.getInvItem().getCode()));
				if (null == foundInvGoodsReceiptItem) {
					invGoodsReceiptItem.setInvGoodsReceipt(invGoodsReceiptSave);
					invGoodsReceiptItemDao.save(invGoodsReceiptItem);
				} else {
					// update old object
					foundInvGoodsReceiptItem.setMemo(invGoodsReceiptItem.getMemo());
					foundInvGoodsReceiptItem.setQty(invGoodsReceiptItem.getQty());
					foundInvGoodsReceiptItem.setUnitPrice(invGoodsReceiptItem.getUnitPrice());
					foundInvGoodsReceiptItem.setUnitType(invGoodsReceiptItem.getUnitType());
					invGoodsReceiptItemDao.save(foundInvGoodsReceiptItem);
				}
				totalCost = totalCost.add(invGoodsReceiptItem.getQty().multiply(invGoodsReceiptItem.getUnitPrice()));
			}
			invGoodsReceiptSave.setTotalCost(totalCost);

			// update cost
			invGoodsReceiptSave = invGoodsReceiptDao.save(invGoodsReceiptSave);

		} else {
			// calculate cost
			BigDecimal totalCost = BigDecimal.ZERO;

			for (InvGoodsReceiptItem invGoodsReceiptItem : newInvGoodsReceiptItemList) {
				invGoodsReceiptItem.setInvGoodsReceipt(invGoodsReceiptSave);
				invGoodsReceiptItemDao.save(invGoodsReceiptItem);
				totalCost = totalCost.add(invGoodsReceiptItem.getQty().multiply(invGoodsReceiptItem.getUnitPrice()));
			}
			invGoodsReceiptSave.setTotalCost(totalCost);

			// update cost
			invGoodsReceiptSave = invGoodsReceiptDao.save(invGoodsReceiptSave);

		}
		return invGoodsReceiptSave;
	}

	@Override
	public InvGoodsReceipt saveToStock(InvGoodsReceipt invGoodsReceipt) {
		// get running no
		try {
			Long documentNumber = documentNumberGenerator.nextDocumentNumber(InvGoodsReceipt.class);
			String runningNo = MessageFormat.format(documentNumberFormat, documentNumber);
			invGoodsReceipt.setRunningNo(runningNo);
			invGoodsReceipt = invGoodsReceiptDao.save(invGoodsReceipt);

			List<InvGoodsReceiptItem> invGoodsReceiptItemList = invGoodsReceiptItemDao.findByInvGoodReceipt(invGoodsReceipt.getId());

			for (InvGoodsReceiptItem invGoodsReceiptItem : invGoodsReceiptItemList) {
				InvItemLevel invItemLevel = new InvItemLevel();
				invItemLevel.setInvItem(invGoodsReceiptItem.getInvItem());
				invItemLevel.setQtyInStock(invGoodsReceiptItem.getQty());
				invItemLevel.setTransactionDate(new Date());
				invItemLevel.setRefDocument(invGoodsReceipt.getRunningNo());
				invItemLevel.setRefType(RefType.GOOD_RECEIPT.getCode());

				invItemLevelDao.save(invItemLevel);

				// design to do one by one itemLevel, if not work will change to
				// per invGoodReeipt
				// by changing constructor and move out from loop
				// this should not effect current transaction, mean will not
				// rollback.
				InvItemLevelChangedEvent invItemLevelChangedEvent = new InvItemLevelChangedEvent(invItemLevel);
				context.publishEvent(invItemLevelChangedEvent);
			}
		} catch (DocumentNumberGeneratorException e) {
			// roll back transaction
			throw new RuntimeException(e);
		}
		return invGoodsReceipt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.worldbestsoft.service.impl.InvGoodsReceiptManager#remove(java.lang
	 * .Long)
	 */
	@Override
	public void remove(Long id) {
		InvGoodsReceipt invGoodsReceipt = invGoodsReceiptDao.get(id);
		if (null != invGoodsReceipt) {
			// delete child if exist
			for (InvGoodsReceiptItem invGoodsReceiptItem : invGoodsReceipt.getInvGoodsReceiptItems()) {
				invGoodsReceiptItemDao.remove(invGoodsReceiptItem.getId());
			}
		}
		invGoodsReceiptDao.remove(id);
	}

}
