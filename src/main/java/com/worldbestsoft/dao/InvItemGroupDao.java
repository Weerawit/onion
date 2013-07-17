package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.InvItemGroup;

public interface InvItemGroupDao extends GenericDao<InvItemGroup, Long> {


	public abstract List<InvItemGroup> checkDuplicate(String InvItemGroupCode, Long id);

	public abstract InvItemGroup findByInvItemGroupCode(String InvItemGroupCode);

	public abstract Integer querySize(InvItemGroup criteria);

	public abstract List<InvItemGroup> query(InvItemGroup criteria, final int page, final int pageSize, final String sortColumn, final String order);

}