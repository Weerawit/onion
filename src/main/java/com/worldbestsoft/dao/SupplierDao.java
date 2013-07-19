package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.Supplier;

public interface SupplierDao extends GenericDao<Supplier, Long> {

	public abstract List<Supplier> query(Supplier criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(Supplier criteria);

	public abstract List<Supplier> checkDuplicate(String code, Long id);

	public abstract Supplier findBySupplierCode(String supplierCode);

}