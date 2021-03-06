package com.worldbestsoft.model;

// Generated Sep 9, 2013 10:54:50 AM by Hibernate Tools 4.0.0

import java.math.BigDecimal;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

/**
 * SaleOrder generated by hbm2java
 */
@Entity
@Table(name = "sale_order", catalog = "onion")
@Indexed
public class SaleOrder implements java.io.Serializable {

	private Long id;
	private Customer customer;
	@IndexedEmbedded
	private DocumentNumber documentNumber;
	private String paymentType;
	private Date deliveryDate;
	private BigDecimal totalPrice;
	private BigDecimal paymentPaid;
	private String paymentStatus;
	private String status;
	private String cancelReason;
	private Date createDate;
	private String createUser;
	private Date updateDate;
	private String updateUser;
	private Set<SaleReceipt> saleReceipts = new HashSet<SaleReceipt>(0);
	private Set<SaleOrderItem> saleOrderItems = new HashSet<SaleOrderItem>(0);
	private Set<JobOrder> jobOrders = new HashSet<JobOrder>(0);

	public SaleOrder() {
	}

	public SaleOrder(Customer customer) {
		this.customer = customer;
	}

	public SaleOrder(Customer customer, DocumentNumber documentNumber, String paymentType, Date deliveryDate, BigDecimal totalPrice, BigDecimal paymentPaid, String paymentStatus, String status, String cancelReason, Date createDate, String createUser, Date updateDate, String updateUser, Set<SaleReceipt> saleReceipts, Set<SaleOrderItem> saleOrderItems, Set<JobOrder> jobOrders) {
		this.customer = customer;
		this.documentNumber = documentNumber;
		this.paymentType = paymentType;
		this.deliveryDate = deliveryDate;
		this.totalPrice = totalPrice;
		this.paymentPaid = paymentPaid;
		this.paymentStatus = paymentStatus;
		this.status = status;
		this.cancelReason = cancelReason;
		this.createDate = createDate;
		this.createUser = createUser;
		this.updateDate = updateDate;
		this.updateUser = updateUser;
		this.saleReceipts = saleReceipts;
		this.saleOrderItems = saleOrderItems;
		this.jobOrders = jobOrders;
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
	@JoinColumn(name = "customer_id", nullable = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "document_number_internal_no")
	public DocumentNumber getDocumentNumber() {
		return this.documentNumber;
	}

	public void setDocumentNumber(DocumentNumber documentNumber) {
		this.documentNumber = documentNumber;
	}

	@Column(name = "payment_type", length = 3)
	public String getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "delivery_date", length = 19)
	public Date getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@Column(name = "total_price", precision = 10)
	public BigDecimal getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Column(name = "payment_paid", precision = 10)
	public BigDecimal getPaymentPaid() {
		return this.paymentPaid;
	}

	public void setPaymentPaid(BigDecimal paymentPaid) {
		this.paymentPaid = paymentPaid;
	}

	@Column(name = "payment_status", length = 3)
	public String getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Column(name = "status", length = 3)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "cancelReason")
	public String getCancelReason() {
		return this.cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "saleOrder")
	public Set<SaleReceipt> getSaleReceipts() {
		return this.saleReceipts;
	}

	public void setSaleReceipts(Set<SaleReceipt> saleReceipts) {
		this.saleReceipts = saleReceipts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "saleOrder")
	public Set<SaleOrderItem> getSaleOrderItems() {
		return this.saleOrderItems;
	}

	public void setSaleOrderItems(Set<SaleOrderItem> saleOrderItems) {
		this.saleOrderItems = saleOrderItems;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "saleOrder")
	public Set<JobOrder> getJobOrders() {
		return this.jobOrders;
	}

	public void setJobOrders(Set<JobOrder> jobOrders) {
		this.jobOrders = jobOrders;
	}

	@Override
    public String toString() {
	    final int maxLen = 10;
	    StringBuilder builder = new StringBuilder();
	    builder.append("SaleOrder [id=");
	    builder.append(id);
	    builder.append(", customer=");
	    builder.append(customer);
	    builder.append(", documentNumber=");
	    builder.append(documentNumber);
	    builder.append(", paymentType=");
	    builder.append(paymentType);
	    builder.append(", deliveryDate=");
	    builder.append(deliveryDate);
	    builder.append(", totalPrice=");
	    builder.append(totalPrice);
	    builder.append(", paymentPaid=");
	    builder.append(paymentPaid);
	    builder.append(", paymentStatus=");
	    builder.append(paymentStatus);
	    builder.append(", status=");
	    builder.append(status);
	    builder.append(", cancelReason=");
	    builder.append(cancelReason);
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
	    SaleOrder other = (SaleOrder) obj;
	    if (id == null) {
		    if (other.id != null)
			    return false;
	    } else if (!id.equals(other.id))
		    return false;
	    return true;
    }

	
}
