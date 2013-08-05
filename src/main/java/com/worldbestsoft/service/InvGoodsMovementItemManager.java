package com.worldbestsoft.service;

import com.worldbestsoft.model.InvGoodsMovementItem;

public interface InvGoodsMovementItemManager {

	public abstract InvGoodsMovementItem get(Long id);

	public abstract InvGoodsMovementItem save(InvGoodsMovementItem object);

	public abstract void remove(Long id);

}