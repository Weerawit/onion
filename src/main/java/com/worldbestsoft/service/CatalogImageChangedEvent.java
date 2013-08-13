package com.worldbestsoft.service;

import org.springframework.context.ApplicationEvent;

public class CatalogImageChangedEvent extends ApplicationEvent {

	public CatalogImageChangedEvent(Object source) {
		super(source);
	}

}
