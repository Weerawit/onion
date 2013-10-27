package com.worldbestsoft.model;

// Generated Sep 4, 2013 12:25:17 PM by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * InvItem generated by hbm2java
 */
@Entity
@Table(name = "inv_item", catalog = "onion")
@Indexed
public class InvItem implements java.io.Serializable {

	private Long id;
	private InvItemGroup invItemGroup;

	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String code;
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String name;
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String description;
	private Date createDate;
	private String createUser;
	private Date updateDate;
	private String updateUser;
	private InvStock invStock;
	private Set<Catalog> catalogs = new HashSet<Catalog>(0);
	private Set<InvGoodsMovementItem> invGoodsMovementItems = new HashSet<InvGoodsMovementItem>(0);
	private Set<InvGoodsReceiptItem> invGoodsReceiptItems = new HashSet<InvGoodsReceiptItem>(0);
	private Set<InvItemLevel> invItemLevels = new HashSet<InvItemLevel>(0);
	private Set<CatalogItem> catalogItems = new HashSet<CatalogItem>(0);

	public InvItem() {
	}

	public InvItem(InvItemGroup invItemGroup, String code) {
		this.invItemGroup = invItemGroup;
		this.code = code;
	}

	public InvItem(InvItemGroup invItemGroup, String code, String name, String description, Date createDate, String createUser, Date updateDate, String updateUser, InvStock invStock, Set<Catalog> catalogs, Set<InvGoodsMovementItem> invGoodsMovementItems, Set<InvGoodsReceiptItem> invGoodsReceiptItems, Set<InvItemLevel> invItemLevels, Set<CatalogItem> catalogItems) {
		this.invItemGroup = invItemGroup;
		this.code = code;
		this.name = name;
		this.description = description;
		this.createDate = createDate;
		this.createUser = createUser;
		this.updateDate = updateDate;
		this.updateUser = updateUser;
		this.invStock = invStock;
		this.catalogs = catalogs;
		this.invGoodsMovementItems = invGoodsMovementItems;
		this.invGoodsReceiptItems = invGoodsReceiptItems;
		this.invItemLevels = invItemLevels;
		this.catalogItems = catalogItems;
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
	@JoinColumn(name = "inv_item_group_id", nullable = false)
	public InvItemGroup getInvItemGroup() {
		return this.invItemGroup;
	}

	public void setInvItemGroup(InvItemGroup invItemGroup) {
		this.invItemGroup = invItemGroup;
	}

	@Column(name = "code", nullable = false, length = 10)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "invItem")
	public InvStock getInvStock() {
		return this.invStock;
	}

	public void setInvStock(InvStock invStock) {
		this.invStock = invStock;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invItem")
	public Set<Catalog> getCatalogs() {
		return this.catalogs;
	}

	public void setCatalogs(Set<Catalog> catalogs) {
		this.catalogs = catalogs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invItem")
	public Set<InvGoodsMovementItem> getInvGoodsMovementItems() {
		return this.invGoodsMovementItems;
	}

	public void setInvGoodsMovementItems(Set<InvGoodsMovementItem> invGoodsMovementItems) {
		this.invGoodsMovementItems = invGoodsMovementItems;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invItem")
	public Set<InvGoodsReceiptItem> getInvGoodsReceiptItems() {
		return this.invGoodsReceiptItems;
	}

	public void setInvGoodsReceiptItems(Set<InvGoodsReceiptItem> invGoodsReceiptItems) {
		this.invGoodsReceiptItems = invGoodsReceiptItems;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invItem")
	public Set<InvItemLevel> getInvItemLevels() {
		return this.invItemLevels;
	}

	public void setInvItemLevels(Set<InvItemLevel> invItemLevels) {
		this.invItemLevels = invItemLevels;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invItem")
	public Set<CatalogItem> getCatalogItems() {
		return this.catalogItems;
	}

	public void setCatalogItems(Set<CatalogItem> catalogItems) {
		this.catalogItems = catalogItems;
	}

	@Override
    public String toString() {
	    final int maxLen = 10;
	    StringBuilder builder = new StringBuilder();
	    builder.append("InvItem [id=");
	    builder.append(id);
	    builder.append(", invItemGroup=");
	    builder.append(invItemGroup);
	    builder.append(", code=");
	    builder.append(code);
	    builder.append(", name=");
	    builder.append(name);
	    builder.append(", description=");
	    builder.append(description);
	    builder.append(", createDate=");
	    builder.append(createDate);
	    builder.append(", createUser=");
	    builder.append(createUser);
	    builder.append(", updateDate=");
	    builder.append(updateDate);
	    builder.append(", updateUser=");
	    builder.append(updateUser);
	    builder.append(", invStock=");
	    builder.append(invStock);
	    builder.append(", catalogs=");
	    builder.append(catalogs != null ? toString(catalogs, maxLen) : null);
	    builder.append(", invGoodsMovementItems=");
	    builder.append(invGoodsMovementItems != null ? toString(invGoodsMovementItems, maxLen) : null);
	    builder.append(", invGoodsReceiptItems=");
	    builder.append(invGoodsReceiptItems != null ? toString(invGoodsReceiptItems, maxLen) : null);
	    builder.append(", invItemLevels=");
	    builder.append(invItemLevels != null ? toString(invItemLevels, maxLen) : null);
	    builder.append(", catalogItems=");
	    builder.append(catalogItems != null ? toString(catalogItems, maxLen) : null);
	    builder.append("]");
	    return builder.toString();
    }

	private String toString(Collection<?> collection, int maxLen) {
	    StringBuilder builder = new StringBuilder();
	    builder.append("[");
	    int i = 0;
	    for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
		    if (i > 0)
			    builder.append(", ");
		    builder.append(iterator.next());
	    }
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
	    InvItem other = (InvItem) obj;
	    if (id == null) {
		    if (other.id != null)
			    return false;
	    } else if (!id.equals(other.id))
		    return false;
	    return true;
    }

	
}
