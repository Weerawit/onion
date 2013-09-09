package com.worldbestsoft.dao;

import com.worldbestsoft.model.DocumentSeq;

public interface DocumentSeqDao extends GenericDao<DocumentSeq, Long> {

	public abstract DocumentSeq findByType(String type);

}