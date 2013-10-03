package com.worldbestsoft.service;

import java.util.List;

import com.worldbestsoft.model.criteria.SaleOrderCriteria;
import com.worldbestsoft.model.criteria.SaleOrderPaymentSummaryReport;

public interface SaleOrderPaymentSummaryReportManager {

	public abstract List<SaleOrderPaymentSummaryReport> query(SaleOrderCriteria criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(SaleOrderCriteria criteria);

}