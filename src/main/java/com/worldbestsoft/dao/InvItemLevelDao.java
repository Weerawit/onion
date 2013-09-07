package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.ConstantModel;
import com.worldbestsoft.model.InvItemLevel;

public interface InvItemLevelDao extends GenericDao<InvItemLevel, Long> {

	public abstract List<InvItemLevel> findByInvItemCode(String invItemCode);

	public abstract List<InvItemLevel> findByRefDocument(String refDocument, ConstantModel.RefType refType, ConstantModel.ItemSockTransactionType transactionType);

}