package com.worldbestsoft.model;

// Generated Sep 9, 2013 1:02:46 PM by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
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

/**
 * DocumentNumber generated by hbm2java
 */
@Entity
@Table(name = "document_number", catalog = "onion")
public class DocumentNumber implements java.io.Serializable {

	private Long internalNo;
	private String documentNo;
	private Date updateDate;

	public DocumentNumber() {
	}

	public DocumentNumber(String documentNo, Date updateDate) {
		this.documentNo = documentNo;
		this.updateDate = updateDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "internal_no", unique = true, nullable = false)
	public Long getInternalNo() {
		return this.internalNo;
	}

	public void setInternalNo(Long internalNo) {
		this.internalNo = internalNo;
	}

	@Column(name = "document_no", length = 45)
	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
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
