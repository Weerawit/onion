package com.worldbestsoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.CatalogTypeDao;
import com.worldbestsoft.model.CatalogType;
import com.worldbestsoft.service.CatalogTypeManager;

@Service("catalogTypeManager")
public class CatalogTypeManagerImpl implements CatalogTypeManager {
	
	private CatalogTypeDao catalogTypeDao;
	
	public CatalogTypeDao getCatalogTypeDao() {
		return catalogTypeDao;
	}

	@Autowired
	public void setCatalogTypeDao(CatalogTypeDao catalogTypeDao) {
		this.catalogTypeDao = catalogTypeDao;
	}

	
	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CatalogTypeManager#get(java.lang.Long)
	 */
	@Override
    public CatalogType get(Long id) {
		return catalogTypeDao.get(id);
	}

	
	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CatalogTypeManager#checkDuplicate(java.lang.String, java.lang.Long)
	 */
	@Override
    public List<CatalogType> checkDuplicate(String catalogTypeCode, Long id) {
		return catalogTypeDao.checkDuplicate(catalogTypeCode, id);
	}

	
	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CatalogTypeManager#getAllCatalogType()
	 */
	@Override
    public List<CatalogType> getAllCatalogType() {
		return catalogTypeDao.getAll();
	}

	
	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CatalogTypeManager#save(com.worldbestsoft.model.CatalogType)
	 */
	@Override
    public CatalogType save(CatalogType value) {
		CatalogType obj = catalogTypeDao.save(value);
		return obj;
	}

	
	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CatalogTypeManager#findByCatalogTypeCode(java.lang.String)
	 */
	@Override
    public CatalogType findByCatalogTypeCode(String catalogTypeCode) {
		return catalogTypeDao.findByCatalogTypeCode(catalogTypeCode);
	}

	
	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CatalogTypeManager#remove(java.lang.Long)
	 */
	@Override
    public void remove(Long id) {
		catalogTypeDao.remove(id);
	}

	@Override
    public Integer querySize(CatalogType criteria) {
	    return catalogTypeDao.querySize(criteria);
    }

	@Override
    public List<CatalogType> query(CatalogType criteria, int page, int pageSize, String sortColumn, String order) {
	    return catalogTypeDao.query(criteria, page, pageSize, sortColumn, order);
    }
	
	

}
