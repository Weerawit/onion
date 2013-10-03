package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.criteria.SaleOrderCriteria;
import com.worldbestsoft.model.criteria.SaleOrderPaymentSummaryReport;

public interface SaleOrderPaymentSummaryReportDao {

	public abstract List<SaleOrderPaymentSummaryReport> query(SaleOrderCriteria criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(SaleOrderCriteria criteria);

}