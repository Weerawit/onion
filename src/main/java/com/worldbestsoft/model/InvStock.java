package com.worldbestsoft.model;

// Generated Jul 29, 2013 7:18:05 PM by Hibernate Tools 4.0.0

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
 * InvStock generated by hbm2java
 */
@Entity
@Table(name = "inv_stock", catalog = "onion")
public class InvStock implements java.io.Serializable {

	private Long id;
	private InvItem invItem;
	private BigDecimal qty;
	private Date updateDate;

	public InvStock() {
	}

	public InvStock(InvItem invItem) {
		this.invItem = invItem;
	}

	public InvStock(InvItem invItem, BigDecimal qty, Date updateDate) {
		this.invItem = invItem;
		this.qty = qty;
		this.updateDate = updateDate;
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

	@Column(name = "qty", precision = 10)
	public BigDecimal getQty() {
		return this.qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
