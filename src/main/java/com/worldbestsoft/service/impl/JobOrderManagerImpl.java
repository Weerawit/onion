package com.worldbestsoft.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.hibernate.JobOrderDao;
import com.worldbestsoft.model.ConstantModel;
import com.worldbestsoft.model.DocumentNumber;
import com.worldbestsoft.model.InvItemLevel;
import com.worldbestsoft.model.JobOrder;
import com.worldbestsoft.model.criteria.JobOrderCriteria;
import com.worldbestsoft.service.DocumentNumberFormatter;
import com.worldbestsoft.service.DocumentNumberGenerator;
import com.worldbestsoft.service.InvStockManager;
import com.worldbestsoft.service.JobOrderManager;

@Service("jobOrderManager")
public class JobOrderManagerImpl implements JobOrderManager {
	
	private DocumentNumberGenerator documentNumberGenerator;
	private JobOrderDao jobOrderDao;
	private InvStockManager invStockManager;
	
	private String documentNumberFormat = "JB{0,number,00000}";
	
	public JobOrderDao getJobOrderDao() {
		return jobOrderDao;
	}

	@Autowired
	public void setJobOrderDao(JobOrderDao jobOrderDao) {
		this.jobOrderDao = jobOrderDao;
	}
	
	public DocumentNumberGenerator getDocumentNumberGenerator() {
		return documentNumberGenerator;
	}
	
	public String getDocumentNumberFormat() {
		return documentNumberFormat;
	}

	public void setDocumentNumberFormat(String documentNumberFormat) {
		this.documentNumberFormat = documentNumberFormat;
	}

	@Autowired
	public void setDocumentNumberGenerator(DocumentNumberGenerator documentNumberGenerator) {
		this.documentNumberGenerator = documentNumberGenerator;
	}
	

	public InvStockManager getInvStockManager() {
		return invStockManager;
	}

	@Autowired
	public void setInvStockManager(InvStockManager invStockManager) {
		this.invStockManager = invStockManager;
	}

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.JobOrderManager#query(com.worldbestsoft.model.criteria.JobOrderCriteria, int, int, java.lang.String, java.lang.String)
	 */
	@Override
    public List<JobOrder> query(JobOrderCriteria criteria, int page, int pageSize, String sortColumn, String order) {
	    return jobOrderDao.query(criteria, page, pageSize, sortColumn, order);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.JobOrderManager#querySize(com.worldbestsoft.model.criteria.JobOrderCriteria)
	 */
	@Override
    public Integer querySize(JobOrderCriteria criteria) {
	    return jobOrderDao.querySize(criteria);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.JobOrderManager#get(java.lang.Long)
	 */
	@Override
    public JobOrder get(Long id) {
	    return jobOrderDao.get(id);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.JobOrderManager#save(com.worldbestsoft.model.JobOrder)
	 */
	@Override
    public JobOrder save(JobOrder object) {
		if (null == object.getId()) {
			DocumentNumber documentNumber = documentNumberGenerator.newDocumentNumber();
			object.setDocumentNumber(documentNumber);
			object.setStatus(ConstantModel.JobOrderStatus.NEW.getCode());
			object = jobOrderDao.save(object);
			//get document number
        		documentNumberGenerator.nextDocumentNumber(JobOrder.class, documentNumber.getInternalNo(), new DocumentNumberFormatter() {
					
					@Override
					public String format(Long nextSeq) {
						return MessageFormat.format(documentNumberFormat, nextSeq);
					}
				});
            return object;
		} else {
			return jobOrderDao.save(object);
		}
    }
	
	@Override
    public JobOrder maskAsDone(Long jobOrderId, String user) {
		JobOrder jobOrder = jobOrderDao.get(jobOrderId);
		jobOrder.setStatus(ConstantModel.JobOrderStatus.DONE.getCode());
		jobOrder.setActualEndDate(new Date());
		jobOrder.setUpdateDate(new Date());
		jobOrder.setUpdateUser(user);
		jobOrder = jobOrderDao.save(jobOrder);
		
		InvItemLevel invItemLevel = new InvItemLevel();
		invItemLevel.setInvItem(jobOrder.getCatalog().getInvItem());
		invItemLevel.setQtyAdjust(jobOrder.getQty());
		invItemLevel.setQtyAvailableAdjust(jobOrder.getQty());
		invItemLevel.setTransactionDate(new Date());
		invItemLevel.setTransactionType(ConstantModel.ItemSockTransactionType.COMMIT.getCode());
		invItemLevel.setUpdateUser(user);
		invItemLevel.setDocumentNumber(jobOrder.getDocumentNumber());
		invItemLevel.setRefType(ConstantModel.RefType.JOB_ORDER.getCode());
		getInvStockManager().updateStock(invItemLevel);
		return jobOrder;
	}

	@Override
    public void remove(Long id, String user, String cancelReason) {
		JobOrder jobOrder = jobOrderDao.get(id);
		jobOrder.setUpdateDate(new Date());
		jobOrder.setUpdateUser(user);
		jobOrder.setStatus(ConstantModel.JobOrderStatus.CANCEL.getCode()); //cancel
		jobOrder.setCancelReason(cancelReason);
		jobOrder = jobOrderDao.save(jobOrder);
    }
	
}
