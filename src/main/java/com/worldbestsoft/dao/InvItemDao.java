package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.InvItem;

public interface InvItemDao extends GenericDao<InvItem, Long> {


	public abstract List<InvItem> checkDuplicate(String InvItemCode, Long id);

	public abstract InvItem findByInvItemCode(String InvItemCode);

	public abstract Integer querySize(InvItem criteria);

	public abstract List<InvItem> query(InvItem criteria, final int page, final int pageSize, final String sortColumn, final String order);

}