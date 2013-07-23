package com.worldbestsoft.service;

import java.util.List;

import com.worldbestsoft.model.InvItem;

public interface InvItemManager {
	
	public abstract InvItem get(Long id);

	public abstract List<InvItem> query(InvItem criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(InvItem criteria);

	public abstract void remove(Long id);

	public abstract InvItem save(InvItem value);

	public abstract List<InvItem> checkDuplicate(String invItemCode, Long id);

	public abstract InvItem findByInvItemCode(String InvItemCode);

	public abstract List<InvItem> getAll();

}
