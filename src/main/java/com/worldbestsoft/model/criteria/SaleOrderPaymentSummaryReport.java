package com.worldbestsoft.model.criteria;

import java.math.BigDecimal;

public class SaleOrderPaymentSummaryReport {

	private String customerName;
	private String saleOrderNo;
	private BigDecimal nonPaidAmount;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getSaleOrderNo() {
		return saleOrderNo;
	}
	public void setSaleOrderNo(String saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
	}
	public BigDecimal getNonPaidAmount() {
		return nonPaidAmount;
	}
	public void setNonPaidAmount(BigDecimal nonPaidAmount) {
		this.nonPaidAmount = nonPaidAmount;
	}
	@Override
    public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("SaleOrderPaymentSummaryReport [customerName=");
	    builder.append(customerName);
	    builder.append(", saleOrderNo=");
	    builder.append(saleOrderNo);
	    builder.append(", nonPaidAmount=");
	    builder.append(nonPaidAmount);
	    builder.append("]");
	    return builder.toString();
    }
	
	
	
}
