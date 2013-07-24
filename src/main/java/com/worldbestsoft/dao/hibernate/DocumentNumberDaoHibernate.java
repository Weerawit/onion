package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.DocumentNumberDao;
import com.worldbestsoft.model.DocumentNumber;

@Repository("documentNumberDao")
public class DocumentNumberDaoHibernate extends GenericDaoHibernate<DocumentNumber, Long> implements DocumentNumberDao {

	public DocumentNumberDaoHibernate() {
	    super(DocumentNumber.class);
    }
	
	/* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.DocumentNumberDao#findByType(java.lang.String)
	 */
	@Override
    public DocumentNumber findByType(String type) {
		String hsql = "select o from DocumentNumber o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		hsql += " and o.type = :type";
		params.put("type", type);
		hsql += " order by o.type";
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		List<DocumentNumber> result = queryObj.list();
		if (null != result && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
}
