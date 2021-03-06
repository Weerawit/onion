package com.worldbestsoft.webapp.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.worldbestsoft.model.ConstantModel;
import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.model.SaleReceipt;
import com.worldbestsoft.model.criteria.DownloadModel;
import com.worldbestsoft.service.LookupManager;
import com.worldbestsoft.service.SaleOrderManager;
import com.worldbestsoft.service.SaleReceiptManager;
import com.worldbestsoft.webapp.util.ReportUtil.ExportType;

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
			
			if (null != saleReceiptForm.getSaleOrder() && null != saleReceiptForm.getSaleOrder().getDocumentNumber() && StringUtils.isNotBlank(saleReceiptForm.getSaleOrder().getDocumentNumber().getDocumentNo())) {
				SaleOrder saleOrder = getSaleOrderManager().findBySaleOrderNo(saleReceiptForm.getSaleOrder().getDocumentNumber().getDocumentNo());
				saleReceiptForm.setSaleOrder(saleOrder);
				if (null == saleOrder) {
					errors.rejectValue("saleOrder.saleOrderNo", "errors.invalid", new Object[] { getText("saleReceipt.saleOrder.saleOrderNo", request.getLocale())}, "errors.invalid");	
				}
			}
			
			if (StringUtils.equalsIgnoreCase(saleReceiptForm.getReceiptType(), ConstantModel.ReceiptType.CHEQUE.getCode())) {//cheque
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
			saveMessage(request, getText("saleReceipt.deleted", saleReceiptForm.getDocumentNumber().getDocumentNo(), locale));
			return new ModelAndView("redirect:/saleReceiptList");
		} else {
		
			if (null == saleReceiptForm.getId()) {
				// add
				saleReceiptForm.setCreateDate(new Date());
				saleReceiptForm.setCreateUser(request.getRemoteUser());
				
				saleReceiptForm = getSaleReceiptManager().save(saleReceiptForm);
	
				saveMessage(request, getText("saleReceipt.added", saleReceiptForm.getDocumentNumber().getDocumentNo(), locale));
				return new ModelAndView("redirect:/saleReceipt").addObject("id", saleReceiptForm.getId());
			} else {
				// edit
	
				SaleReceipt saleReceipt = getSaleReceiptManager().get(saleReceiptForm.getId());
				saleReceipt.setUpdateDate(new Date());
				saleReceipt.setUpdateUser(request.getRemoteUser());
				saleReceipt = getSaleReceiptManager().save(saleReceipt);
	
				request.setAttribute("saleReceipt", saleReceipt);
				saveMessage(request, getText("saleReceipt.saved", saleReceipt.getDocumentNumber().getDocumentNo(), locale));
				return new ModelAndView("redirect:/saleReceiptList");
			}
		}
	}

	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String saleOrderNo = request.getParameter("saleOrderNo");
		SaleReceipt saleReceipt = new SaleReceipt();
		saleReceipt.setReceiptType(ConstantModel.ReceiptType.CASH.getCode());//default cash
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
		model.put("saleReceiptStatusList", lookupManager.getAllSaleReceiptStatusList(request.getLocale()));
		if (StringUtils.isNotBlank(saleOrderNo)) {
			SaleOrder saleOrder = saleOrderManager.findBySaleOrderNo(saleOrderNo);
			BigDecimal paymentPaid = BigDecimal.ZERO;
			if (null != saleOrder) {
				for (SaleReceipt saleReceipt : saleOrder.getSaleReceipts()) {
					if (StringUtils.equalsIgnoreCase(ConstantModel.SaleReceiptStatus.ACTIVE.getCode(), saleReceipt.getStatus())) {
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
	
	@RequestMapping(value = "/download")
	public void download(@RequestParam("type") String type,
			@RequestParam("token") String token, 
			@RequestParam("id") String id,
			HttpServletResponse response) throws JRException, IOException {
		SaleReceipt saleReceipt = getSaleReceiptManager().get(Long.valueOf(id));
		ExportType exportType = ExportType.fromString(type);
		String jrxmlFile = "/reports/SaleReceipt.jrxml";
//		String jrxmlFile = "/Users/Weerawit/Documents/Projects/company_wbs/wbs/projects/inventory_wood/git/onion/src/main/resources/reports/SaleReceipt.jrxml";
		String downloadFileName = "receipt";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("saleReceipt", saleReceipt);
		
		DownloadModel downloadModel = new DownloadModel();
		downloadModel.setJrxml(jrxmlFile);
		downloadModel.setOutputFileName(downloadFileName);
		downloadModel.setParams(params);
		downloadModel.setToken(token);
		downloadModel.setResponse(response);
		downloadModel.setType(exportType);
		
//		for testing
//		Collection<Object> data = new ArrayList<Object>();
//		for (int i = 0; i < 70; i++) {
//			data.addAll(saleReceipt.getSaleOrder().getSaleOrderItems());
//		}
//		
		downloadModel.setJrDataSource(new JRBeanCollectionDataSource(saleReceipt.getSaleOrder().getSaleOrderItems()));
		
		getReportUtil().download(downloadModel);
	}

}
