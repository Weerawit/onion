package com.worldbestsoft.model.criteria;

import java.math.BigDecimal;

public class SaleOrderSummaryReport {

	private String customerName;
	private String saleOrderNo;
	private BigDecimal totalPrice;
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
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	@Override
    public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("SaleOrderSummaryReport [customerName=");
	    builder.append(customerName);
	    builder.append(", saleOrderNo=");
	    builder.append(saleOrderNo);
	    builder.append(", totalPrice=");
	    builder.append(totalPrice);
	    builder.append("]");
	    return builder.toString();
    }
	
	
	
}
