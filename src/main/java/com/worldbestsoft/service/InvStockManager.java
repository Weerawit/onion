package com.worldbestsoft.service;

import com.worldbestsoft.model.InvItemLevel;
import com.worldbestsoft.model.InvStock;

public interface InvStockManager {

	public abstract InvStock updateStock(InvItemLevel invItemLevel);

	public abstract InvStock findByInvItemCode(String invItemCode);

}