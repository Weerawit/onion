package com.worldbestsoft.dao;

import java.util.List;

import com.worldbestsoft.model.CatalogItem;

public interface CatalogItemDao extends GenericDao<CatalogItem, Long> {

	public abstract List<CatalogItem> findByCatalog(Long id);


}