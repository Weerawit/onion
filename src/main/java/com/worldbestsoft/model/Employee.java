package com.worldbestsoft.model;

// Generated Jul 16, 2013 3:46:49 PM by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Employee generated by hbm2java
 */
@Entity
@Table(name = "employee", catalog = "onion")
public class Employee implements java.io.Serializable {

	private int id;
	private String firstName;
	private String lastName;
	private String nickName;
	private Integer age;
	private String idCardNo;
	private String address;
	private BigDecimal wage;
	private String memo;

	public Employee() {
	}

	public Employee(int id) {
		this.id = id;
	}

	public Employee(int id, String firstName, String lastName, String nickName, Integer age, String idCardNo, String address, BigDecimal wage, String memo) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.nickName = nickName;
		this.age = age;
		this.idCardNo = idCardNo;
		this.address = address;
		this.wage = wage;
		this.memo = memo;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "first_name", length = 50)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", length = 50)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "nick_name", length = 50)
	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Column(name = "age")
	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "id_card_no", length = 50)
	public String getIdCardNo() {
		return this.idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	@Column(name = "address")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "wage", precision = 10)
	public BigDecimal getWage() {
		return this.wage;
	}

	public void setWage(BigDecimal wage) {
		this.wage = wage;
	}

	@Column(name = "memo")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
