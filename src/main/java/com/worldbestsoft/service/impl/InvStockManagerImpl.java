package com.worldbestsoft.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.worldbestsoft.dao.InvStockDao;
import com.worldbestsoft.model.InvItemLevel;
import com.worldbestsoft.model.InvStock;
import com.worldbestsoft.service.InvStockManager;

@Service("invStockManager")
public class InvStockManagerImpl implements InvStockManager {
	
	private InvStockDao invStockDao;

	public InvStockDao getInvStockDao() {
		return invStockDao;
	}

	@Autowired
	public void setInvStockDao(InvStockDao invStockDao) {
		this.invStockDao = invStockDao;
	}
	
	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvStockManager#updateStock(com.worldbestsoft.model.InvItemLevel)
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public InvStock updateStock(InvItemLevel invItemLevel) {
		InvStock invStock = invStockDao.findByInvItemCode(invItemLevel.getInvItem().getCode());
		if (null == invStock) {
			invStock = new InvStock();
			invStock.setInvItem(invItemLevel.getInvItem());
			invStock.setQty(BigDecimal.ZERO);
		}
		
		BigDecimal newQty = invStock.getQty().add(invItemLevel.getQtyInStock());
		invStock.setQty(newQty);
		return invStockDao.save(invStock);
	}
	
}
