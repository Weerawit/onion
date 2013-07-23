package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.InvGoodReceiptItem;

public interface InvGoodsReceiptItemDao extends GenericDao<InvGoodReceiptItem, Long> {

	public abstract List<InvGoodReceiptItem> findByInvGoodReceipt(Long id);

}