package com.worldbestsoft.model.criteria;

import java.util.Date;

import com.worldbestsoft.model.InvGoodsReceipt;

public class InvGoodsReceiptCriteria extends InvGoodsReceipt implements java.io.Serializable {
	/**
	 * 
	 */
    private static final long serialVersionUID = -8371153053950130747L;
    
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
	    builder.append("InvGoodsReceiptCriteria [receiptDateFrom=");
	    builder.append(receiptDateFrom);
	    builder.append(", receiptDateTo=");
	    builder.append(receiptDateTo);
	    builder.append("]");
	    return builder.toString();
    }
	
}
