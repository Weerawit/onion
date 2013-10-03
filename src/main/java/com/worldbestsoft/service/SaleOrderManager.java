package com.worldbestsoft.service;

import java.util.Collection;
import java.util.List;

import com.worldbestsoft.dao.SearchException;
import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.model.SaleOrderItem;
import com.worldbestsoft.model.criteria.SaleOrderCriteria;

public interface SaleOrderManager {

	public abstract List<SaleOrder> query(SaleOrderCriteria criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(SaleOrderCriteria criteria);

	public abstract SaleOrder findBySaleOrderNo(String saleOrderNo);

	public abstract List<SaleOrderItem> findBySaleOrder(Long id);

	public abstract SaleOrder save(SaleOrder saleOrder, Collection<SaleOrderItem> newSaleOrderItemList);

	public abstract SaleOrder get(Long id);

	public abstract void updateSaleOrderPayment(SaleOrder saleOrder);

	public abstract void remove(Long id, String user, String cancelReason);

	public SaleOrder delivery(Long saleOrderId, String user);

	public abstract List<SaleOrder> search(String searchTerm) throws SearchException;

}