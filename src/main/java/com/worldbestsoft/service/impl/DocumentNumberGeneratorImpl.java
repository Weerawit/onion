package com.worldbestsoft.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.worldbestsoft.dao.DocumentNumberDao;
import com.worldbestsoft.dao.DocumentSeqDao;
import com.worldbestsoft.model.DocumentNumber;
import com.worldbestsoft.model.DocumentSeq;
import com.worldbestsoft.model.InvGoodsMovement;
import com.worldbestsoft.model.InvGoodsReceipt;
import com.worldbestsoft.model.JobOrder;
import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.model.SaleReceipt;
import com.worldbestsoft.service.DocumentNumberFormatter;
import com.worldbestsoft.service.DocumentNumberGenerator;

@Service("documentNumberGenerator")
public class DocumentNumberGeneratorImpl implements DocumentNumberGenerator {
	
	private static final Map<String, Object> lockObject = new HashMap<String, Object>();
	
	private DocumentSeqDao documentSeqDao;
	private DocumentNumberDao documentNumberDao;
	

	public DocumentNumberGeneratorImpl() {
		lockObject.put(InvGoodsReceipt.class.getName(), new ReentrantLock());
		lockObject.put(InvGoodsMovement.class.getName(), new ReentrantLock());
		lockObject.put(SaleOrder.class.getName(), new ReentrantLock());
		lockObject.put(SaleReceipt.class.getName(), new ReentrantLock());
		lockObject.put(JobOrder.class.getName(), new ReentrantLock());
	}
	
	public DocumentSeqDao getDocumentSeqDao() {
		return documentSeqDao;
	}

	@Autowired
	public void setDocumentSeqDao(DocumentSeqDao documentSeqDao) {
		this.documentSeqDao = documentSeqDao;
	}

	public DocumentNumberDao getDocumentNumberDao() {
		return documentNumberDao;
	}

	@Autowired
	public void setDocumentNumberDao(DocumentNumberDao documentNumberDao) {
		this.documentNumberDao = documentNumberDao;
	}

	@Override
    public DocumentNumber newDocumentNumber() {
		DocumentNumber documentNumber = new DocumentNumber();
		documentNumber.setDocumentNo("TEMPORARY");
		documentNumber.setUpdateDate(new Date());
		return documentNumberDao.save(documentNumber);
	}
	
	@Override
    public void nextDocumentNumber(Class klass, Long internalNo, DocumentNumberFormatter formatter) {
		Object lock = lockObject.get(klass.getName());
		synchronized (lock) {
			DocumentSeq seq = documentSeqDao.findByType(klass.getSimpleName());
			if (null != seq) {
				Long nextValue = seq.getCurrentVal() + 1;
				seq.setCurrentVal(nextValue);
				seq.setUpdateDate(new Date());
				documentSeqDao.save(seq);
				
				String docNumber = formatter.format(nextValue);
				DocumentNumber documentNumber = documentNumberDao.get(internalNo);
				documentNumber.setDocumentNo(docNumber);
				documentNumberDao.save(documentNumber);
			}
        }
	}
}
