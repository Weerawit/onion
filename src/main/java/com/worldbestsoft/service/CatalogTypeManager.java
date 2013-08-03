package com.worldbestsoft.service;

import java.util.List;

import com.worldbestsoft.model.CatalogType;

public interface CatalogTypeManager {

	public abstract CatalogType get(Long id);

	public abstract List<CatalogType> checkDuplicate(String catalogTypeCode, Long id);

	public abstract List<CatalogType> getAllCatalogType();

	public abstract CatalogType save(CatalogType value);

	public abstract CatalogType findByCatalogTypeCode(String catalogTypeCode);

	public abstract void remove(Long id);

	public abstract List<CatalogType> query(CatalogType criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(CatalogType criteria);

}