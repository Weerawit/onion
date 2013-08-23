package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.SaleReceipt;
import com.worldbestsoft.model.criteria.SaleReceiptCriteria;

public interface SaleReceiptDao extends GenericDao<SaleReceipt, Long> {

	public abstract List<SaleReceipt> query(SaleReceiptCriteria criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(SaleReceiptCriteria criteria);

	public abstract List<SaleReceipt> findBySaleOrderNo(String saleOrderNo);

}