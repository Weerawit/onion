package com.worldbestsoft.service;

import java.util.List;

import com.worldbestsoft.model.CatalogItem;

public interface CatalogItemManager {

	public abstract List<CatalogItem> findByCatalog(Long id);

	public abstract CatalogItem get(Long id);

	public abstract CatalogItem save(CatalogItem object);

	public abstract void remove(Long id);

}