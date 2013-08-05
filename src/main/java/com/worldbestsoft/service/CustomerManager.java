package com.worldbestsoft.service;

import java.util.List;

import com.worldbestsoft.model.Customer;

public interface CustomerManager {

	public abstract List<Customer> query(Customer criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(Customer criteria);

	public abstract Customer get(Long id);

	public abstract Customer save(Customer object);

	public abstract void remove(Long id);

}