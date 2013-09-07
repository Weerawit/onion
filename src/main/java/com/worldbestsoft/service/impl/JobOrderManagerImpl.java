package com.worldbestsoft.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.hibernate.JobOrderDao;
import com.worldbestsoft.model.ConstantModel;
import com.worldbestsoft.model.JobOrder;
import com.worldbestsoft.model.criteria.JobOrderCriteria;
import com.worldbestsoft.service.DocumentNumberGenerator;
import com.worldbestsoft.service.DocumentNumberGeneratorException;
import com.worldbestsoft.service.JobOrderManager;

@Service("jobOrderManager")
public class JobOrderManagerImpl implements JobOrderManager {
	
	private DocumentNumberGenerator documentNumberGenerator;
	private JobOrderDao jobOrderDao;
	
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
			object.setStatus(ConstantModel.JobOrderStatus.NEW.getCode());
			object = jobOrderDao.save(object);
			//get document number
            try {
            		Long documentNumber = documentNumberGenerator.nextDocumentNumber(JobOrder.class);
	            String runningNo = MessageFormat.format(documentNumberFormat, documentNumber);
	            object.setRunningNo(runningNo);
            } catch (DocumentNumberGeneratorException e) {
    				throw new RuntimeException(e);
            }
		}
	    return jobOrderDao.save(object);
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
