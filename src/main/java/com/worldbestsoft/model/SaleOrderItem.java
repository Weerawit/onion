package com.worldbestsoft.model;

// Generated Sep 6, 2013 7:28:59 PM by Hibernate Tools 4.0.0

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
 * SaleOrderItem generated by hbm2java
 */
@Entity
@Table(name = "sale_order_item", catalog = "onion")
public class SaleOrderItem implements java.io.Serializable {

	private Long id;
	private SaleOrder saleOrder;
	private Catalog catalog;
	private BigDecimal qty;
	private BigDecimal pricePerUnit;
	private BigDecimal price;
	private Date createDate;
	private String createUser;
	private Date updateDate;
	private String updateUser;

	public SaleOrderItem() {
	}

	public SaleOrderItem(SaleOrder saleOrder, Catalog catalog) {
		this.saleOrder = saleOrder;
		this.catalog = catalog;
	}

	public SaleOrderItem(SaleOrder saleOrder, Catalog catalog, BigDecimal qty, BigDecimal pricePerUnit, BigDecimal price, Date createDate, String createUser, Date updateDate, String updateUser) {
		this.saleOrder = saleOrder;
		this.catalog = catalog;
		this.qty = qty;
		this.pricePerUnit = pricePerUnit;
		this.price = price;
		this.createDate = createDate;
		this.createUser = createUser;
		this.updateDate = updateDate;
		this.updateUser = updateUser;
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
	@JoinColumn(name = "sale_order_id", nullable = false)
	public SaleOrder getSaleOrder() {
		return this.saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "catalog_id", nullable = false)
	public Catalog getCatalog() {
		return this.catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	@Column(name = "qty", precision = 10)
	public BigDecimal getQty() {
		return this.qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	@Column(name = "price_per_unit", precision = 10)
	public BigDecimal getPricePerUnit() {
		return this.pricePerUnit;
	}

	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	@Column(name = "price", precision = 10)
	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "create_user", length = 50)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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
	    builder.append("SaleOrderItem [id=");
	    builder.append(id);
	    builder.append(", saleOrder=");
	    builder.append(saleOrder);
	    builder.append(", catalog=");
	    builder.append(catalog);
	    builder.append(", qty=");
	    builder.append(qty);
	    builder.append(", pricePerUnit=");
	    builder.append(pricePerUnit);
	    builder.append(", price=");
	    builder.append(price);
	    builder.append(", createDate=");
	    builder.append(createDate);
	    builder.append(", createUser=");
	    builder.append(createUser);
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
	    result = prime * result + ((id == null) ? 0 : id.hashCode());
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
	    SaleOrderItem other = (SaleOrderItem) obj;
	    if (id == null) {
		    if (other.id != null)
			    return false;
	    } else if (!id.equals(other.id))
		    return false;
	    return true;
    }

	
}
