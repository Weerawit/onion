package com.worldbestsoft.model;

// Generated Jul 23, 2013 9:54:03 PM by Hibernate Tools 4.0.0

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * InvItemGroup generated by hbm2java
 */
@Entity
@Table(name = "inv_item_group", catalog = "onion", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class InvItemGroup implements java.io.Serializable {

	private Long id;
	private String code;
	private String name;
	private Date createDate;
	private String createUser;
	private Date updateDate;
	private String updateUser;
	private Set<InvItem> invItems = new HashSet<InvItem>(0);

	public InvItemGroup() {
	}

	public InvItemGroup(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public InvItemGroup(String code, String name, Date createDate, String createUser, Date updateDate, String updateUser, Set<InvItem> invItems) {
		this.code = code;
		this.name = name;
		this.createDate = createDate;
		this.createUser = createUser;
		this.updateDate = updateDate;
		this.updateUser = updateUser;
		this.invItems = invItems;
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

	@Column(name = "code", unique = true, nullable = false, length = 10)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invItemGroup")
	public Set<InvItem> getInvItems() {
		return this.invItems;
	}

	public void setInvItems(Set<InvItem> invItems) {
		this.invItems = invItems;
	}

	@Override
    public String toString() {
	    final int maxLen = 10;
	    StringBuilder builder = new StringBuilder();
	    builder.append("InvItemGroup [id=");
	    builder.append(id);
	    builder.append(", code=");
	    builder.append(code);
	    builder.append(", name=");
	    builder.append(name);
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
	    InvItemGroup other = (InvItemGroup) obj;
	    if (id == null) {
		    if (other.id != null)
			    return false;
	    } else if (!id.equals(other.id))
		    return false;
	    return true;
    }
	
	

}
