package com.worldbestsoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.worldbestsoft.model.InvItemLevel;

@Component("invItemChangedListener")
public class InvItemChangedListener implements ApplicationListener<InvItemLevelChangedEvent> {
	
	private InvStockManager invStockManager;
	
	public InvStockManager getInvStockManager() {
		return invStockManager;
	}

	@Autowired
	public void setInvStockManager(InvStockManager invStockManager) {
		this.invStockManager = invStockManager;
	}

	@Override
    public void onApplicationEvent(InvItemLevelChangedEvent event) {
		if (event.getSource() instanceof InvItemLevel) {
	        InvItemLevel invItemLevel = (InvItemLevel) event.getSource();
	        invStockManager.updateStock(invItemLevel);
        }
    }
}