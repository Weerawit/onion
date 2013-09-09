package com.worldbestsoft.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.DocumentNumberDao;
import com.worldbestsoft.model.DocumentNumber;

@Repository("documentNumberDao")
public class DocumentNumberDaoHibernate extends GenericDaoHibernate<DocumentNumber, Long> implements DocumentNumberDao {

	public DocumentNumberDaoHibernate() {
	    super(DocumentNumber.class);
    }
	
}
