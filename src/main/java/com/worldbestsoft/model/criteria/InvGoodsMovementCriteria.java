package com.worldbestsoft.model.criteria;

import java.util.Date;

import com.worldbestsoft.model.InvGoodsMovement;

public class InvGoodsMovementCriteria extends InvGoodsMovement implements java.io.Serializable {

	/**
	 * 
	 */
    private static final long serialVersionUID = 4687323242714497647L;

	private Date movementDateFrom;
	
	private Date movementDateTo;

	public Date getMovementDateFrom() {
		return movementDateFrom;
	}

	public void setMovementDateFrom(Date movementDateFrom) {
		this.movementDateFrom = movementDateFrom;
	}

	public Date getMovementDateTo() {
		return movementDateTo;
	}

	public void setMovementDateTo(Date movementDateTo) {
		this.movementDateTo = movementDateTo;
	}
	
	
	
}
