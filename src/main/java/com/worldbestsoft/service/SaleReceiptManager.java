package com.worldbestsoft.service;

import java.util.List;

import com.worldbestsoft.model.SaleReceipt;
import com.worldbestsoft.model.criteria.SaleReceiptCriteria;

public interface SaleReceiptManager {

	public abstract List<SaleReceipt> query(SaleReceiptCriteria criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(SaleReceiptCriteria criteria);

	public abstract SaleReceipt get(Long id);

	public abstract SaleReceipt save(SaleReceipt object);

	public abstract List<SaleReceipt> findBySaleOrderNo(String saleOrderNo);

	public abstract void remove(Long id, String user, String cancelReason);

}