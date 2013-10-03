package com.worldbestsoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.SearchException;
import com.worldbestsoft.dao.SupplierDao;
import com.worldbestsoft.model.Supplier;
import com.worldbestsoft.service.SupplierManager;

@Service("supplierManager")
public class SupplierManagerImpl implements SupplierManager {
	
	private SupplierDao supplierDao;
	
	public SupplierDao getSupplierDao() {
		return supplierDao;
	}

	@Autowired
	public void setSupplierDao(SupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SupplierManager#query(com.worldbestsoft.model.Supplier, int, int, java.lang.String, java.lang.String)
	 */
	@Override
    public List<Supplier> query(Supplier criteria, int page, int pageSize, String sortColumn, String order) {
	    return supplierDao.query(criteria, page, pageSize, sortColumn, order);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SupplierManager#querySize(com.worldbestsoft.model.Supplier)
	 */
	@Override
    public Integer querySize(Supplier criteria) {
	    return supplierDao.querySize(criteria);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SupplierManager#checkDuplicate(java.lang.String, java.lang.Long)
	 */
	@Override
    public List<Supplier> checkDuplicate(String code, Long id) {
	    return supplierDao.checkDuplicate(code, id);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SupplierManager#findBySupplierCode(java.lang.String)
	 */
	@Override
    public Supplier findBySupplierCode(String supplierCode) {
	    return supplierDao.findBySupplierCode(supplierCode);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SupplierManager#getAll()
	 */
	@Override
    public List<Supplier> getAll() {
	    return supplierDao.getAll();
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SupplierManager#get(java.lang.Long)
	 */
	@Override
    public Supplier get(Long id) {
	    return supplierDao.get(id);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SupplierManager#save(com.worldbestsoft.model.Supplier)
	 */
	@Override
    public Supplier save(Supplier object) {
	    return supplierDao.save(object);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SupplierManager#remove(java.lang.Long)
	 */
	@Override
    public void remove(Long id) {
	    supplierDao.remove(id);
    }

	@Override
    public List<Supplier> search(String searchTerm) throws SearchException {
	    return supplierDao.search(searchTerm);
    }

}
