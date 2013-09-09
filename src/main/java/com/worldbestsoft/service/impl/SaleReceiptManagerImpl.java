package com.worldbestsoft.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.SaleReceiptDao;
import com.worldbestsoft.model.ConstantModel;
import com.worldbestsoft.model.DocumentNumber;
import com.worldbestsoft.model.SaleReceipt;
import com.worldbestsoft.model.criteria.SaleReceiptCriteria;
import com.worldbestsoft.service.DocumentNumberFormatter;
import com.worldbestsoft.service.DocumentNumberGenerator;
import com.worldbestsoft.service.DocumentNumberGeneratorException;
import com.worldbestsoft.service.SaleReceiptChangedEvent;
import com.worldbestsoft.service.SaleReceiptManager;

@Service("saleReceiptManager")
public class SaleReceiptManagerImpl implements SaleReceiptManager, ApplicationContextAware {
	
	private SaleReceiptDao  saleReceiptDao;
	
	private DocumentNumberGenerator documentNumberGenerator;
	
	private String documentNumberFormat = "RE{0,number,00000}";
	
	private ApplicationContext context;

	public SaleReceiptDao getSaleReceiptDao() {
		return saleReceiptDao;
	}

	@Autowired
	public void setSaleReceiptDao(SaleReceiptDao saleReceiptDao) {
		this.saleReceiptDao = saleReceiptDao;
	}
	
	public DocumentNumberGenerator getDocumentNumberGenerator() {
		return documentNumberGenerator;
	}

	@Autowired
	public void setDocumentNumberGenerator(DocumentNumberGenerator documentNumberGenerator) {
		this.documentNumberGenerator = documentNumberGenerator;
	}
	
	public String getDocumentNumberFormat() {
		return documentNumberFormat;
	}

	public void setDocumentNumberFormat(String documentNumberFormat) {
		this.documentNumberFormat = documentNumberFormat;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.context = arg0;
	}

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SaleReceiptManager#query(com.worldbestsoft.model.criteria.SaleReceiptCriteria, int, int, java.lang.String, java.lang.String)
	 */
	@Override
    public List<SaleReceipt> query(SaleReceiptCriteria criteria, int page, int pageSize, String sortColumn, String order) {
	    return saleReceiptDao.query(criteria, page, pageSize, sortColumn, order);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SaleReceiptManager#querySize(com.worldbestsoft.model.criteria.SaleReceiptCriteria)
	 */
	@Override
    public Integer querySize(SaleReceiptCriteria criteria) {
	    return saleReceiptDao.querySize(criteria);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SaleReceiptManager#get(java.lang.Long)
	 */
	@Override
    public SaleReceipt get(Long id) {
	    return saleReceiptDao.get(id);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SaleReceiptManager#save(com.worldbestsoft.model.SaleReceipt)
	 */
	@Override
    public SaleReceipt save(SaleReceipt object) {
		if (null == object.getId()) {
			//save first , prevent rollback
			DocumentNumber documentNumber = documentNumberGenerator.newDocumentNumber();
			object.setDocumentNumber(documentNumber);
			object.setStatus(ConstantModel.SaleReceiptStatus.ACTIVE.getCode());
			object = saleReceiptDao.save(object);
			//get document number
        		documentNumberGenerator.nextDocumentNumber(SaleReceipt.class, documentNumber.getInternalNo(), new DocumentNumberFormatter() {
					
					@Override
					public String format(Long nextSeq) {
						return MessageFormat.format(documentNumberFormat, nextSeq);
					}
				});
		} else {
			object = saleReceiptDao.save(object);
		}
		
		SaleReceiptChangedEvent event = new SaleReceiptChangedEvent(object);
		context.publishEvent(event);
		return object;
    }

    @Override
    public void remove(Long id, String user, String cancelReason) {
		SaleReceipt saleReceipt = saleReceiptDao.get(id);
		saleReceipt.setUpdateDate(new Date());
		saleReceipt.setUpdateUser(user);
		saleReceipt.setStatus(ConstantModel.SaleReceiptStatus.CANCEL.getCode()); //cancel
		saleReceipt.setCancelReason(cancelReason);
		saleReceipt = saleReceiptDao.save(saleReceipt);
		SaleReceiptChangedEvent event = new SaleReceiptChangedEvent(saleReceipt);
		context.publishEvent(event);
    }

	@Override
    public List<SaleReceipt> findBySaleOrderNo(String saleOrderNo) {
	    return saleReceiptDao.findBySaleOrderNo(saleOrderNo);
    }
	
}
