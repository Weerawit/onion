package com.worldbestsoft.service;

import org.springframework.context.ApplicationEvent;

public class InvItemLevelChangedEvent extends ApplicationEvent {

	public InvItemLevelChangedEvent(Object source) {
		super(source);
	}

}
