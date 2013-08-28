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
import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.model.SaleReceipt;
import com.worldbestsoft.model.criteria.SaleReceiptCriteria;
import com.worldbestsoft.service.SaleReceiptManager;

@Controller
@RequestMapping("/saleReceiptList*")
public class SaleReceiptListController extends BaseFormController {
	
	private static final String[] DATE_PATTERN = new String[] {"dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy"};

	private SaleReceiptManager saleReceiptManager;
	
	public SaleReceiptManager getSaleReceiptManager() {
		return saleReceiptManager;
	}

	@Autowired
	public void setSaleReceiptManager(SaleReceiptManager saleReceiptManager) {
		this.saleReceiptManager = saleReceiptManager;
	}


	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Model model = new ExtendedModelMap();

		SaleReceiptCriteria criteria = new SaleReceiptCriteria();
		SaleOrder saleOrder = new SaleOrder();
		saleOrder.setSaleOrderNo(request.getParameter("saleOrder.saleOrderNo"));
		
		Customer customer = new Customer();
		customer.setName(request.getParameter("saleOrder.customer.name"));
		saleOrder.setCustomer(customer);
		criteria.setSaleOrder(saleOrder);
		
		String startTime = request.getParameter("receiptDateFrom");
		String endTime = request.getParameter("receiptDateTo");
		try {
			if (StringUtils.isNotBlank(startTime)) {
				Date startTimeDate = DateUtils.parseDateStrictly(startTime, DATE_PATTERN);
				criteria.setReceiptDateFrom(startTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { startTime }, request.getLocale()));
			return new ModelAndView("saleReceiptList", model.asMap());
		}
		try {
			if (StringUtils.isNotBlank(endTime)) {
				Date endTimeDate = DateUtils.parseDate(endTime, DATE_PATTERN);
				criteria.setReceiptDateTo(endTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { endTime }, request.getLocale()));
			return new ModelAndView("saleReceiptList", model.asMap());
		}

		
		int page = getPage(request);
		String sortColumn = getSortColumn(request);
		String order = getSortOrder(request);
		int size = getPageSize(request);

		log.info(request.getRemoteUser() + " is quering SaleReceipt criteria := " + criteria);

		model.addAttribute("resultSize", saleReceiptManager.querySize(criteria));
		model.addAttribute("saleReceiptList", saleReceiptManager.query(criteria, page, size, sortColumn, order));
		return new ModelAndView("saleReceiptList", model.asMap());
	}
	
	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = request.getLocale();
		String[] checkbox = request.getParameterValues("checkbox");
		String user = request.getRemoteUser();
		String cancelReason = request.getParameter("cancelReason");
		if (null != checkbox && checkbox.length > 0) {
			for (int i = 0; i < checkbox.length; i++) {
				SaleReceipt saleReceipt = saleReceiptManager.get(Long.valueOf(checkbox[i]));
				saleReceiptManager.remove(Long.valueOf(checkbox[i]), user, cancelReason);
				saveMessage(request, getText("catalog.deleted", saleReceipt.getReceiptNo(), locale));
			}
		} else {
			saveError(request, getText("global.errorNoCheckboxSelectForDelete", request.getLocale()));
		}
		return new ModelAndView("redirect:/saleReceiptList");
	}

}
