package com.worldbestsoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.EmployeeDao;
import com.worldbestsoft.model.Employee;
import com.worldbestsoft.service.EmployeeManager;

@Service("employeeManager")
public class EmployeeManagerImpl implements EmployeeManager {
	
	private EmployeeDao employeeDao;
	
	
	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	@Autowired
	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.EmployeeManager#query(com.worldbestsoft.model.Employee, int, int, java.lang.String, java.lang.String)
	 */
	@Override
    public List<Employee> query(Employee criteria, int page, int pageSize, String sortColumn, String order) {
	    return employeeDao.query(criteria, page, pageSize, sortColumn, order);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.EmployeeManager#querySize(com.worldbestsoft.model.Employee)
	 */
	@Override
    public Integer querySize(Employee criteria) {
	    return employeeDao.querySize(criteria);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.EmployeeManager#getAll()
	 */
	@Override
    public List<Employee> getAll() {
	    return employeeDao.getAll();
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.EmployeeManager#get(java.lang.Long)
	 */
	@Override
    public Employee get(Long id) {
	    return employeeDao.get(id);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.EmployeeManager#save(com.worldbestsoft.model.Employee)
	 */
	@Override
    public Employee save(Employee object) {
	    return employeeDao.save(object);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.EmployeeManager#remove(java.lang.Long)
	 */
	@Override
    public void remove(Long id) {
	    employeeDao.remove(id);
    }


	
	
	

}
