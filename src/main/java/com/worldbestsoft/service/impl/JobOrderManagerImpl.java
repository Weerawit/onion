package com.worldbestsoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.hibernate.JobOrderDao;
import com.worldbestsoft.model.JobOrder;
import com.worldbestsoft.model.criteria.JobOrderCriteria;
import com.worldbestsoft.service.JobOrderManager;

@Service("jobOrderManager")
public class JobOrderManagerImpl implements JobOrderManager {
	private JobOrderDao jobOrderDao;
	
	public JobOrderDao getJobOrderDao() {
		return jobOrderDao;
	}

	@Autowired
	public void setJobOrderDao(JobOrderDao jobOrderDao) {
		this.jobOrderDao = jobOrderDao;
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
	    return jobOrderDao.save(object);
    }

	@Override
    public void remove(Long id) {
	    jobOrderDao.remove(id);
    }
	
	
}
