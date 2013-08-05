package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.InvGoodsMovementItem;

public interface InvGoodsMovementItemDao extends GenericDao<InvGoodsMovementItem, Long> {

	public abstract List<InvGoodsMovementItem> findByInvGoodMovement(Long id);

}