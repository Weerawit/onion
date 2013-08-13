package com.worldbestsoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.CatalogItemDao;
import com.worldbestsoft.model.CatalogItem;
import com.worldbestsoft.service.CatalogItemManager;

@Service("catalogItemManager")
public class CatalogItemManagerImpl implements CatalogItemManager {

	private CatalogItemDao catalogItemDao;

	public CatalogItemDao getCatalogItemDao() {
		return catalogItemDao;
	}

	@Autowired
	public void setCatalogItemDao(CatalogItemDao catalogItemDao) {
		this.catalogItemDao = catalogItemDao;
	}

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CatalogItemManager#findByCatalog(java.lang.Long)
	 */
	@Override
    public List<CatalogItem> findByCatalog(Long id) {
	    return catalogItemDao.findByCatalog(id);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CatalogItemManager#get(java.lang.Long)
	 */
	@Override
    public CatalogItem get(Long id) {
	    return catalogItemDao.get(id);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CatalogItemManager#save(com.worldbestsoft.model.CatalogItem)
	 */
	@Override
    public CatalogItem save(CatalogItem object) {
	    return catalogItemDao.save(object);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CatalogItemManager#remove(java.lang.Long)
	 */
	@Override
    public void remove(Long id) {
	    catalogItemDao.remove(id);
    }
	
	
	
}
