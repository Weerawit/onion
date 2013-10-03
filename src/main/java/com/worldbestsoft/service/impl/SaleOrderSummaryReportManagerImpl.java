package com.worldbestsoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.SaleOrderSummaryReportDao;
import com.worldbestsoft.model.criteria.SaleOrderCriteria;
import com.worldbestsoft.model.criteria.SaleOrderSummaryReport;
import com.worldbestsoft.service.SaleOrderSummaryReportManager;

@Service("saleOrderSummaryReportManager")
public class SaleOrderSummaryReportManagerImpl implements SaleOrderSummaryReportManager {
	private SaleOrderSummaryReportDao saleOrderSummaryReportDao;

	public SaleOrderSummaryReportDao getSaleOrderSummaryReportDao() {
		return saleOrderSummaryReportDao;
	}

	@Autowired
	public void setSaleOrderSummaryReportDao(SaleOrderSummaryReportDao saleOrderSummaryReportDao) {
		this.saleOrderSummaryReportDao = saleOrderSummaryReportDao;
	}

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SaleOrderSummaryReportManager#query(com.worldbestsoft.model.criteria.SaleOrderCriteria, int, int, java.lang.String, java.lang.String)
	 */
	@Override
    public List<SaleOrderSummaryReport> query(SaleOrderCriteria criteria, int page, int pageSize, String sortColumn, String order) {
	    return saleOrderSummaryReportDao.query(criteria, page, pageSize, sortColumn, order);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SaleOrderSummaryReportManager#querySize(com.worldbestsoft.model.criteria.SaleOrderCriteria)
	 */
	@Override
    public Integer querySize(SaleOrderCriteria criteria) {
	    return saleOrderSummaryReportDao.querySize(criteria);
    }
	
}
