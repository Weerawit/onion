package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.criteria.JobOrderCriteria;
import com.worldbestsoft.model.criteria.JobOrderSummaryReport;

public interface JobOrderSummaryReportDao {

	public abstract List<JobOrderSummaryReport> query(JobOrderCriteria criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(JobOrderCriteria criteria);

}