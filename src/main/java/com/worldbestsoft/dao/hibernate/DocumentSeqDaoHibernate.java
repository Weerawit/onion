package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.DocumentSeqDao;
import com.worldbestsoft.model.DocumentSeq;

@Repository("documentSeqDao")
public class DocumentSeqDaoHibernate extends GenericDaoHibernate<DocumentSeq, Long> implements DocumentSeqDao {

	public DocumentSeqDaoHibernate() {
	    super(DocumentSeq.class);
    }
	
	@Override
    public DocumentSeq findByType(String type) {
		String hsql = "select o from DocumentSeq o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		hsql += " and o.type = :type";
		params.put("type", type);
		hsql += " order by o.type";
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		List<DocumentSeq> result = queryObj.list();
		if (null != result && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
}
