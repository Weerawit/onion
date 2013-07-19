package com.worldbestsoft.service;

import java.util.List;

import com.worldbestsoft.model.InvGoodReceipt;
import com.worldbestsoft.model.criteria.InvGoodsReceiptCriteria;

public interface InvGoodsReceiptManager {

	public abstract List<InvGoodReceipt> query(InvGoodsReceiptCriteria criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(InvGoodsReceiptCriteria criteria);

	public abstract List<InvGoodReceipt> getAll();

	public abstract InvGoodReceipt get(Long id);

	public abstract InvGoodReceipt save(InvGoodReceipt object);

	public abstract void remove(Long id);

}