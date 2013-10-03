package com.worldbestsoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.SaleOrderPaymentSummaryReportDao;
import com.worldbestsoft.model.criteria.SaleOrderCriteria;
import com.worldbestsoft.model.criteria.SaleOrderPaymentSummaryReport;
import com.worldbestsoft.service.SaleOrderPaymentSummaryReportManager;

@Service("saleOrderPaymentSummaryReportManager")
public class SaleOrderPaymentSummaryReportManagerImpl implements SaleOrderPaymentSummaryReportManager {
	private SaleOrderPaymentSummaryReportDao saleOrderPaymentSummaryReportDao;

	public SaleOrderPaymentSummaryReportDao getSaleOrderPaymentSummaryReportDao() {
		return saleOrderPaymentSummaryReportDao;
	}

	@Autowired
	public void setSaleOrderPaymentSummaryReportDao(SaleOrderPaymentSummaryReportDao saleOrderPaymentSummaryReportDao) {
		this.saleOrderPaymentSummaryReportDao = saleOrderPaymentSummaryReportDao;
	}

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SaleOrderPaymentSummaryReportManager#query(com.worldbestsoft.model.criteria.SaleOrderCriteria, int, int, java.lang.String, java.lang.String)
	 */
	@Override
    public List<SaleOrderPaymentSummaryReport> query(SaleOrderCriteria criteria, int page, int pageSize, String sortColumn, String order) {
	    return saleOrderPaymentSummaryReportDao.query(criteria, page, pageSize, sortColumn, order);
    }

	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.SaleOrderPaymentSummaryReportManager#querySize(com.worldbestsoft.model.criteria.SaleOrderCriteria)
	 */
	@Override
    public Integer querySize(SaleOrderCriteria criteria) {
	    return saleOrderPaymentSummaryReportDao.querySize(criteria);
    }
	
}
