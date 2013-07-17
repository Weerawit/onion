package com.worldbestsoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.InvItemGroupDao;
import com.worldbestsoft.model.InvItemGroup;
import com.worldbestsoft.service.InvItemGroupManager;

@Service("invItemGroupManager")
public class InvItemGroupManagerImpl implements InvItemGroupManager {
	
	private InvItemGroupDao invItemGroupDao;
	
	public InvItemGroupDao getInvItemGroupDao() {
		return invItemGroupDao;
	}

	@Autowired
	public void setInvItemGroupDao(InvItemGroupDao invItemGroupDao) {
		this.invItemGroupDao = invItemGroupDao;
	}

	
	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvItemGroupManager#get(java.lang.Long)
	 */
	@Override
    public InvItemGroup get(Long id) {
		return invItemGroupDao.get(id);
	}

	
	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvItemGroupManager#checkDuplicate(java.lang.String, java.lang.Long)
	 */
	@Override
    public List<InvItemGroup> checkDuplicate(String invItemGroupCode, Long id) {
		return invItemGroupDao.checkDuplicate(invItemGroupCode, id);
	}

	
	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvItemGroupManager#getAllInvItemGroup()
	 */
	@Override
    public List<InvItemGroup> getAllInvItemGroup() {
		return invItemGroupDao.getAll();
	}

	
	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvItemGroupManager#save(com.worldbestsoft.model.InvItemGroup)
	 */
	@Override
    public InvItemGroup save(InvItemGroup value) {
		InvItemGroup obj = invItemGroupDao.save(value);
		return obj;
	}

	
	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvItemGroupManager#findByInvItemGroupCode(java.lang.String)
	 */
	@Override
    public InvItemGroup findByInvItemGroupCode(String invItemGroupCode) {
		return invItemGroupDao.findByInvItemGroupCode(invItemGroupCode);
	}

	
	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvItemGroupManager#remove(java.lang.Long)
	 */
	@Override
    public void remove(Long id) {
		invItemGroupDao.remove(id);
	}

	@Override
    public Integer querySize(InvItemGroup criteria) {
	    return invItemGroupDao.querySize(criteria);
    }

	@Override
    public List<InvItemGroup> query(InvItemGroup criteria, int page, int pageSize, String sortColumn, String order) {
	    return invItemGroupDao.query(criteria, page, pageSize, sortColumn, order);
    }
	
	

}
