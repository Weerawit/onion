package com.worldbestsoft.service;

import java.util.List;

import com.worldbestsoft.model.InvItemLevel;
import com.worldbestsoft.model.criteria.InvItemLevelCriteria;

public interface InvItemLevelManager {

	public abstract Integer querySize(InvItemLevelCriteria criteria);

	public abstract List<InvItemLevel> query(InvItemLevelCriteria criteria, int page, int pageSize, String sortColumn, String order);

}