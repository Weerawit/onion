package com.worldbestsoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.InvItemLevelDao;
import com.worldbestsoft.model.InvItemLevel;
import com.worldbestsoft.model.criteria.InvItemLevelCriteria;
import com.worldbestsoft.service.InvItemLevelManager;

@Service("invItemLevelManager")
public class InvItemLevelManagerImpl implements InvItemLevelManager {
	
	private InvItemLevelDao invItemLevelDao;

	public InvItemLevelDao getInvItemLevelDao() {
		return invItemLevelDao;
	}

	@Autowired
	public void setInvItemLevelDao(InvItemLevelDao invItemLevelDao) {
		this.invItemLevelDao = invItemLevelDao;
	}

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvItemLevelManager#querySize(com.worldbestsoft.model.criteria.InvItemLevelCriteria)
	 */
	@Override
    public Integer querySize(InvItemLevelCriteria criteria) {
	    return invItemLevelDao.querySize(criteria);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvItemLevelManager#query(com.worldbestsoft.model.criteria.InvItemLevelCriteria, int, int, java.lang.String, java.lang.String)
	 */
	@Override
    public List<InvItemLevel> query(InvItemLevelCriteria criteria, int page, int pageSize, String sortColumn, String order) {
	    return invItemLevelDao.query(criteria, page, pageSize, sortColumn, order);
    }
	
	
	
	
	
}
