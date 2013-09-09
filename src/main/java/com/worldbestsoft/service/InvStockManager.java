package com.worldbestsoft.service;

import java.util.List;

import com.worldbestsoft.model.ConstantModel;
import com.worldbestsoft.model.InvItemLevel;
import com.worldbestsoft.model.InvStock;
/**
 * Main class to handle stock in & out
 * @author Weerawit
 *
 */
public interface InvStockManager {

	public abstract InvStock updateStock(InvItemLevel invItemLevel);

	public abstract InvStock findByInvItemCode(String invItemCode);

	public abstract void save(InvStock object);

	public abstract List<InvStock> query(InvStock criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(InvStock criteria);

	public abstract InvStock get(Long id);

	public abstract void cancelReserved(String documentNumber, ConstantModel.RefType documentType, String user);

	public abstract void commitReserved(String documentNumber, ConstantModel.RefType documentType, String user);

}