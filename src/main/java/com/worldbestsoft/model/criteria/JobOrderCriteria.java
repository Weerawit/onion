package com.worldbestsoft.model.criteria;

import java.util.Date;

import com.worldbestsoft.model.Employee;
import com.worldbestsoft.model.JobOrder;
import com.worldbestsoft.model.SaleOrderItem;

public class JobOrderCriteria extends JobOrder {
	private Date startDateFrom;
	private Date startDateTo;
	private Date endDateFrom;
	private Date endDateTo;
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
	public Date getEndDateFrom() {
		return endDateFrom;
	}
	public void setEndDateFrom(Date endDateFrom) {
		this.endDateFrom = endDateFrom;
	}
	public Date getEndDateTo() {
		return endDateTo;
	}
	public void setEndDateTo(Date endDateTo) {
		this.endDateTo = endDateTo;
	}
	
	
}
