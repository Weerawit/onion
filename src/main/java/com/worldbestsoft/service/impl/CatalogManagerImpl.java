package com.worldbestsoft.service.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.Constants;
import com.worldbestsoft.dao.CatalogDao;
import com.worldbestsoft.dao.CatalogItemDao;
import com.worldbestsoft.dao.InvItemDao;
import com.worldbestsoft.dao.InvItemGroupDao;
import com.worldbestsoft.model.Catalog;
import com.worldbestsoft.model.CatalogItem;
import com.worldbestsoft.model.InvItem;
import com.worldbestsoft.model.InvItemGroup;
import com.worldbestsoft.service.CatalogManager;
import com.worldbestsoft.service.InvItemManager;

@Service("catalogManager")
public class CatalogManagerImpl implements CatalogManager {
	
	private CatalogDao catalogDao;
	private InvItemDao invItemDao;
	private InvItemGroupDao invItemGroupDao;
	private CatalogItemDao catalogItemDao;
	private InvItemManager invItemManager;
	
	public CatalogDao getCatalogDao() {
		return catalogDao;
	}

	@Autowired
	public void setCatalogDao(CatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}
	
	public InvItemDao getInvItemDao() {
		return invItemDao;
	}

	@Autowired
	public void setInvItemDao(InvItemDao invItemDao) {
		this.invItemDao = invItemDao;
	}
	
	public CatalogItemDao getCatalogItemDao() {
		return catalogItemDao;
	}

	@Autowired
	public void setCatalogItemDao(CatalogItemDao catalogItemDao) {
		this.catalogItemDao = catalogItemDao;
	}

	public InvItemGroupDao getInvItemGroupDao() {
		return invItemGroupDao;
	}

	@Autowired
	public void setInvItemGroupDao(InvItemGroupDao invItemGroupDao) {
		this.invItemGroupDao = invItemGroupDao;
	}
	
	public InvItemManager getInvItemManager() {
		return invItemManager;
	}

	@Autowired
	public void setInvItemManager(InvItemManager invItemManager) {
		this.invItemManager = invItemManager;
	}

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CatalogManager#query(com.worldbestsoft.model.Catalog, int, int, java.lang.String, java.lang.String)
	 */
	@Override
    public List<Catalog> query(Catalog criteria, int page, int pageSize, String sortColumn, String order) {
	    return catalogDao.query(criteria, page, pageSize, sortColumn, order);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CatalogManager#querySize(com.worldbestsoft.model.Catalog)
	 */
	@Override
    public Integer querySize(Catalog criteria) {
	    return catalogDao.querySize(criteria);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CatalogManager#checkDuplicate(java.lang.String, java.lang.Long)
	 */
	@Override
    public boolean checkDuplicate(String code, Long id) {
	    List<Catalog> resultList = catalogDao.checkDuplicate(code, id);
	    List<InvItem> resultList2 = invItemDao.checkDuplicate(code, id);
	    return resultList.size() > 0 || resultList2.size() > 0;
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CatalogManager#findByCatalogCode(java.lang.String)
	 */
	@Override
    public Catalog findByCatalogCode(String catalogCode) {
	    return catalogDao.findByCatalogCode(catalogCode);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CatalogManager#get(java.lang.Long)
	 */
	@Override
    public Catalog get(Long id) {
	    return catalogDao.get(id);
    }

    @Override
    public Catalog save(Catalog catalog, Collection<CatalogItem> newCatalogItemList) {
    	 	
		if (null != catalog.getId()) {
			//update
			List<CatalogItem> oldCatalogItemList = catalogItemDao.findByCatalog(catalog.getId());
			// delete if not in the new list.
			for (CatalogItem catalogItem : oldCatalogItemList) {
				CatalogItem foundcatalogItem = (CatalogItem) CollectionUtils.find(newCatalogItemList, new BeanPropertyValueEqualsPredicate("invItem.code", catalogItem.getInvItem().getCode()));
				if (null == foundcatalogItem) {
					// delete
					catalogItemDao.remove(catalogItem.getId());
				}
			}
			
			// add or update new list
			if (null != newCatalogItemList) {
				for (CatalogItem catalogItem : newCatalogItemList) {
					CatalogItem foundCatalogItem = (CatalogItem) CollectionUtils.find(oldCatalogItemList, new BeanPropertyValueEqualsPredicate("invItem.code", catalogItem.getInvItem().getCode()));
					if (null == foundCatalogItem) {
						catalogItem.setCatalog(catalog);
						catalogItemDao.save(catalogItem);
					} else {
						// update old catalog item
						foundCatalogItem.setInvItem(catalogItem.getInvItem());
						foundCatalogItem.setName(catalogItem.getName());
						foundCatalogItem.setQty(catalogItem.getQty());
						catalogItemDao.save(foundCatalogItem);
					}
				}
			}
			
			catalog = catalogDao.save(catalog);
		} else {
			InvItemGroup invItemGroup = invItemGroupDao.findByInvItemGroupCode(Constants.CATALOG_INV_ITEM_GROUP_CODE);
			//create invItem for it's catalog
			InvItem invItem = new InvItem();
			invItem.setInvItemGroup(invItemGroup);
			invItem.setCode(catalog.getCode());
			invItem.setName(catalog.getName());
			invItem.setCreateDate(catalog.getCreateDate());
			invItem.setCreateUser(catalog.getCreateUser());
			invItem.setDescription("Auto create from catalog");
			//Save Catalog as in stock (item)
			invItem = invItemManager.save(invItem);
			catalog.setInvItem(invItem);
			
			Catalog catalogSave = catalogDao.save(catalog);
			if (null != newCatalogItemList) {
				for (CatalogItem catalogItem : newCatalogItemList) {
					catalogItem.setCatalog(catalogSave);
					catalogItemDao.save(catalogItem);
				}
			}
			catalog = catalogDao.save(catalogSave);
		}
		
	    return catalog;
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.CatalogManager#remove(java.lang.Long)
	 */
	@Override
    public void remove(Long id) {
		Catalog catalog = catalogDao.get(id);
		if (null != catalog.getCatalogItems()) {
			for (CatalogItem catalogItem : catalog.getCatalogItems()) {
				catalogItemDao.remove(catalogItem.getId());
			}
		}
	    catalogDao.remove(id);
	    invItemDao.remove(catalog.getInvItem().getId());
    }

	
	
}
