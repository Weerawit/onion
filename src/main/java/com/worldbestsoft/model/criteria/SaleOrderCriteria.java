package com.worldbestsoft.model.criteria;

import java.util.Date;

import com.worldbestsoft.model.SaleOrder;

public class SaleOrderCriteria extends SaleOrder {

	private Date deliveryDateFrom;
	private Date deliveryDateTo;
	public Date getDeliveryDateFrom() {
		return deliveryDateFrom;
	}
	public void setDeliveryDateFrom(Date deliveryDateFrom) {
		this.deliveryDateFrom = deliveryDateFrom;
	}
	public Date getDeliveryDateTo() {
		return deliveryDateTo;
	}
	public void setDeliveryDateTo(Date deliveryDateTo) {
		this.deliveryDateTo = deliveryDateTo;
	}
	@Override
    public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("SaleOrderCriteria [deliveryDateFrom=");
	    builder.append(deliveryDateFrom);
	    builder.append(", deliveryDateTo=");
	    builder.append(deliveryDateTo);
	    builder.append("]");
	    return builder.toString();
    }
	
	
}
