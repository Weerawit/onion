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

import com.sun.org.apache.bcel.internal.generic.DCONST;
import com.worldbestsoft.model.DocumentNumber;
import com.worldbestsoft.model.Supplier;
import com.worldbestsoft.model.criteria.InvGoodsReceiptCriteria;
import com.worldbestsoft.service.InvGoodsReceiptManager;
import com.worldbestsoft.service.LookupManager;

@Controller
@RequestMapping("/invGoodsReceiptList*")
public class InvGoodsReceiptListController extends BaseFormController {
	
	private static final String[] DATE_PATTERN = new String[] {"dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy"};

	private InvGoodsReceiptManager invGoodsReceiptManager;
	
	private LookupManager lookupManager;
	
	public InvGoodsReceiptManager getInvGoodsReceiptManager() {
		return invGoodsReceiptManager;
	}

	@Autowired
	public void setInvGoodsReceiptManager(InvGoodsReceiptManager invGoodsReceiptManager) {
		this.invGoodsReceiptManager = invGoodsReceiptManager;
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
		model.addAttribute("goodsReceiptTypeList", lookupManager.getAllGoodsReceiptType(request.getLocale()));

		InvGoodsReceiptCriteria criteria = new InvGoodsReceiptCriteria();
		criteria.setReceiptType(request.getParameter("receiptType"));
		DocumentNumber documentNumber = new DocumentNumber();
		documentNumber.setDocumentNo(request.getParameter("documentNumber.documentNo"));
		criteria.setDocumentNumber(documentNumber);
		Supplier supplier = new Supplier();
		supplier.setName(request.getParameter("supplier.name"));
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
		model.addAttribute("invGoodsReceiptList", invGoodsReceiptManager.query(criteria, page, size, sortColumn, order));
		return new ModelAndView("invGoodsReceiptList", model.asMap());
	}

}
