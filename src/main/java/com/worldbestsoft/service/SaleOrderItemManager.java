package com.worldbestsoft.service;

import java.util.List;

import com.worldbestsoft.model.SaleOrderItem;

public interface SaleOrderItemManager {

	public abstract List<SaleOrderItem> findBySaleOrder(Long id);

	public abstract SaleOrderItem get(Long id);

	public abstract SaleOrderItem save(SaleOrderItem object);

	public abstract void remove(Long id);

}