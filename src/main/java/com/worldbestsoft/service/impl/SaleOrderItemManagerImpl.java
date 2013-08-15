package com.worldbestsoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.SaleOrderItemDao;
import com.worldbestsoft.model.SaleOrderItem;
import com.worldbestsoft.service.SaleOrderItemManager;

@Service("saleOrderItemManager")
public class SaleOrderItemManagerImpl implements SaleOrderItemManager {

	private SaleOrderItemDao saleOrderItemDao;
	
	public SaleOrderItemDao getSaleOrderItemDao() {
		return saleOrderItemDao;
	}

	@Autowired
	public void setSaleOrderItemDao(SaleOrderItemDao saleOrderItemDao) {
		this.saleOrderItemDao = saleOrderItemDao;
	}

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SaleOrderItemManager#findBySaleOrder(java.lang.Long)
	 */
	@Override
    public List<SaleOrderItem> findBySaleOrder(Long id) {
	    return saleOrderItemDao.findBySaleOrder(id);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SaleOrderItemManager#get(java.lang.Long)
	 */
	@Override
    public SaleOrderItem get(Long id) {
	    return saleOrderItemDao.get(id);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SaleOrderItemManager#save(com.worldbestsoft.model.SaleOrderItem)
	 */
	@Override
    public SaleOrderItem save(SaleOrderItem object) {
	    return saleOrderItemDao.save(object);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SaleOrderItemManager#remove(java.lang.Long)
	 */
	@Override
    public void remove(Long id) {
	    saleOrderItemDao.remove(id);
    }


	
	
}
