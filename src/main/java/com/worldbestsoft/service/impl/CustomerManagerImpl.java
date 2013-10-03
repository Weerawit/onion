package com.worldbestsoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.CustomerDao;
import com.worldbestsoft.dao.SearchException;
import com.worldbestsoft.model.Customer;
import com.worldbestsoft.service.CustomerManager;

@Service("customerManager")
public class CustomerManagerImpl implements CustomerManager {
	
	private CustomerDao customerDao;
	
	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	@Autowired
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CustomerManager#query(com.worldbestsoft.model.Customer, int, int, java.lang.String, java.lang.String)
	 */
	@Override
    public List<Customer> query(Customer criteria, int page, int pageSize, String sortColumn, String order) {
	    return customerDao.query(criteria, page, pageSize, sortColumn, order);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CustomerManager#querySize(com.worldbestsoft.model.Customer)
	 */
	@Override
    public Integer querySize(Customer criteria) {
	    return customerDao.querySize(criteria);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CustomerManager#get(java.lang.Long)
	 */
	@Override
    public Customer get(Long id) {
	    return customerDao.get(id);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CustomerManager#save(com.worldbestsoft.model.Customer)
	 */
	@Override
    public Customer save(Customer object) {
	    return customerDao.save(object);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CustomerManager#remove(java.lang.Long)
	 */
	@Override
    public void remove(Long id) {
	    customerDao.remove(id);
    }

	@Override
    public List<Customer> search(String searchTerm) throws SearchException {
	    return customerDao.search(searchTerm);
    }

	
	
	
}
