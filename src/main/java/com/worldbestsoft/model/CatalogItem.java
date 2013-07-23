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
 * CatalogItem generated by hbm2java
 */
@Entity
@Table(name = "catalog_item", catalog = "onion")
public class CatalogItem implements java.io.Serializable {

	private Long id;
	private Catalog catalog;
	private InvItem invItem;
	private String name;
	private BigDecimal qty;
	private Date createDate;
	private String createUser;
	private Date updateDate;
	private String updateUser;

	public CatalogItem() {
	}

	public CatalogItem(Catalog catalog, InvItem invItem) {
		this.catalog = catalog;
		this.invItem = invItem;
	}

	public CatalogItem(Catalog catalog, InvItem invItem, String name, BigDecimal qty, Date createDate, String createUser, Date updateDate, String updateUser) {
		this.catalog = catalog;
		this.invItem = invItem;
		this.name = name;
		this.qty = qty;
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
	@JoinColumn(name = "catalog_id", nullable = false)
	public Catalog getCatalog() {
		return this.catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inv_item_id", nullable = false)
	public InvItem getInvItem() {
		return this.invItem;
	}

	public void setInvItem(InvItem invItem) {
		this.invItem = invItem;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "qty", precision = 10)
	public BigDecimal getQty() {
		return this.qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
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

}
