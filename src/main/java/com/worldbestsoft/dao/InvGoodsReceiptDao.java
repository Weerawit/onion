package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.InvGoodReceipt;
import com.worldbestsoft.model.criteria.InvGoodsReceiptCriteria;

public interface InvGoodsReceiptDao extends GenericDao<InvGoodReceipt, Long>{

	public abstract List<InvGoodReceipt> query(InvGoodsReceiptCriteria criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(InvGoodsReceiptCriteria criteria);

}