package com.worldbestsoft.service;

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

}