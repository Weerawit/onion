package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.Employee;

public interface EmployeeDao extends GenericDao<Employee, Long> {

	public abstract List<Employee> query(Employee criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(Employee criteria);

}