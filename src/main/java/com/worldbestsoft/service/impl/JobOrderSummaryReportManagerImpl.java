package com.worldbestsoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.JobOrderSummaryReportDao;
import com.worldbestsoft.model.criteria.JobOrderCriteria;
import com.worldbestsoft.model.criteria.JobOrderSummaryReport;
import com.worldbestsoft.service.JobOrderSummaryReportManager;

@Service("jobOrderSummaryReportManager")
public class JobOrderSummaryReportManagerImpl implements JobOrderSummaryReportManager {
	
	private JobOrderSummaryReportDao jobOrderSummaryReportDao;

	public JobOrderSummaryReportDao getJobOrderSummaryReportDao() {
		return jobOrderSummaryReportDao;
	}

	@Autowired
	public void setJobOrderSummaryReportDao(JobOrderSummaryReportDao jobOrderSummaryReportDao) {
		this.jobOrderSummaryReportDao = jobOrderSummaryReportDao;
	}

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.JobOrderSummaryReportManager#query(com.worldbestsoft.model.criteria.JobOrderCriteria, int, int, java.lang.String, java.lang.String)
	 */
	@Override
    public List<JobOrderSummaryReport> query(JobOrderCriteria criteria, int page, int pageSize, String sortColumn, String order) {
	    return jobOrderSummaryReportDao.query(criteria, page, pageSize, sortColumn, order);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.JobOrderSummaryReportManager#querySize(com.worldbestsoft.model.criteria.JobOrderCriteria)
	 */
	@Override
    public Integer querySize(JobOrderCriteria criteria) {
	    return jobOrderSummaryReportDao.querySize(criteria);
    }
	
	
}
