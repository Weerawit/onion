package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.InvGoodsReceipt;
import com.worldbestsoft.model.criteria.InvGoodsReceiptCriteria;

public interface InvGoodsReceiptDao extends GenericDao<InvGoodsReceipt, Long>{

	public abstract List<InvGoodsReceipt> query(InvGoodsReceiptCriteria criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(InvGoodsReceiptCriteria criteria);

}