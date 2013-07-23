package com.worldbestsoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.InvGoodsReceiptItemDao;
import com.worldbestsoft.model.InvGoodReceiptItem;
import com.worldbestsoft.service.InvGoodsReceiptItemManager;

@Service("invGoodsReceiptItemManager")
public class InvGoodsReceiptItemManagerImpl implements InvGoodsReceiptItemManager {
	private InvGoodsReceiptItemDao invGoodsReceiptItemDao;

	public InvGoodsReceiptItemDao getInvGoodsReceiptItemDao() {
		return invGoodsReceiptItemDao;
	}

	@Autowired
	public void setInvGoodsReceiptItemDao(InvGoodsReceiptItemDao invGoodsReceiptItemDao) {
		this.invGoodsReceiptItemDao = invGoodsReceiptItemDao;
	}

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvGoodsReceiptItemManager#getAll()
	 */
	@Override
    public List<InvGoodReceiptItem> getAll() {
	    return invGoodsReceiptItemDao.getAll();
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvGoodsReceiptItemManager#get(java.lang.Long)
	 */
	@Override
    public InvGoodReceiptItem get(Long id) {
	    return invGoodsReceiptItemDao.get(id);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvGoodsReceiptItemManager#save(com.worldbestsoft.model.InvGoodReceiptItem)
	 */
	@Override
    public InvGoodReceiptItem save(InvGoodReceiptItem object) {
	    return invGoodsReceiptItemDao.save(object);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvGoodsReceiptItemManager#remove(java.lang.Long)
	 */
	@Override
    public void remove(Long id) {
	    invGoodsReceiptItemDao.remove(id);
    }

	
	
}
