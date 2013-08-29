package com.worldbestsoft.model;

public enum RefType {
	
	GOOD_RECEIPT("GR"), GOOD_MOVEMENT("GM"), SALE_ORDER("SA");
	
	private final String code;
	
	private RefType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
