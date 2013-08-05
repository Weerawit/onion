package com.worldbestsoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.InvGoodsMovementItemDao;
import com.worldbestsoft.model.InvGoodsMovementItem;
import com.worldbestsoft.service.InvGoodsMovementItemManager;

@Service("invGoodsMovementItemManager")
public class InvGoodsMovementItemManagerImpl implements InvGoodsMovementItemManager {
	
	private InvGoodsMovementItemDao invGoodsMovementItemDao;
	
	public InvGoodsMovementItemDao getInvGoodsMovementItemDao() {
		return invGoodsMovementItemDao;
	}

	@Autowired
	public void setInvGoodsMovementItemDao(InvGoodsMovementItemDao invGoodsMovementItemDao) {
		this.invGoodsMovementItemDao = invGoodsMovementItemDao;
	}

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvGoodsMovementItemManager#get(java.lang.Long)
	 */
	@Override
    public InvGoodsMovementItem get(Long id) {
	    return invGoodsMovementItemDao.get(id);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvGoodsMovementItemManager#save(com.worldbestsoft.model.InvGoodsMovementItem)
	 */
	@Override
    public InvGoodsMovementItem save(InvGoodsMovementItem object) {
	    return invGoodsMovementItemDao.save(object);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvGoodsMovementItemManager#remove(java.lang.Long)
	 */
	@Override
    public void remove(Long id) {
	    invGoodsMovementItemDao.remove(id);
    }
	
	
	
	
}
