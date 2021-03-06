package com.worldbestsoft.model.criteria;

import java.math.BigDecimal;
import java.util.Date;

import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.model.SaleReceipt;

public class SaleReceiptCriteria extends SaleReceipt {

	private Date receiptDateFrom;
	private Date receiptDateTo;
	public Date getReceiptDateFrom() {
		return receiptDateFrom;
	}
	public void setReceiptDateFrom(Date receiptDateFrom) {
		this.receiptDateFrom = receiptDateFrom;
	}
	public Date getReceiptDateTo() {
		return receiptDateTo;
	}
	public void setReceiptDateTo(Date receiptDateTo) {
		this.receiptDateTo = receiptDateTo;
	}
	@Override
    public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("SaleReceiptCriteria [receiptDateFrom=");
	    builder.append(receiptDateFrom);
	    builder.append(", receiptDateTo=");
	    builder.append(receiptDateTo);
	    builder.append("]");
	    return builder.toString();
    }
	
	
	
}
