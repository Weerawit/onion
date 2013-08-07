package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.Catalog;

public interface CatalogDao extends GenericDao<Catalog, Long> {

	public abstract List<Catalog> query(Catalog criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(Catalog criteria);

	public abstract List<Catalog> checkDuplicate(String code, Long id);

	public abstract Catalog findByCatalogCode(String catalogCode);

}