package com.worldbestsoft.service;

import org.springframework.context.ApplicationEvent;

public class SaleReceiptChangedEvent extends ApplicationEvent {

	public SaleReceiptChangedEvent(Object source) {
		super(source);
	}

}
