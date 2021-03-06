package com.worldbestsoft.model;

// Generated Sep 9, 2013 10:21:54 AM by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * InvStock generated by hbm2java
 */
@Entity
@Table(name = "inv_stock", catalog = "onion")
public class InvStock implements java.io.Serializable {

	private Long invItemId;
	private InvItem invItem;
	private BigDecimal qty;
	private BigDecimal qtyAvailable;
	private Date updateDate;
	private String updateUser;

	public InvStock() {
	}

	public InvStock(InvItem invItem) {
		this.invItem = invItem;
	}

	public InvStock(InvItem invItem, BigDecimal qty, BigDecimal qtyAvailable, Date updateDate, String updateUser) {
		this.invItem = invItem;
		this.qty = qty;
		this.qtyAvailable = qtyAvailable;
		this.updateDate = updateDate;
		this.updateUser = updateUser;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "invItem"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "inv_item_id", unique = true, nullable = false)
	public Long getInvItemId() {
		return this.invItemId;
	}

	public void setInvItemId(Long invItemId) {
		this.invItemId = invItemId;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
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

	@Column(name = "qty_available", precision = 10)
	public BigDecimal getQtyAvailable() {
		return this.qtyAvailable;
	}

	public void setQtyAvailable(BigDecimal qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "update_user", length = 50)
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Override
    public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("InvStock [invItemId=");
	    builder.append(invItemId);
//	    builder.append(", invItem=");
//	    builder.append(invItem);
	    builder.append(", qty=");
	    builder.append(qty);
	    builder.append(", qtyAvailable=");
	    builder.append(qtyAvailable);
	    builder.append(", updateDate=");
	    builder.append(updateDate);
	    builder.append(", updateUser=");
	    builder.append(updateUser);
	    builder.append("]");
	    return builder.toString();
    }

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((invItemId == null) ? 0 : invItemId.hashCode());
	    return result;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    InvStock other = (InvStock) obj;
	    if (invItemId == null) {
		    if (other.invItemId != null)
			    return false;
	    } else if (!invItemId.equals(other.invItemId))
		    return false;
	    return true;
    }

	
}
