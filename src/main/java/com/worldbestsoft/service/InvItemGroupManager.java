package com.worldbestsoft.service;

import java.util.List;

import com.worldbestsoft.model.InvItemGroup;

public interface InvItemGroupManager {

	public abstract InvItemGroup get(Long id);

	public abstract List<InvItemGroup> checkDuplicate(String invItemGroupCode, Long id);

	public abstract List<InvItemGroup> getAllInvItemGroup();

	public abstract InvItemGroup save(InvItemGroup value);

	public abstract InvItemGroup findByInvItemGroupCode(String invItemGroupCode);

	public abstract void remove(Long id);

	public abstract List<InvItemGroup> query(InvItemGroup criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(InvItemGroup criteria);

}