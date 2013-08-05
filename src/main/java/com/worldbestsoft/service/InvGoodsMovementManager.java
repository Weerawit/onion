package com.worldbestsoft.service;

import java.util.Collection;
import java.util.List;

import com.worldbestsoft.model.InvGoodsMovement;
import com.worldbestsoft.model.InvGoodsMovementItem;
import com.worldbestsoft.model.criteria.InvGoodsMovementCriteria;

public interface InvGoodsMovementManager {

	public abstract InvGoodsMovement save(InvGoodsMovement invGoodsMovement, Collection<InvGoodsMovementItem> newInvGoodsMovementItemList);

	public abstract InvGoodsMovement saveToStock(InvGoodsMovement invGoodsMovement);

	public abstract void remove(Long id);

	public abstract Integer querySize(InvGoodsMovementCriteria criteria);

	public abstract List<InvGoodsMovement> query(InvGoodsMovementCriteria criteria, int page, int pageSize, String sortColumn, String order);

	public abstract InvGoodsMovement get(Long id);

}