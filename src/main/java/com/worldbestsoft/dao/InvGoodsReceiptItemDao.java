package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.InvGoodsReceiptItem;

public interface InvGoodsReceiptItemDao extends GenericDao<InvGoodsReceiptItem, Long> {

	public abstract List<InvGoodsReceiptItem> findByInvGoodReceipt(Long id);

}