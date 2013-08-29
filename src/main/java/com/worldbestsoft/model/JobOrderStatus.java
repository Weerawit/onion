package com.worldbestsoft.model;

public enum JobOrderStatus {
	NEW("NEW"), INPROGRESS("INPROGRESS"), DONE("DONE"), DELIVERY("DELIVERY");
	
	private final String code;
	
	private JobOrderStatus(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
