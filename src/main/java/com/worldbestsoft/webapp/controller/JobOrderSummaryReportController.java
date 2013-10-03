package com.worldbestsoft.webapp.controller;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.worldbestsoft.model.Catalog;
import com.worldbestsoft.model.DocumentNumber;
import com.worldbestsoft.model.Employee;
import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.model.criteria.JobOrderCriteria;
import com.worldbestsoft.service.JobOrderSummaryReportManager;
import com.worldbestsoft.service.LookupManager;

@Controller
@RequestMapping("/report/jobOrderSummaryReport*")
public class JobOrderSummaryReportController extends BaseFormController {
	
	private static final String[] DATE_PATTERN = new String[] {"dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy"};
	
	private JobOrderSummaryReportManager jobOrderSummaryReportManager;
	private LookupManager lookupManager;
	public LookupManager getLookupManager() {
		return lookupManager;
	}

	@Autowired
	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}
	
	public JobOrderSummaryReportManager getJobOrderSummaryReportManager() {
		return jobOrderSummaryReportManager;
	}

	@Autowired
	public void setJobOrderSummaryReportManager(JobOrderSummaryReportManager jobOrderSummaryReportManager) {
		this.jobOrderSummaryReportManager = jobOrderSummaryReportManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Model model = new ExtendedModelMap();
		model.addAttribute("jobOrderStatusList", lookupManager.getAllJobOrderStatus(request.getLocale()));
		JobOrderCriteria criteria = new JobOrderCriteria();
		Employee employee = new Employee();
		String id = request.getParameter("employee.id");
		if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
			employee.setId(Long.valueOf(id));
			criteria.setEmployee(employee);
		}
		SaleOrder saleOrder = new SaleOrder();
		DocumentNumber documentNumber = new DocumentNumber();
		documentNumber.setDocumentNo(request.getParameter("saleOrder.saleOrderNo"));
		saleOrder.setDocumentNumber(documentNumber);
		criteria.setSaleOrder(saleOrder);
		
		Catalog  catalog = new Catalog();
		catalog.setCode(request.getParameter("catalog.code"));
		criteria.setCatalog(catalog);
		
		String startTime = request.getParameter("startDateFrom");
		String endTime = request.getParameter("startDateTo");
		try {
			if (StringUtils.isNotBlank(startTime)) {
				Date startTimeDate = DateUtils.parseDateStrictly(startTime, DATE_PATTERN);
				criteria.setStartDateFrom(startTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { startTime }, request.getLocale()));
			return new ModelAndView("report/jobOrderSummaryReportList", model.asMap());
		}
		try {
			if (StringUtils.isNotBlank(endTime)) {
				Date endTimeDate = DateUtils.parseDate(endTime, DATE_PATTERN);
				criteria.setStartDateTo(endTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { endTime }, request.getLocale()));
			return new ModelAndView("report/jobOrderSummaryReportList", model.asMap());
		}
		
		startTime = request.getParameter("targetEndDateFrom");
		endTime = request.getParameter("targetEndDateTo");
		try {
			if (StringUtils.isNotBlank(startTime)) {
				Date startTimeDate = DateUtils.parseDateStrictly(startTime, DATE_PATTERN);
				criteria.setTargetEndDateFrom(startTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { startTime }, request.getLocale()));
			return new ModelAndView("report/jobOrderSummaryReportList", model.asMap());
		}
		try {
			if (StringUtils.isNotBlank(endTime)) {
				Date endTimeDate = DateUtils.parseDate(endTime, DATE_PATTERN);
				criteria.setTargetEndDateTo(endTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { endTime }, request.getLocale()));
			return new ModelAndView("report/jobOrderSummaryReportList", model.asMap());
		}
		
		criteria.setStatus(request.getParameter("status"));

		
		int page = getPage(request);
		String sortColumn = getSortColumn(request);
		String order = getSortOrder(request);
		int size = getPageSize(request);

		log.info(request.getRemoteUser() + " is quering jobOrderSummaryReport criteria := " + criteria);

		model.addAttribute("resultSize", jobOrderSummaryReportManager.querySize(criteria));
		model.addAttribute("jobOrderSummaryReportList", jobOrderSummaryReportManager.query(criteria, page, size, sortColumn, order));
		return new ModelAndView("report/jobOrderSummaryReportList", model.asMap());
	}
}
