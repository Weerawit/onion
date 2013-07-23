package com.worldbestsoft.service;

import java.util.List;

import com.worldbestsoft.model.InvGoodsReceipt;
import com.worldbestsoft.model.criteria.InvGoodsReceiptCriteria;

public interface InvGoodsReceiptManager {

	public abstract List<InvGoodsReceipt> query(InvGoodsReceiptCriteria criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(InvGoodsReceiptCriteria criteria);

	public abstract List<InvGoodsReceipt> getAll();

	public abstract InvGoodsReceipt get(Long id);

	public abstract InvGoodsReceipt save(InvGoodsReceipt invGoodsReceipt);

	public abstract void remove(Long id);

}