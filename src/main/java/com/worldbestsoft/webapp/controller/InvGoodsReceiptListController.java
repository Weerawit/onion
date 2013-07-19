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

import com.worldbestsoft.model.Supplier;
import com.worldbestsoft.model.criteria.InvGoodsReceiptCriteria;
import com.worldbestsoft.service.InvGoodsReceiptManager;
import com.worldbestsoft.service.SupplierManager;

@Controller
@RequestMapping("/invGoodsReceiptList*")
public class InvGoodsReceiptListController extends BaseFormController {
	
	private static final String[] DATE_PATTERN = new String[] {"dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy"};

	private SupplierManager supplierManager;
	
	private InvGoodsReceiptManager invGoodsReceiptManager;
	
	public SupplierManager getSupplierManager() {
		return supplierManager;
	}

	@Autowired
	public void setSupplierManager(SupplierManager supplierManager) {
		this.supplierManager = supplierManager;
	}
	
	public InvGoodsReceiptManager getInvGoodsReceiptManager() {
		return invGoodsReceiptManager;
	}

	@Autowired
	public void setInvGoodsReceiptManager(InvGoodsReceiptManager invGoodsReceiptManager) {
		this.invGoodsReceiptManager = invGoodsReceiptManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Model model = new ExtendedModelMap();
		model.addAttribute("supplierList", supplierManager.getAll());

		InvGoodsReceiptCriteria criteria = new InvGoodsReceiptCriteria();
		criteria.setRunningNo(request.getParameter("runningNo"));
		Supplier supplier = new Supplier();
		supplier.setCode(request.getParameter("invGoodsReceipt.supplier.code"));
		criteria.setSupplier(supplier);
		
		String startTime = request.getParameter("receiptDateFrom");
		String endTime = request.getParameter("receiptDateTo");
		try {
			if (StringUtils.isNotBlank(startTime)) {
				Date startTimeDate = DateUtils.parseDateStrictly(startTime, DATE_PATTERN);
				criteria.setReceiptDateFrom(startTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { startTime }, request.getLocale()));
			return new ModelAndView("invGoodsReceiptList", model.asMap());
		}
		try {
			if (StringUtils.isNotBlank(endTime)) {
				Date endTimeDate = DateUtils.parseDate(endTime, DATE_PATTERN);
				criteria.setReceiptDateTo(endTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { endTime }, request.getLocale()));
			return new ModelAndView("invGoodsReceiptList", model.asMap());
		}

		
		int page = getPage(request);
		String sortColumn = getSortColumn(request);
		String order = getSortOrder(request);
		int size = getPageSize(request);

		log.info(request.getRemoteUser() + " is quering InvGoodsReceipt criteria := " + criteria);

		model.addAttribute("resultSize", invGoodsReceiptManager.querySize(criteria));
		model.addAttribute("invItemList", invGoodsReceiptManager.query(criteria, page, size, sortColumn, order));
		return new ModelAndView("invGoodsReceiptList", model.asMap());
	}

}
