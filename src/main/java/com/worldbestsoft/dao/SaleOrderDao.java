package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.model.criteria.SaleOrderCriteria;

public interface SaleOrderDao extends GenericDao<SaleOrder, Long> {

	public abstract List<SaleOrder> query(SaleOrderCriteria criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(SaleOrderCriteria criteria);

	public abstract SaleOrder findBySaleOrderNo(String saleOrderNo);

}