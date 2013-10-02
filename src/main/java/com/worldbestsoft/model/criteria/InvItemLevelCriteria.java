package com.worldbestsoft.model.criteria;

import java.math.BigDecimal;
import java.util.Date;

import com.worldbestsoft.model.DocumentNumber;
import com.worldbestsoft.model.InvItem;
import com.worldbestsoft.model.InvItemLevel;

public class InvItemLevelCriteria extends InvItemLevel {
	private Date transactionDateFrom;
	private Date transactionDateTo;
	public Date getTransactionDateFrom() {
		return transactionDateFrom;
	}
	public void setTransactionDateFrom(Date transactionDateFrom) {
		this.transactionDateFrom = transactionDateFrom;
	}
	public Date getTransactionDateTo() {
		return transactionDateTo;
	}
	public void setTransactionDateTo(Date transactionDateTo) {
		this.transactionDateTo = transactionDateTo;
	}
	
	
}
