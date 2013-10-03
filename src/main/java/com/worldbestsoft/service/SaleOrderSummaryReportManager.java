package com.worldbestsoft.service;

import java.util.List;

import com.worldbestsoft.model.criteria.SaleOrderCriteria;
import com.worldbestsoft.model.criteria.SaleOrderSummaryReport;

public interface SaleOrderSummaryReportManager {

	public abstract List<SaleOrderSummaryReport> query(SaleOrderCriteria criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(SaleOrderCriteria criteria);

}