package com.worldbestsoft.model.criteria;

import java.math.BigDecimal;

public class JobOrderSummaryReport {

	private String firstName;
	private String lastName;
	private BigDecimal cost;
	private String status;
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
    public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("JobOrderSummaryReport [firstName=");
	    builder.append(firstName);
	    builder.append(", lastName=");
	    builder.append(lastName);
	    builder.append(", cost=");
	    builder.append(cost);
	    builder.append(", status=");
	    builder.append(status);
	    builder.append("]");
	    return builder.toString();
    }
	
	
}
