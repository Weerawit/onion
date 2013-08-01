package com.worldbestsoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.InvItemDao;
import com.worldbestsoft.dao.SearchException;
import com.worldbestsoft.model.InvItem;
import com.worldbestsoft.service.InvItemManager;

@Service("invItemManager")
public class InvItemManagerImpl implements InvItemManager {
	
	private InvItemDao invItemDao;
	
	
	public InvItemDao getInvItemDao() {
		return invItemDao;
	}

	@Autowired
	public void setInvItemDao(InvItemDao invItemDao) {
		this.invItemDao = invItemDao;
	}
	
	@Override
    public InvItem get(Long id) {
		return invItemDao.get(id);
	}

	@Override
    public List<InvItem> checkDuplicate(String invItemCode, Long id) {
		return invItemDao.checkDuplicate(invItemCode, id);
	}

	
    @Override
    public InvItem save(InvItem value) {
		InvItem obj = invItemDao.save(value);
		return obj;
	}

	
    @Override
    public void remove(Long id) {
		invItemDao.remove(id);
	}

	@Override
    public Integer querySize(InvItem criteria) {
	    return invItemDao.querySize(criteria);
    }

	@Override
    public List<InvItem> query(InvItem criteria, int page, int pageSize, String sortColumn, String order) {
	    return invItemDao.query(criteria, page, pageSize, sortColumn, order);
    }
	
	@Override
    public InvItem findByInvItemCode(String InvItemCode) {
	    return invItemDao.findByInvItemCode(InvItemCode);
    }

	@Override
    public List<InvItem> getAll() {
	    return invItemDao.getAll();
    }

	@Override
    public List<InvItem> search(String searchTerm) throws SearchException {
	    return invItemDao.search(searchTerm);
    }
	
	
}
