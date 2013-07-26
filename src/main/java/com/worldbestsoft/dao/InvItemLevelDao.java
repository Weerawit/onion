package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.InvItemLevel;
import com.worldbestsoft.model.RefType;

public interface InvItemLevelDao extends GenericDao<InvItemLevel, Long> {

	public abstract List<InvItemLevel> findByRefDocument(String refDocument, RefType refType);

	public abstract List<InvItemLevel> findByInvItemCode(String invItemCode);

}