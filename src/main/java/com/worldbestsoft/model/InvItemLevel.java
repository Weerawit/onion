package com.worldbestsoft.model;

// Generated Jul 23, 2013 9:54:03 PM by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * InvItemLevel generated by hbm2java
 */
@Entity
@Table(name = "inv_item_level", catalog = "onion")
public class InvItemLevel implements java.io.Serializable {

	private Long id;
	private InvItem invItem;
	private Date transactionDate;
	private BigDecimal qtyInStock;
	private String refDocument;
	private String refType;

	public InvItemLevel() {
	}

	public InvItemLevel(InvItem invItem, Date transactionDate) {
		this.invItem = invItem;
		this.transactionDate = transactionDate;
	}

	public InvItemLevel(InvItem invItem, Date transactionDate, BigDecimal qtyInStock, String refDocument, String refType) {
		this.invItem = invItem;
		this.transactionDate = transactionDate;
		this.qtyInStock = qtyInStock;
		this.refDocument = refDocument;
		this.refType = refType;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inv_item_id", nullable = false)
	public InvItem getInvItem() {
		return this.invItem;
	}

	public void setInvItem(InvItem invItem) {
		this.invItem = invItem;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "transaction_date", nullable = false, length = 19)
	public Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Column(name = "qty_in_stock", precision = 10)
	public BigDecimal getQtyInStock() {
		return this.qtyInStock;
	}

	public void setQtyInStock(BigDecimal qtyInStock) {
		this.qtyInStock = qtyInStock;
	}

	@Column(name = "ref_document", length = 50)
	public String getRefDocument() {
		return this.refDocument;
	}

	public void setRefDocument(String refDocument) {
		this.refDocument = refDocument;
	}

	@Column(name = "ref_type", length = 3)
	public String getRefType() {
		return this.refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

}
