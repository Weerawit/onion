package com.worldbestsoft.service;

import java.util.List;

import com.worldbestsoft.model.Employee;

public interface EmployeeManager {

	public abstract List<Employee> query(Employee criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(Employee criteria);

	public abstract List<Employee> getAll();

	public abstract Employee get(Long id);

	public abstract Employee save(Employee object);

	public abstract void remove(Long id);

}