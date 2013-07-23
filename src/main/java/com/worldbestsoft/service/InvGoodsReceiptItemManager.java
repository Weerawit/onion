package com.worldbestsoft.service;

import java.util.List;

import com.worldbestsoft.model.InvGoodReceiptItem;

public interface InvGoodsReceiptItemManager {

	public abstract List<InvGoodReceiptItem> getAll();

	public abstract InvGoodReceiptItem get(Long id);

	public abstract InvGoodReceiptItem save(InvGoodReceiptItem object);

	public abstract void remove(Long id);

}