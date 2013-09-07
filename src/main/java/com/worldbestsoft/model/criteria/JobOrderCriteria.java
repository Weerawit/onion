package com.worldbestsoft.model.criteria;

import java.util.Date;

import com.worldbestsoft.model.Employee;
import com.worldbestsoft.model.JobOrder;
import com.worldbestsoft.model.SaleOrderItem;

public class JobOrderCriteria extends JobOrder {
	private Date startDateFrom;
	private Date startDateTo;
	private Date targetEndDateFrom;
	private Date targetEndDateTo;
	public Date getStartDateFrom() {
		return startDateFrom;
	}
	public void setStartDateFrom(Date startDateFrom) {
		this.startDateFrom = startDateFrom;
	}
	public Date getStartDateTo() {
		return startDateTo;
	}
	public void setStartDateTo(Date startDateTo) {
		this.startDateTo = startDateTo;
	}
	public Date getTargetEndDateFrom() {
		return targetEndDateFrom;
	}
	public void setTargetEndDateFrom(Date targetEndDateFrom) {
		this.targetEndDateFrom = targetEndDateFrom;
	}
	public Date getTargetEndDateTo() {
		return targetEndDateTo;
	}
	public void setTargetEndDateTo(Date targetEndDateTo) {
		this.targetEndDateTo = targetEndDateTo;
	}
	
	
}
