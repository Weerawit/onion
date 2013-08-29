package com.worldbestsoft.dao.hibernate;

import java.util.List;

import com.worldbestsoft.dao.GenericDao;
import com.worldbestsoft.model.JobOrder;
import com.worldbestsoft.model.criteria.JobOrderCriteria;

public interface JobOrderDao extends GenericDao<JobOrder, Long> {

	public abstract List<JobOrder> query(JobOrderCriteria criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(JobOrderCriteria criteria);

}