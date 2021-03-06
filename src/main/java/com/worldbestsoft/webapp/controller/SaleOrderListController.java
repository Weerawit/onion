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

import com.worldbestsoft.model.Customer;
import com.worldbestsoft.model.DocumentNumber;
import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.model.criteria.SaleOrderCriteria;
import com.worldbestsoft.service.LookupManager;
import com.worldbestsoft.service.SaleOrderManager;

@Controller
@RequestMapping("/saleOrderList*")
public class SaleOrderListController extends BaseFormController {
	
	private static final String[] DATE_PATTERN = new String[] {"dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy"};

	private SaleOrderManager saleOrderManager;
	
	private LookupManager lookupManager;
	
	public SaleOrderManager getSaleOrderManager() {
		return saleOrderManager;
	}

	@Autowired
	public void setSaleOrderManager(SaleOrderManager saleOrderManager) {
		this.saleOrderManager = saleOrderManager;
	}
	
	public LookupManager getLookupManager() {
		return lookupManager;
	}

	@Autowired
	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Model model = new ExtendedModelMap();
		model.addAttribute("saleOrderStatusList", lookupManager.getAllSaleOrderStatusList(request.getLocale()));
		SaleOrderCriteria criteria = new SaleOrderCriteria();
		DocumentNumber documentNumber = new DocumentNumber();
		documentNumber.setDocumentNo(request.getParameter("documentNumber.documentNo"));
		criteria.setDocumentNumber(documentNumber);
		
		criteria.setStatus(request.getParameter("status"));
		
		String startTime = request.getParameter("deliveryDateFrom");
		String endTime = request.getParameter("deliveryDateTo");
		try {
			if (StringUtils.isNotBlank(startTime)) {
				Date startTimeDate = DateUtils.parseDateStrictly(startTime, DATE_PATTERN);
				criteria.setDeliveryDateFrom(startTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { startTime }, request.getLocale()));
			return new ModelAndView("saleOrderList", model.asMap());
		}
		try {
			if (StringUtils.isNotBlank(endTime)) {
				Date endTimeDate = DateUtils.parseDate(endTime, DATE_PATTERN);
				criteria.setDeliveryDateTo(endTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { endTime }, request.getLocale()));
			return new ModelAndView("saleOrderList", model.asMap());
		}
		
		Customer customer = new Customer();
		customer.setName(request.getParameter("customer.name"));
		criteria.setCustomer(customer);
		int page = getPage(request);
		String sortColumn = getSortColumn(request);
		String order = getSortOrder(request);
		int size = getPageSize(request);

		log.info(request.getRemoteUser() + " is quering SaleOrder criteria := " + criteria);

		
		model.addAttribute("resultSize", saleOrderManager.querySize(criteria));
		model.addAttribute("saleOrderList", saleOrderManager.query(criteria, page, size, sortColumn, order));
		return new ModelAndView("saleOrderList", model.asMap());
	}

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = request.getLocale();
		String[] checkbox = request.getParameterValues("checkbox");
		String user = request.getRemoteUser();
		String cancelReason = request.getParameter("cancelReason");
		if (null != checkbox && checkbox.length > 0) {
			for (int i = 0; i < checkbox.length; i++) {
				SaleOrder saleOrder = saleOrderManager.get(Long.valueOf(checkbox[i]));
				saleOrderManager.remove(Long.valueOf(checkbox[i]), user, cancelReason);
				saveMessage(request, getText("saleOrder.deleted", saleOrder.getDocumentNumber().getDocumentNo(), locale));
			}
		} else {
			saveError(request, getText("global.errorNoCheckboxSelectForDelete", request.getLocale()));
		}
		return new ModelAndView("redirect:/saleOrderList");
	}

}
