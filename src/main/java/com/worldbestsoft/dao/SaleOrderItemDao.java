package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.SaleOrderItem;

public interface SaleOrderItemDao extends GenericDao<SaleOrderItem, Long>{

	public abstract List<SaleOrderItem> findBySaleOrder(Long id);

}