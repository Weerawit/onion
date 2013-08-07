package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.CatalogItemDao;
import com.worldbestsoft.model.CatalogItem;

@Repository("catalogItemDao")
public class CatalogItemDaoHibernate extends GenericDaoHibernate<CatalogItem, Long> implements CatalogItemDao {

	public CatalogItemDaoHibernate() {
	    super(CatalogItem.class);
    }
	

    @Override
    public List<CatalogItem> findByCatalog(Long id) {
		String hsql = "select o from CatalogItem o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		hsql += " and o.catalog.id = :id";
		params.put("id", id);
		hsql += " order by o.id";
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return queryObj.list();
	} 


}
