package com.worldbestsoft.service.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.SaleOrderDao;
import com.worldbestsoft.dao.SaleOrderItemDao;
import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.model.SaleOrderItem;
import com.worldbestsoft.model.criteria.SaleOrderCriteria;
import com.worldbestsoft.service.SaleOrderManager;

@Service("saleOrderManager")
public class SaleOrderManagerImpl implements SaleOrderManager {
	
	private SaleOrderDao saleOrderDao;
	private SaleOrderItemDao saleOrderItemDao;
	
	public SaleOrderDao getSaleOrderDao() {
		return saleOrderDao;
	}

	@Autowired
	public void setSaleOrderDao(SaleOrderDao saleOrderDao) {
		this.saleOrderDao = saleOrderDao;
	}

	public SaleOrderItemDao getSaleOrderItemDao() {
		return saleOrderItemDao;
	}

	@Autowired
	public void setSaleOrderItemDao(SaleOrderItemDao saleOrderItemDao) {
		this.saleOrderItemDao = saleOrderItemDao;
	}

	
    /* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SaleOrderManager#query(com.worldbestsoft.model.SaleOrderCriteria, int, int, java.lang.String, java.lang.String)
	 */
    @Override
    public List<SaleOrder> query(SaleOrderCriteria criteria, int page, int pageSize, String sortColumn, String order) {
	    return saleOrderDao.query(criteria, page, pageSize, sortColumn, order);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SaleOrderManager#querySize(com.worldbestsoft.model.SaleOrderCriteria)
	 */
	@Override
    public Integer querySize(SaleOrderCriteria criteria) {
	    return saleOrderDao.querySize(criteria);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SaleOrderManager#findBySaleOrderNo(java.lang.String)
	 */
	@Override
    public SaleOrder findBySaleOrderNo(String saleOrderNo) {
	    return saleOrderDao.findBySaleOrderNo(saleOrderNo);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SaleOrderManager#findBySaleOrder(java.lang.Long)
	 */
	@Override
    public List<SaleOrderItem> findBySaleOrder(Long id) {
	    return saleOrderItemDao.findBySaleOrder(id);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SaleOrderManager#save(com.worldbestsoft.model.SaleOrder, java.util.Collection)
	 */
	@Override
    public SaleOrder save(SaleOrder saleOrder, Collection<SaleOrderItem> newSaleOrderItemList) {
    	 	
		if (null != saleOrder.getId()) {
			//update
			List<SaleOrderItem> oldSaleOrderItemList = saleOrderItemDao.findBySaleOrder(saleOrder.getId());
			// delete if not in the new list.
			for (SaleOrderItem saleOrderItem : oldSaleOrderItemList) {
				SaleOrderItem foundsaleOrderItem = (SaleOrderItem) CollectionUtils.find(newSaleOrderItemList, new BeanPropertyValueEqualsPredicate("catalog.code", saleOrderItem.getCatalog().getCode()));
				if (null == foundsaleOrderItem) {
					// delete
					saleOrderItemDao.remove(saleOrderItem.getId());
				}
			}
			
			// add or update new list
			if (null != newSaleOrderItemList) {
				for (SaleOrderItem saleOrderItem : newSaleOrderItemList) {
					SaleOrderItem foundSaleOrderItem = (SaleOrderItem) CollectionUtils.find(oldSaleOrderItemList, new BeanPropertyValueEqualsPredicate("catalog.code", saleOrderItem.getCatalog().getCode()));
					if (null == foundSaleOrderItem) {
						saleOrderItem.setSaleOrder(saleOrder);
						saleOrderItemDao.save(saleOrderItem);
					} else {
						// update old saleOrder item
						foundSaleOrderItem.setPrice(saleOrderItem.getPrice());
						foundSaleOrderItem.setPricePerUnit(saleOrderItem.getPricePerUnit());
						foundSaleOrderItem.setQty(saleOrderItem.getQty());
						saleOrderItemDao.save(foundSaleOrderItem);
					}
				}
			}
			
			saleOrder = saleOrderDao.save(saleOrder);
		} else {
			SaleOrder saleOrderSave = saleOrderDao.save(saleOrder);
			if (null != newSaleOrderItemList) {
				for (SaleOrderItem saleOrderItem : newSaleOrderItemList) {
					saleOrderItem.setSaleOrder(saleOrderSave);
					saleOrderItemDao.save(saleOrderItem);
				}
			}
			saleOrder = saleOrderDao.save(saleOrderSave);
		}
		
	    return saleOrder;
    }

    /* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SaleOrderManager#remove(java.lang.Long)
	 */
    @Override
    public void remove(Long id) {
		SaleOrder saleOrder = saleOrderDao.get(id);
		if (null != saleOrder.getSaleOrderItems()) {
			for (SaleOrderItem saleOrderItem : saleOrder.getSaleOrderItems()) {
				saleOrderItemDao.remove(saleOrderItem.getId());
			}
		}
	    saleOrderDao.remove(id);
    }

	@Override
    public SaleOrder get(Long id) {
	    return saleOrderDao.get(id);
    }

	
	
}
