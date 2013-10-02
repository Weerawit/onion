package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.ConstantModel;
import com.worldbestsoft.model.InvItemLevel;
import com.worldbestsoft.model.criteria.InvItemLevelCriteria;

public interface InvItemLevelDao extends GenericDao<InvItemLevel, Long> {

	public abstract List<InvItemLevel> findByInvItemCode(String invItemCode);

	public abstract List<InvItemLevel> findByRefDocument(String refDocument, ConstantModel.RefType refType, ConstantModel.ItemStockTransactionType transactionType);

	public abstract Integer querySize(InvItemLevelCriteria criteria);

	public abstract List<InvItemLevel> query(InvItemLevelCriteria criteria, final int page, final int pageSize, final String sortColumn, final String order);

}