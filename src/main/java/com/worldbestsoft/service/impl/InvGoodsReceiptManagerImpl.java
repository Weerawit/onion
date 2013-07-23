package com.worldbestsoft.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.InvGoodsReceiptDao;
import com.worldbestsoft.dao.InvGoodsReceiptItemDao;
import com.worldbestsoft.model.InvGoodReceipt;
import com.worldbestsoft.model.InvGoodReceiptItem;
import com.worldbestsoft.model.criteria.InvGoodsReceiptCriteria;
import com.worldbestsoft.service.InvGoodsReceiptManager;

@Service("invGoodsReceiptManager")
public class InvGoodsReceiptManagerImpl implements InvGoodsReceiptManager {
	private InvGoodsReceiptDao invGoodsReceiptDao;
	private InvGoodsReceiptItemDao invGoodsReceiptItemDao;

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

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvGoodsReceiptManager#query(com.worldbestsoft.model.criteria.InvGoodsReceiptCriteria, int, int, java.lang.String, java.lang.String)
	 */
	@Override
    public List<InvGoodReceipt> query(InvGoodsReceiptCriteria criteria, int page, int pageSize, String sortColumn, String order) {
	    return invGoodsReceiptDao.query(criteria, page, pageSize, sortColumn, order);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvGoodsReceiptManager#querySize(com.worldbestsoft.model.criteria.InvGoodsReceiptCriteria)
	 */
	@Override
    public Integer querySize(InvGoodsReceiptCriteria criteria) {
	    return invGoodsReceiptDao.querySize(criteria);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvGoodsReceiptManager#getAll()
	 */
	@Override
    public List<InvGoodReceipt> getAll() {
	    return invGoodsReceiptDao.getAll();
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvGoodsReceiptManager#get(java.lang.Long)
	 */
	@Override
    public InvGoodReceipt get(Long id) {
	    return invGoodsReceiptDao.get(id);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvGoodsReceiptManager#save(com.worldbestsoft.model.InvGoodReceipt)
	 */
	@Override
    public InvGoodReceipt save(InvGoodReceipt invGoodsReceipt) {
		//save to get key
		InvGoodReceipt invGoodsReceiptSave = invGoodsReceiptDao.save(invGoodsReceipt);
		Set<InvGoodReceiptItem> newInvGoodReceiptItemList = invGoodsReceipt.getInvGoodReceiptItems();
		
		if (null != invGoodsReceipt.getId()) {
			List<InvGoodReceiptItem> oldinvGoodReceiptItemList = invGoodsReceiptItemDao.findByInvGoodReceipt(invGoodsReceipt.getId());
			//delete if not in the new list.
			for (InvGoodReceiptItem invGoodReceiptItem : oldinvGoodReceiptItemList) {
				InvGoodReceiptItem foundInvGoodReceiptItem = (InvGoodReceiptItem) CollectionUtils.find(newInvGoodReceiptItemList, new BeanPropertyValueEqualsPredicate("invItem.code", invGoodReceiptItem.getInvItem().getCode()));
				if (null == foundInvGoodReceiptItem) {
					//delete 
					invGoodsReceiptItemDao.remove(invGoodReceiptItem.getId());
				}
			}
			
			//add or update new list
			
			for (InvGoodReceiptItem invGoodReceiptItem : newInvGoodReceiptItemList) {
				InvGoodReceiptItem foundInvGoodReceiptItem = (InvGoodReceiptItem) CollectionUtils.find(oldinvGoodReceiptItemList, new BeanPropertyValueEqualsPredicate("invItem.code", invGoodReceiptItem.getInvItem().getCode()));
				if (null == foundInvGoodReceiptItem) {
					invGoodReceiptItem.setInvGoodReceipt(invGoodsReceiptSave);
					invGoodsReceiptItemDao.save(invGoodReceiptItem);
				} else {
					//update old oboect
					foundInvGoodReceiptItem.setMemo(invGoodReceiptItem.getMemo());
					foundInvGoodReceiptItem.setQty(invGoodReceiptItem.getQty());
					foundInvGoodReceiptItem.setUnitPrice(invGoodReceiptItem.getUnitPrice());
					foundInvGoodReceiptItem.setUnitType(invGoodReceiptItem.getUnitType());
					
					invGoodsReceiptItemDao.save(foundInvGoodReceiptItem);
				}
			}
			
		} else {
			//get running no
			for (InvGoodReceiptItem invGoodReceiptItem : newInvGoodReceiptItemList) {
				invGoodReceiptItem.setInvGoodReceipt(invGoodsReceiptSave);
				invGoodsReceiptItemDao.save(invGoodReceiptItem);
			}
		}
	    return invGoodsReceiptSave;
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvGoodsReceiptManager#remove(java.lang.Long)
	 */
	@Override
    public void remove(Long id) {
	    invGoodsReceiptDao.remove(id);
    }
	
}
