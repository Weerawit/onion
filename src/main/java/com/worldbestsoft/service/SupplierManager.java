package com.worldbestsoft.service;

import java.util.List;

import com.worldbestsoft.dao.SearchException;
import com.worldbestsoft.model.Supplier;

public interface SupplierManager {

	public abstract List<Supplier> query(Supplier criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(Supplier criteria);

	public abstract List<Supplier> checkDuplicate(String code, Long id);

	public abstract Supplier findBySupplierCode(String supplierCode);

	public abstract List<Supplier> getAll();

	public abstract Supplier get(Long id);

	public abstract Supplier save(Supplier object);

	public abstract void remove(Long id);

	public abstract List<Supplier> search(String searchTerm) throws SearchException;

}