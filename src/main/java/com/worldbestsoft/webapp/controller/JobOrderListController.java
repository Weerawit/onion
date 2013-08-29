package com.worldbestsoft.webapp.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

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

import com.worldbestsoft.model.Employee;
import com.worldbestsoft.model.JobOrder;
import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.model.SaleOrderItem;
import com.worldbestsoft.model.criteria.JobOrderCriteria;
import com.worldbestsoft.service.JobOrderManager;
import com.worldbestsoft.service.LookupManager;

@Controller
@RequestMapping("/jobOrderList*")
public class JobOrderListController extends BaseFormController {
	
	private static final String[] DATE_PATTERN = new String[] {"dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy"};
	
	private LookupManager lookupManager;
	private JobOrderManager  jobOrderManager;
	
	public LookupManager getLookupManager() {
		return lookupManager;
	}

	@Autowired
	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}

	public JobOrderManager getJobOrderManager() {
		return jobOrderManager;
	}

	@Autowired
	public void setJobOrderManager(JobOrderManager jobOrderManager) {
		this.jobOrderManager = jobOrderManager;
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
		saleOrder.setSaleOrderNo(request.getParameter("saleOrderItem.saleOrder.saleOrderNo"));
		SaleOrderItem saleOrderItem = new SaleOrderItem();
		saleOrderItem.setSaleOrder(saleOrder);
		criteria.setSaleOrderItem(saleOrderItem);
		
		String startTime = request.getParameter("startDateFrom");
		String endTime = request.getParameter("startDateTo");
		try {
			if (StringUtils.isNotBlank(startTime)) {
				Date startTimeDate = DateUtils.parseDateStrictly(startTime, DATE_PATTERN);
				criteria.setStartDateFrom(startTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { startTime }, request.getLocale()));
			return new ModelAndView("jobOrderList", model.asMap());
		}
		try {
			if (StringUtils.isNotBlank(endTime)) {
				Date endTimeDate = DateUtils.parseDate(endTime, DATE_PATTERN);
				criteria.setStartDateTo(endTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { endTime }, request.getLocale()));
			return new ModelAndView("jobOrderList", model.asMap());
		}
		
		startTime = request.getParameter("endDateFrom");
		endTime = request.getParameter("endDateTo");
		try {
			if (StringUtils.isNotBlank(startTime)) {
				Date startTimeDate = DateUtils.parseDateStrictly(startTime, DATE_PATTERN);
				criteria.setEndDateFrom(startTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { startTime }, request.getLocale()));
			return new ModelAndView("jobOrderList", model.asMap());
		}
		try {
			if (StringUtils.isNotBlank(endTime)) {
				Date endTimeDate = DateUtils.parseDate(endTime, DATE_PATTERN);
				criteria.setEndDateTo(endTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { endTime }, request.getLocale()));
			return new ModelAndView("jobOrderList", model.asMap());
		}
		
		criteria.setStatus(request.getParameter("status"));

		
		int page = getPage(request);
		String sortColumn = getSortColumn(request);
		String order = getSortOrder(request);
		int size = getPageSize(request);

		log.info(request.getRemoteUser() + " is quering JobOrder criteria := " + criteria);

		model.addAttribute("resultSize", jobOrderManager.querySize(criteria));
		model.addAttribute("jobOrderList", jobOrderManager.query(criteria, page, size, sortColumn, order));
		return new ModelAndView("jobOrderList", model.asMap());
	}

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = request.getLocale();
		String[] checkbox = request.getParameterValues("checkbox");
		if (null != checkbox && checkbox.length > 0) {
			for (int i = 0; i < checkbox.length; i++) {
				JobOrder jobOrder = jobOrderManager.get(Long.valueOf(checkbox[i]));
				jobOrderManager.remove(Long.valueOf(checkbox[i]));
				saveMessage(request, getText("jobOrder.deleted", jobOrder.getId(), locale));
			}
		} else {
			saveError(request, getText("global.errorNoCheckboxSelectForDelete", request.getLocale()));
		}
		return new ModelAndView("redirect:/jobOrderList");
	}

}
