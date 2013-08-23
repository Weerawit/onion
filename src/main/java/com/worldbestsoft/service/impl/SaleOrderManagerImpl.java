package com.worldbestsoft.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.SaleOrderDao;
import com.worldbestsoft.dao.SaleOrderItemDao;
import com.worldbestsoft.model.InvGoodsReceipt;
import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.model.SaleOrderItem;
import com.worldbestsoft.model.SaleReceipt;
import com.worldbestsoft.model.criteria.SaleOrderCriteria;
import com.worldbestsoft.service.DocumentNumberGenerator;
import com.worldbestsoft.service.DocumentNumberGeneratorException;
import com.worldbestsoft.service.SaleOrderManager;

@Service("saleOrderManager")
public class SaleOrderManagerImpl implements SaleOrderManager, ApplicationContextAware {
	
	private SaleOrderDao saleOrderDao;
	private SaleOrderItemDao saleOrderItemDao;
	private DocumentNumberGenerator documentNumberGenerator;
	private ApplicationContext context;

	private String documentNumberFormat = "SA{0,number,00000}";
	
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
			BigDecimal totalPrice = BigDecimal.ZERO;
			if (null != newSaleOrderItemList) {
				for (SaleOrderItem saleOrderItem : newSaleOrderItemList) {
					SaleOrderItem foundSaleOrderItem = (SaleOrderItem) CollectionUtils.find(oldSaleOrderItemList, new BeanPropertyValueEqualsPredicate("catalog.code", saleOrderItem.getCatalog().getCode()));
					if (null == foundSaleOrderItem) {
						saleOrderItem.setSaleOrder(saleOrder);
						saleOrderItem.setPrice(saleOrderItem.getPricePerUnit().multiply(saleOrderItem.getQty()));
						totalPrice = totalPrice.add(saleOrderItem.getPrice());
						saleOrderItemDao.save(saleOrderItem);
					} else {
						// update old saleOrder item
						foundSaleOrderItem.setPrice(saleOrderItem.getPricePerUnit().multiply(saleOrderItem.getQty()));
						foundSaleOrderItem.setPricePerUnit(saleOrderItem.getPricePerUnit());
						foundSaleOrderItem.setQty(saleOrderItem.getQty());
						totalPrice = totalPrice.add(saleOrderItem.getPrice());
						saleOrderItemDao.save(foundSaleOrderItem);
					}
				}
			}
			saleOrder.setTotalPrice(totalPrice);
			
			saleOrder = saleOrderDao.save(saleOrder);
		} else {
			SaleOrder saleOrderSave = saleOrderDao.save(saleOrder);
			BigDecimal totalPrice = BigDecimal.ZERO;
			if (null != newSaleOrderItemList) {
				for (SaleOrderItem saleOrderItem : newSaleOrderItemList) {
					saleOrderItem.setPrice(saleOrderItem.getPricePerUnit().multiply(saleOrderItem.getQty()));
					totalPrice = totalPrice.add(saleOrderItem.getPrice());
					saleOrderItem.setSaleOrder(saleOrderSave);
					saleOrderItemDao.save(saleOrderItem);
				}
			}
			saleOrderSave.setTotalPrice(totalPrice);
			
			//get document number
            try {
            		Long documentNumber = documentNumberGenerator.nextDocumentNumber(SaleOrder.class);
	            String runningNo = MessageFormat.format(documentNumberFormat, documentNumber);
				saleOrderSave.setSaleOrderNo(runningNo);
				
            } catch (DocumentNumberGeneratorException e) {
    				throw new RuntimeException(e);
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

	
	@Override
    public void updateSaleOrderPayment(SaleOrder saleOrder) {
		saleOrder = saleOrderDao.get(saleOrder.getId());
		BigDecimal paymentPaid =BigDecimal.ZERO;
		if (null != saleOrder.getSaleReceipts()) {
			for (SaleReceipt saleReceipt : saleOrder.getSaleReceipts()) {
				//all active
				if (StringUtils.equalsIgnoreCase("A", saleReceipt.getStatus())) {
					paymentPaid = paymentPaid.add(saleReceipt.getReceiptAmount());
				}
			}
		} 
		
		saleOrder.setPaymentPaid(paymentPaid);
		/**
		 * 1 = NONE
			2 = Partial paid
			3 = Fully paid
		 */
		if (BigDecimal.ZERO.equals(paymentPaid)) {
			saleOrder.setPaymentStatus("1");
		} else if (saleOrder.getTotalPrice().compareTo(saleOrder.getPaymentPaid()) <= 0) {
			saleOrder.setPaymentStatus("3");
		} else {
			saleOrder.setPaymentStatus("2");
		}
		saleOrderDao.save(saleOrder);
	}
	
	
}
