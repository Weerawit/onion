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
	
	
	
}
