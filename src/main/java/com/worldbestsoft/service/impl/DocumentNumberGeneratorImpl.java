package com.worldbestsoft.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.worldbestsoft.dao.DocumentNumberDao;
import com.worldbestsoft.model.DocumentNumber;
import com.worldbestsoft.model.InvGoodsMovement;
import com.worldbestsoft.model.InvGoodsReceipt;
import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.model.SaleReceipt;
import com.worldbestsoft.service.DocumentNumberGenerator;
import com.worldbestsoft.service.DocumentNumberGeneratorException;

@Service("documentNumberGenerator")
public class DocumentNumberGeneratorImpl implements DocumentNumberGenerator {
	
	private static final Map<String, Object> lockObject = new HashMap<String, Object>();
	
	private DocumentNumberDao documentNoDao;
	
//	private long lockTimeout = 100;

	public DocumentNumberGeneratorImpl() {
		lockObject.put(InvGoodsReceipt.class.getName(), new ReentrantLock());
		lockObject.put(InvGoodsMovement.class.getName(), new ReentrantLock());
		lockObject.put(SaleOrder.class.getName(), new ReentrantLock());
		lockObject.put(SaleReceipt.class.getName(), new ReentrantLock());
	}

//	public String nextDocumentNumber(Class klass) throws InterruptedException {
//		Lock lock = lockObject.get(klass);
////		while (lock.tryLock(lockTimeout, TimeUnit.MICROSECONDS)) {
//		if (lock.tryLock()) {
//			try {
//				Thread.sleep(1000);
//				return "ABC" + Thread.currentThread().getName() + " : " + index++;
//			} finally {
//				lock.unlock();
//			}
//		}
//		return null;
//	}
	
	public DocumentNumberDao getDocumentNumberDao() {
		return documentNoDao;
	}

	@Autowired
	public void setDocumentNumberDao(DocumentNumberDao documentNoDao) {
		this.documentNoDao = documentNoDao;
	}

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.DocumentNumberGenerator#nextDocumentNumber(java.lang.Class)
	 */
	@Override
    @SuppressWarnings("rawtypes")
    public Long nextDocumentNumber(Class klass) throws DocumentNumberGeneratorException {
		Object lock = lockObject.get(klass.getName());
		synchronized (lock) {
			DocumentNumber documentNo = documentNoDao.findByType(klass.getSimpleName());
			if (null != documentNo) {
				Long nextValue = documentNo.getCurrentVal() + 1;
				documentNo.setCurrentVal(nextValue);
				documentNo.setUpdateDate(new Date());
				documentNoDao.save(documentNo);
				return nextValue;
			}
        }
		throw new DocumentNumberGeneratorException("Cannot generate running no for Class := " + klass.getSimpleName());
	}
	
//	public static void main(String[] args) throws InterruptedException {
//		
//		final DocumentNumberGeneratorImpl impl = new DocumentNumberGeneratorImpl();
//		for (int i = 0 ; i < 50; i++) {
//			Thread aThread = new Thread("T" + i) {
//				public void run() {
//					try {
//	                    System.out.println(getName() + ":" + impl.nextDocumentNumber(InvGoodsReceipt.class));
//                    } catch (InterruptedException e) {
//	                    e.printStackTrace();
//                    }
//				}
//			};
//			aThread.start();
//			Thread.sleep(1000);
//		}
//	}
}
