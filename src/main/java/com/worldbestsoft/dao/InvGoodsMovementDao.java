package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.InvGoodsMovement;
import com.worldbestsoft.model.criteria.InvGoodsMovementCriteria;

public interface InvGoodsMovementDao extends GenericDao<InvGoodsMovement, Long> {

	public abstract List<InvGoodsMovement> query(InvGoodsMovementCriteria criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(InvGoodsMovementCriteria criteria);

}