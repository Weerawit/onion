package com.worldbestsoft.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.InvGoodsMovementDao;
import com.worldbestsoft.dao.InvGoodsMovementItemDao;
import com.worldbestsoft.dao.InvItemLevelDao;
import com.worldbestsoft.model.ConstantModel;
import com.worldbestsoft.model.InvGoodsMovement;
import com.worldbestsoft.model.InvGoodsMovementItem;
import com.worldbestsoft.model.InvItemLevel;
import com.worldbestsoft.model.criteria.InvGoodsMovementCriteria;
import com.worldbestsoft.service.DocumentNumberGenerator;
import com.worldbestsoft.service.DocumentNumberGeneratorException;
import com.worldbestsoft.service.InvGoodsMovementManager;
import com.worldbestsoft.service.InvItemLevelChangedEvent;

@Service("invGoodsMovementManager")
public class InvGoodsMovementManagerImpl implements ApplicationContextAware, InvGoodsMovementManager {
	private InvGoodsMovementDao invGoodsMovementDao;
	private InvGoodsMovementItemDao invGoodsMovementItemDao;
	
	private InvItemLevelDao invItemLevelDao;
	private DocumentNumberGenerator documentNumberGenerator;
	private ApplicationContext context;

	private String documentNumberFormat = "GM{0,number,00000}";


	public InvGoodsMovementDao getInvGoodsMovementDao() {
		return invGoodsMovementDao;
	}

	@Autowired
	public void setInvGoodsMovementDao(InvGoodsMovementDao invGoodsMovementDao) {
		this.invGoodsMovementDao = invGoodsMovementDao;
	}

	public InvGoodsMovementItemDao getInvGoodsMovementItemDao() {
		return invGoodsMovementItemDao;
	}

	@Autowired
	public void setInvGoodsMovementItemDao(InvGoodsMovementItemDao invGoodsMovementItemDao) {
		this.invGoodsMovementItemDao = invGoodsMovementItemDao;
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


	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvGoodsMovementManager#save(com.worldbestsoft.model.InvGoodsMovement, java.util.Collection)
	 */
	@Override
    public InvGoodsMovement save(InvGoodsMovement invGoodsMovement, Collection<InvGoodsMovementItem> newInvGoodsMovementItemList) {
		// save to get key
		// newInvGoodsMovementItemList to remove hibernate proxy, otherwise
		// invGoodsMovement.getInvGoodsMovementItems(); will query the database
		// again and remove the newly object
		// Set<InvGoodsMovementItem> newInvGoodsMovementItemList =
		// invGoodsMovement.getInvGoodsMovementItems();
		InvGoodsMovement invGoodsMovementSave = invGoodsMovementDao.save(invGoodsMovement);

		if (null != invGoodsMovement.getId()) {
			List<InvGoodsMovementItem> oldInvGoodsMovementItemList = invGoodsMovementItemDao.findByInvGoodMovement(invGoodsMovement.getId());
			// delete if not in the new list.
			for (InvGoodsMovementItem invGoodsMovementItem : oldInvGoodsMovementItemList) {
				InvGoodsMovementItem foundinvGoodsMovementItem = (InvGoodsMovementItem) CollectionUtils.find(newInvGoodsMovementItemList, new BeanPropertyValueEqualsPredicate("invItem.code", invGoodsMovementItem.getInvItem().getCode()));
				if (null == foundinvGoodsMovementItem) {
					// delete
					invGoodsMovementItemDao.remove(invGoodsMovementItem.getId());
				}
			}
			// add or update new list
			for (InvGoodsMovementItem invGoodsMovementItem : newInvGoodsMovementItemList) {
				InvGoodsMovementItem foundInvGoodsMovementItem = (InvGoodsMovementItem) CollectionUtils.find(oldInvGoodsMovementItemList, new BeanPropertyValueEqualsPredicate("invItem.code", invGoodsMovementItem.getInvItem().getCode()));
				if (null == foundInvGoodsMovementItem) {
					invGoodsMovementItem.setInvGoodsMovement(invGoodsMovementSave);
					invGoodsMovementItemDao.save(invGoodsMovementItem);
				} else {
					// update old object
					foundInvGoodsMovementItem.setMemo(invGoodsMovementItem.getMemo());
					foundInvGoodsMovementItem.setQty(invGoodsMovementItem.getQty());
					invGoodsMovementItemDao.save(foundInvGoodsMovementItem);
				}
			}

			// update cost
			invGoodsMovementSave = invGoodsMovementDao.save(invGoodsMovementSave);

		} else {
			for (InvGoodsMovementItem invGoodsMovementItem : newInvGoodsMovementItemList) {
				invGoodsMovementItem.setInvGoodsMovement(invGoodsMovementSave);
				invGoodsMovementItemDao.save(invGoodsMovementItem);
			}
			// update cost
			invGoodsMovementSave = invGoodsMovementDao.save(invGoodsMovementSave);

		}
		return invGoodsMovementSave;
	}

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvGoodsMovementManager#saveToStock(com.worldbestsoft.model.InvGoodsMovement)
	 */
	@Override
    public InvGoodsMovement saveToStock(InvGoodsMovement invGoodsMovement) {
		// get running no
		try {
			Long documentNumber = documentNumberGenerator.nextDocumentNumber(InvGoodsMovement.class);
			String runningNo = MessageFormat.format(documentNumberFormat, documentNumber);
			invGoodsMovement.setRunningNo(runningNo);
			invGoodsMovement = invGoodsMovementDao.save(invGoodsMovement);

			List<InvGoodsMovementItem> invGoodsMovementItemList = invGoodsMovementItemDao.findByInvGoodMovement(invGoodsMovement.getId());

			for (InvGoodsMovementItem invGoodsMovementItem : invGoodsMovementItemList) {
				InvItemLevel invItemLevel = new InvItemLevel();
				invItemLevel.setInvItem(invGoodsMovementItem.getInvItem());
				invItemLevel.setQtyInStock(invGoodsMovementItem.getQty().multiply(BigDecimal.valueOf(-1)));
				invItemLevel.setTransactionDate(new Date());
				invItemLevel.setRefDocument(invGoodsMovement.getRunningNo());
				invItemLevel.setRefType(ConstantModel.RefType.GOOD_MOVEMENT.getCode());

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
		return invGoodsMovement;
	}

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvGoodsMovementManager#remove(java.lang.Long)
	 */
	@Override
    public void remove(Long id) {
		InvGoodsMovement invGoodsMovement = invGoodsMovementDao.get(id);
		if (null != invGoodsMovement) {
			// delete child if exist
			for (InvGoodsMovementItem invGoodsMovementItem : invGoodsMovement.getInvGoodsMovementItems()) {
				invGoodsMovementItemDao.remove(invGoodsMovementItem.getId());
			}
		}
		invGoodsMovementDao.remove(id);
	}

	@Override
    public List<InvGoodsMovement> query(InvGoodsMovementCriteria criteria, int page, int pageSize, String sortColumn, String order) {
	    return invGoodsMovementDao.query(criteria, page, pageSize, sortColumn, order);
    }

	@Override
    public Integer querySize(InvGoodsMovementCriteria criteria) {
	    return invGoodsMovementDao.querySize(criteria);
    }

	@Override
    public InvGoodsMovement get(Long id) {
	    return invGoodsMovementDao.get(id);
    }

	
	
}
