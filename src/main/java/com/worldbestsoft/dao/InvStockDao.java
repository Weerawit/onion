package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.InvStock;

public interface InvStockDao extends GenericDao<InvStock, Long> {

	public abstract InvStock findByInvItemCode(String invItemCode);

	public abstract Integer querySize(InvStock criteria);

	public abstract List<InvStock> query(InvStock criteria, final int page, final int pageSize, final String sortColumn, final String order);

}