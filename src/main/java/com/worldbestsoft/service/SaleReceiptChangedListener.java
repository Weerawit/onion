package com.worldbestsoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.worldbestsoft.model.SaleReceipt;

@Component("saleReceiptChangedListener")
public class SaleReceiptChangedListener implements ApplicationListener<SaleReceiptChangedEvent> {

	private SaleOrderManager saleOrderManager;
	
	public SaleOrderManager getSaleOrderManager() {
		return saleOrderManager;
	}

	@Autowired
	public void setSaleOrderManager(SaleOrderManager saleOrderManager) {
		this.saleOrderManager = saleOrderManager;
	}


	@Override
    public void onApplicationEvent(SaleReceiptChangedEvent event) {
		if (event.getSource() instanceof SaleReceipt) {
			SaleReceipt saleReceipt = (SaleReceipt) event.getSource();
			saleOrderManager.updateSaleOrderPayment(saleReceipt.getSaleOrder());
        }
    }
}
