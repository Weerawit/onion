package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.Customer;

public interface CustomerDao extends GenericDao<Customer, Long> {

	public abstract List<Customer> query(Customer criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(Customer criteria);

}