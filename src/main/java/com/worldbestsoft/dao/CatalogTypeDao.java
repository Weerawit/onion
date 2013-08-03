package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.CatalogType;

public interface CatalogTypeDao extends GenericDao<CatalogType, Long> {


	public abstract List<CatalogType> checkDuplicate(String CatalogTypeCode, Long id);

	public abstract CatalogType findByCatalogTypeCode(String CatalogTypeCode);

	public abstract Integer querySize(CatalogType criteria);

	public abstract List<CatalogType> query(CatalogType criteria, final int page, final int pageSize, final String sortColumn, final String order);

}