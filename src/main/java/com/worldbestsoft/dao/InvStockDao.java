package com.worldbestsoft.dao;

import com.worldbestsoft.model.InvStock;

public interface InvStockDao extends GenericDao<InvStock, Long> {

	public abstract InvStock findByInvItemCode(String invItemCode);

}