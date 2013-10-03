package com.worldbestsoft.service;

import java.util.Collection;
import java.util.List;

import com.worldbestsoft.dao.SearchException;
import com.worldbestsoft.model.Catalog;
import com.worldbestsoft.model.CatalogItem;

public interface CatalogManager {

	public abstract List<Catalog> query(Catalog criteria, int page, int pageSize, String sortColumn, String order);

	public abstract Integer querySize(Catalog criteria);

	public abstract boolean checkDuplicate(String code, Long id);

	public abstract Catalog findByCatalogCode(String catalogCode);

	public abstract Catalog get(Long id);

	public abstract void remove(Long id);

	public abstract Catalog save(Catalog catalog, Collection<CatalogItem> newCatalogItemList);

	public abstract List<Catalog> search(String searchTerm) throws SearchException;

}