package com.worldbestsoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.InvGoodsReceiptDao;
import com.worldbestsoft.model.InvGoodReceipt;
import com.worldbestsoft.model.criteria.InvGoodsReceiptCriteria;
import com.worldbestsoft.service.InvGoodsReceiptManager;

@Service("invGoodsReceiptManager")
public class InvGoodsReceiptManagerImpl implements InvGoodsReceiptManager {
	private InvGoodsReceiptDao invGoodsReceiptDao;

	public InvGoodsReceiptDao getInvGoodsReceiptDao() {
		return invGoodsReceiptDao;
	}

	@Autowired
	public void setInvGoodsReceiptDao(InvGoodsReceiptDao invGoodsReceiptDao) {
		this.invGoodsReceiptDao = invGoodsReceiptDao;
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
    public InvGoodReceipt save(InvGoodReceipt object) {
	    return invGoodsReceiptDao.save(object);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvGoodsReceiptManager#remove(java.lang.Long)
	 */
	@Override
    public void remove(Long id) {
	    invGoodsReceiptDao.remove(id);
    }
	
}
