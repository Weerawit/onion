package com.worldbestsoft.service;

import java.util.List;

import com.worldbestsoft.model.JobOrder;
import com.worldbestsoft.model.criteria.JobOrderCriteria;

public interface JobOrderManager {

	public abstract List<JobOrder> query(JobOrderCriteria criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(JobOrderCriteria criteria);

	public abstract JobOrder get(Long id);

	public abstract JobOrder save(JobOrder object);

	public abstract void remove(Long id, String user, String cancelReason);

	public abstract JobOrder maskAsDone(Long jobOrderId, String user);


}