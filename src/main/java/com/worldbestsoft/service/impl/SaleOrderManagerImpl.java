package com.worldbestsoft.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Date;
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
import com.worldbestsoft.dao.hibernate.JobOrderDao;
import com.worldbestsoft.model.ConstantModel;
import com.worldbestsoft.model.InvItemLevel;
import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.model.SaleOrderItem;
import com.worldbestsoft.model.SaleReceipt;
import com.worldbestsoft.model.criteria.SaleOrderCriteria;
import com.worldbestsoft.service.DocumentNumberGenerator;
import com.worldbestsoft.service.DocumentNumberGeneratorException;
import com.worldbestsoft.service.InvItemLevelChangedEvent;
import com.worldbestsoft.service.InvStockManager;
import com.worldbestsoft.service.SaleOrderManager;

@Service("saleOrderManager")
public class SaleOrderManagerImpl implements SaleOrderManager, ApplicationContextAware {
	
	private SaleOrderDao saleOrderDao;
	private SaleOrderItemDao saleOrderItemDao;
	private JobOrderDao jobOrderDao;
	private DocumentNumberGenerator documentNumberGenerator;
	private ApplicationContext context;
	private InvStockManager invStockManager;

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
	
	public InvStockManager getInvStockManager() {
		return invStockManager;
	}

	@Autowired
	public void setInvStockManager(InvStockManager invStockManager) {
		this.invStockManager = invStockManager;
	}
	
	public JobOrderDao getJobOrderDao() {
		return jobOrderDao;
	}

	@Autowired
	public void setJobOrderDao(JobOrderDao jobOrderDao) {
		this.jobOrderDao = jobOrderDao;
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
	
	@Override
    public SaleOrder delivery(Long saleOrderId) {
		SaleOrder saleOrder = saleOrderDao.get(saleOrderId);
		saleOrder.setStatus(ConstantModel.SaleOrderStatus.DELIVERY.getCode());
		saleOrder = saleOrderDao.save(saleOrder);
		List<SaleOrderItem> saleOrderItemList = saleOrderItemDao.findBySaleOrder(saleOrder.getId());
		
		for (SaleOrderItem saleOrderItem : saleOrderItemList) {
			InvItemLevel invItemLevel = new InvItemLevel();
			invItemLevel.setInvItem(saleOrderItem.getCatalog().getInvItem());
			invItemLevel.setQtyAdjust(saleOrderItem.getQty().multiply(BigDecimal.valueOf(-1)));
			invItemLevel.setTransactionDate(new Date());
			invItemLevel.setUpdateUser(saleOrder.getCreateUser());
			invItemLevel.setTransactionType(ConstantModel.ItemSockTransactionType.COMMIT.getCode());
			invItemLevel.setRefDocument(saleOrder.getSaleOrderNo());
			invItemLevel.setRefType(ConstantModel.RefType.SALE_ORDER.getCode());
			
			InvItemLevelChangedEvent invItemLevelChangedEvent = new InvItemLevelChangedEvent(invItemLevel);
			context.publishEvent(invItemLevelChangedEvent);
		}
		return saleOrder;
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
			saleOrder.setStatus(ConstantModel.SaleOrderStatus.ACTIVE.getCode());
			SaleOrder saleOrderSave = saleOrderDao.save(saleOrder);
			
			//get document number
            try {
            		Long documentNumber = documentNumberGenerator.nextDocumentNumber(SaleOrder.class);
	            String runningNo = MessageFormat.format(documentNumberFormat, documentNumber);
				saleOrderSave.setSaleOrderNo(runningNo);
				
            } catch (DocumentNumberGeneratorException e) {
    				throw new RuntimeException(e);
            }
            
			BigDecimal totalPrice = BigDecimal.ZERO;
			if (null != newSaleOrderItemList) {
				for (SaleOrderItem saleOrderItem : newSaleOrderItemList) {
					saleOrderItem.setPrice(saleOrderItem.getPricePerUnit().multiply(saleOrderItem.getQty()));
					totalPrice = totalPrice.add(saleOrderItem.getPrice());
					saleOrderItem.setSaleOrder(saleOrderSave);
					saleOrderItem = saleOrderItemDao.save(saleOrderItem);
					
					
					InvItemLevel invItemLevel = new InvItemLevel();
					invItemLevel.setInvItem(saleOrderItem.getCatalog().getInvItem());
					invItemLevel.setQtyAvailableAdjust(saleOrderItem.getQty().multiply(BigDecimal.valueOf(-1)));
					invItemLevel.setTransactionDate(new Date());
					invItemLevel.setUpdateUser(saleOrder.getCreateUser());
					invItemLevel.setTransactionType(ConstantModel.ItemSockTransactionType.RESERVED.getCode());
					invItemLevel.setRefDocument(saleOrderSave.getSaleOrderNo());
					invItemLevel.setRefType(ConstantModel.RefType.SALE_ORDER.getCode());
					
					InvItemLevelChangedEvent invItemLevelChangedEvent = new InvItemLevelChangedEvent(invItemLevel);
					context.publishEvent(invItemLevelChangedEvent);
					
//					//check stock
//					InvStock invStock = invStockManager.findByInvItemCode(saleOrderItem.getCatalog().getInvItem().getCode());
//					//not in stock and qty less than current in stock
//					BigDecimal currentQtyInStock = BigDecimal.ZERO;
//					if (null != invStock) {
//						currentQtyInStock = invStock.getQtyAvailable();
//					}
//					BigDecimal qtyStockAfter = currentQtyInStock.subtract(saleOrderItem.getQty());
//					
//					if (qtyStockAfter.compareTo(BigDecimal.ZERO) < 0) {
//						//need to create job
//						int size = qtyStockAfter.intValue() * -1; //make it > 0
//						for (int i = 0; i < size; i++) {
//							//create job
//							JobOrder jobOrder = new JobOrder();
//							jobOrder.setSaleOrderItem(saleOrderItem);
//							jobOrder.setEndDate(saleOrder.getDeliveryDate());
//							jobOrder.setStatus(ConstantModel.JobOrderStatus.NEW.getCode());
//							jobOrderDao.save(jobOrder);
//						}
//						//remove from stock all current qty
//						InvItemLevel invItemLevel = new InvItemLevel();
//						invItemLevel.setInvItem(saleOrderItem.getCatalog().getInvItem());
//						invItemLevel.setQtyAvailableAdjust(currentQtyInStock.multiply(BigDecimal.valueOf(-1)));
//						invItemLevel.setTransactionDate(new Date());
//						invItemLevel.setTransactionType(ConstantModel.ItemSockTransactionType.RESERVED.getCode());
//						invItemLevel.setRefDocument(saleOrderSave.getSaleOrderNo());
//						invItemLevel.setRefType(ConstantModel.RefType.SALE_ORDER.getCode());
//
//
//						InvItemLevelChangedEvent invItemLevelChangedEvent = new InvItemLevelChangedEvent(invItemLevel);
//						context.publishEvent(invItemLevelChangedEvent);
//					} else {
//						//remoe from stock only
//						//remove from stock all current qty
//						InvItemLevel invItemLevel = new InvItemLevel();
//						invItemLevel.setInvItem(saleOrderItem.getCatalog().getInvItem());
//						invItemLevel.setQtyAvailableAdjust(saleOrderItem.getQty().multiply(BigDecimal.valueOf(-1)));
//						invItemLevel.setTransactionDate(new Date());
//						invItemLevel.setRefDocument(saleOrderSave.getSaleOrderNo());
//						invItemLevel.setRefType(ConstantModel.RefType.SALE_ORDER.getCode());
//
//						InvItemLevelChangedEvent invItemLevelChangedEvent = new InvItemLevelChangedEvent(invItemLevel);
//						context.publishEvent(invItemLevelChangedEvent);
//					}
					
				}
			}
			saleOrderSave.setTotalPrice(totalPrice);
			
			saleOrder = saleOrderDao.save(saleOrderSave);
		}
		
	    return saleOrder;
    }

//    /* (non-Javadoc)
//	 * @see com.worldbestsoft.service.impl.SaleOrderManager#remove(java.lang.Long)
//	 */
//    @Override
//    public void remove(Long id) {
//		SaleOrder saleOrder = saleOrderDao.get(id);
//		if (null != saleOrder.getSaleOrderItems()) {
//			for (SaleOrderItem saleOrderItem : saleOrder.getSaleOrderItems()) {
//				saleOrderItemDao.remove(saleOrderItem.getId());
//			}
//		}
//	    saleOrderDao.remove(id);
//    }
    
    @Override
    public void remove(Long id, String user, String cancelReason) {
    		SaleOrder saleOrder = saleOrderDao.get(id);
		saleOrder.setUpdateDate(new Date());
		saleOrder.setUpdateUser(user);
		saleOrder.setStatus(ConstantModel.SaleOrderStatus.CANCEL.getCode()); //cancel
		saleOrder.setCancelReason(cancelReason);
		saleOrder = saleOrderDao.save(saleOrder);
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
			saleOrder.setPaymentStatus(ConstantModel.PaymentStatus.NONE.getCode());
		} else if (saleOrder.getTotalPrice().compareTo(saleOrder.getPaymentPaid()) <= 0) {
			saleOrder.setPaymentStatus(ConstantModel.PaymentStatus.FULLY_PAID.getCode());
		} else {
			saleOrder.setPaymentStatus(ConstantModel.PaymentStatus.PARTAIL_PAID.getCode());
		}
		saleOrderDao.save(saleOrder);
	}
	
	
}
