package com.worldbestsoft.webapp.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.model.SaleReceipt;
import com.worldbestsoft.service.LookupManager;
import com.worldbestsoft.service.SaleOrderManager;
import com.worldbestsoft.service.SaleReceiptManager;

@Controller
@RequestMapping("/saleReceipt*")
public class SaleReceiptFormController extends BaseFormController {

	private SaleReceiptManager saleReceiptManager;
	private SaleOrderManager saleOrderManager;
	private LookupManager lookupManager;
	
	public SaleReceiptManager getSaleReceiptManager() {
		return saleReceiptManager;
	}
	
	@Autowired
	public void setSaleReceiptManager(SaleReceiptManager saleReceiptManager) {
		this.saleReceiptManager = saleReceiptManager;
	}
	
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

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(SaleReceipt saleReceiptForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/saleReceiptList");
		}

		if (validator != null) { // validator is null during testing
			validator.validate(saleReceiptForm, errors);
			
			if (null != saleReceiptForm.getSaleOrder()) {
				SaleOrder saleOrder = getSaleOrderManager().findBySaleOrderNo(saleReceiptForm.getSaleOrder().getSaleOrderNo());
				saleReceiptForm.setSaleOrder(saleOrder);
				if (null == saleOrder) {
					errors.rejectValue("saleOrder.saleOrderNo", "errors.invalid", new Object[] { getText("saleReceipt.saleOrder.saleOrderNo", request.getLocale())}, "errors.invalid");	
				}
			}
			
			if (StringUtils.equalsIgnoreCase(saleReceiptForm.getReceiptType(), "2")) {//cheque
				if (StringUtils.isBlank(saleReceiptForm.getChequeNo())) {
					errors.rejectValue("chequeNo", "errors.required", new Object[] { getText("saleReceipt.chequeNo", request.getLocale())}, "errors.required");
				}
				if (StringUtils.isBlank(saleReceiptForm.getChequeBank())) {
					errors.rejectValue("chequeBank", "errors.required", new Object[] { getText("saleReceipt.chequeBank", request.getLocale())}, "errors.required");
				}
				if (null == saleReceiptForm.getChequeDate()) {
					errors.rejectValue("chequeDate", "errors.required", new Object[] { getText("saleReceipt.chequeDate", request.getLocale())}, "errors.required");
				}
			}

			if (errors.hasErrors() && !StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) { // don't validate when deleting
				return new ModelAndView("saleReceipt", "saleReceipt", saleReceiptForm).addObject("receiptTypeList", lookupManager.getAllReceiptType(request.getLocale()));
			}
		}
		log.info(request.getRemoteUser() + " is saving SaleReceipt := " + saleReceiptForm);

		Locale locale = request.getLocale();
		
		if (StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) {
			getSaleReceiptManager().remove(saleReceiptForm.getId(), request.getRemoteUser(), saleReceiptForm.getCancelReason());
			saveMessage(request, getText("saleReceipt.deleted", saleReceiptForm.getReceiptNo(), locale));
			return new ModelAndView("redirect:/saleReceiptList");
		} else {
		
			if (null == saleReceiptForm.getId()) {
				// add
				saleReceiptForm.setCreateDate(new Date());
				saleReceiptForm.setCreateUser(request.getRemoteUser());
				
				saleReceiptForm = getSaleReceiptManager().save(saleReceiptForm);
	
				saveMessage(request, getText("saleReceipt.added", saleReceiptForm.getReceiptNo(), locale));
				return new ModelAndView("redirect:/saleReceipt").addObject("id", saleReceiptForm.getId());
			} else {
				// edit
	
				SaleReceipt saleReceipt = getSaleReceiptManager().get(saleReceiptForm.getId());
				saleReceipt.setUpdateDate(new Date());
				saleReceipt.setUpdateUser(request.getRemoteUser());
				saleReceipt = getSaleReceiptManager().save(saleReceipt);
	
				request.setAttribute("saleReceipt", saleReceipt);
				saveMessage(request, getText("saleReceipt.saved", saleReceipt.getReceiptNo(), locale));
				return new ModelAndView("redirect:/saleReceiptList");
			}
		}
	}

	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String saleOrderNo = request.getParameter("saleOrderNo");
		SaleReceipt saleReceipt = new SaleReceipt();
		saleReceipt.setReceiptType("1");//default cash
		saleReceipt.setReceiptDate(new Date());
		
		if (StringUtils.isNotBlank(saleOrderNo)) {
			SaleOrder saleOrder = saleOrderManager.findBySaleOrderNo(saleOrderNo);
			if (null != saleOrder) {
				saleReceipt.setSaleOrder(saleOrder);
				if (null != saleOrder.getPaymentPaid()) {
					saleReceipt.setReceiptAmount(saleOrder.getTotalPrice().subtract(saleOrder.getPaymentPaid()));
				} else {
					saleReceipt.setReceiptAmount(saleOrder.getTotalPrice());
				}
			}
		}
		if (!isFormSubmission(request)) {
			if (id != null) {
				saleReceipt = getSaleReceiptManager().get(Long.valueOf(id));
			}
		} else {
			saleReceipt = getSaleReceiptManager().get(Long.valueOf(id));
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("saleReceipt", saleReceipt);
		model.put("receiptTypeList", lookupManager.getAllReceiptType(request.getLocale()));
		return new ModelAndView("saleReceipt", model);
	}

	private boolean isFormSubmission(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}
	
	@RequestMapping(value = "/displayTable")
	protected ModelAndView displayTable(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String saleOrderNo = request.getParameter("saleOrderNo");
		Map<String, Object> model = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(saleOrderNo)) {
			SaleOrder saleOrder = saleOrderManager.findBySaleOrderNo(saleOrderNo);
			BigDecimal paymentPaid = BigDecimal.ZERO;
			if (null != saleOrder) {
				for (SaleReceipt saleReceipt : saleOrder.getSaleReceipts()) {
					if (StringUtils.equalsIgnoreCase("A", saleReceipt.getStatus())) {
						paymentPaid = paymentPaid.add(saleReceipt.getReceiptAmount());
					}
				}
				
				saleOrder.setPaymentPaid(paymentPaid);
			}
			model.put("saleReceiptList", saleOrder.getSaleReceipts());
			model.put("saleOrder", saleOrder);
		} else {
			model.put("saleOrder", new SaleOrder());
		}
		return new ModelAndView("saleReceiptTable", model);
	}

}
