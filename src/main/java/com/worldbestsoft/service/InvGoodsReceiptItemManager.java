package com.worldbestsoft.service;

import java.util.List;

import com.worldbestsoft.model.InvGoodsReceiptItem;

public interface InvGoodsReceiptItemManager {

	public abstract List<InvGoodsReceiptItem> getAll();

	public abstract InvGoodsReceiptItem get(Long id);

	public abstract InvGoodsReceiptItem save(InvGoodsReceiptItem object);

	public abstract void remove(Long id);

}