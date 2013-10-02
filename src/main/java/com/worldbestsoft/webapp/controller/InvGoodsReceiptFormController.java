package com.worldbestsoft.webapp.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.worldbestsoft.model.InvGoodsReceipt;
import com.worldbestsoft.model.InvGoodsReceiptItem;
import com.worldbestsoft.model.Supplier;
import com.worldbestsoft.service.InvGoodsReceiptManager;
import com.worldbestsoft.service.LookupManager;
import com.worldbestsoft.service.SupplierManager;

@Controller
@RequestMapping("/invGoodsReceipt*")
public class InvGoodsReceiptFormController extends BaseFormController {
	
	private InvGoodsReceiptManager invGoodsReceiptManager;
	private SupplierManager supplierManager;
	private LookupManager lookupManager;

	public InvGoodsReceiptManager getInvGoodsReceiptManager() {
		return invGoodsReceiptManager;
	}

	@Autowired
	public void setInvGoodsReceiptManager(InvGoodsReceiptManager invGoodsReceiptManager) {
		this.invGoodsReceiptManager = invGoodsReceiptManager;
	}

	public SupplierManager getSupplierManager() {
		return supplierManager;
	}

	@Autowired
	public void setSupplierManager(SupplierManager supplierManager) {
		this.supplierManager = supplierManager;
	}
	
	public LookupManager getLookupManager() {
		return lookupManager;
	}

	@Autowired
	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}

	@RequestMapping(value="/save" ,method = RequestMethod.POST)
	public ModelAndView save(InvGoodsReceipt invGoodsReceiptForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/invGoodsReceiptList");
		}

		if (validator != null) { // validator is null during testing
			validator.validate(invGoodsReceiptForm, errors);

			if (errors.hasErrors() && !StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) { // don't validate when deleting
				return new ModelAndView("invGoodsReceipt", "invGoodsReceipt", invGoodsReceiptForm).addObject("goodsReceiptTypeList", lookupManager.getAllGoodsReceiptType(request.getLocale()));
			}
		}
		log.info(request.getRemoteUser() + " is saving InvGoodsReceipt := " + invGoodsReceiptForm);

		Locale locale = request.getLocale();

		if (StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) {
			//since code input is readonly, no value pass to form then we need to query from db.
//			InvGoodReceipt InvGoodReceipt = getInvGoodReceiptManager().get(invGoodsReceiptForm.getId());
			getInvGoodsReceiptManager().remove(invGoodsReceiptForm.getId());
			saveMessage(request, getText("invGoodsReceipt.deleted", invGoodsReceiptForm.getId(), locale));
			return new ModelAndView("redirect:/invGoodsReceiptList");
		} else {
			
			HttpSession session = request.getSession();
			InvGoodsReceipt invGoodsReceiptSession = (InvGoodsReceipt) session.getAttribute("invGoodsReceipt");
			
			if (null == invGoodsReceiptForm.getId()) {
				invGoodsReceiptSession.setReceiptType(invGoodsReceiptForm.getReceiptType());
				invGoodsReceiptSession.setReceiptDate(invGoodsReceiptForm.getReceiptDate());
				invGoodsReceiptSession.setSupplier(invGoodsReceiptForm.getSupplier());
				invGoodsReceiptSession.setMemo(invGoodsReceiptForm.getMemo());
				
				// add
				if (null != invGoodsReceiptForm.getSupplier()) {
					Supplier supplier = getSupplierManager().findBySupplierCode(invGoodsReceiptForm.getSupplier().getCode());
					invGoodsReceiptSession.setSupplier(supplier);
				}

				invGoodsReceiptSession.setCreateDate(new Date());
				invGoodsReceiptSession.setCreateUser(request.getRemoteUser());
				invGoodsReceiptSession.setUpdateDate(new Date());
				invGoodsReceiptSession.setUpdateUser(request.getRemoteUser());
				invGoodsReceiptSession = getInvGoodsReceiptManager().save(invGoodsReceiptSession, invGoodsReceiptSession.getInvGoodsReceiptItems());
				
				if (StringUtils.equalsIgnoreCase("saveToStock", request.getParameter("action"))) {
					invGoodsReceiptSession = getInvGoodsReceiptManager().saveToStock(invGoodsReceiptSession);
					saveMessage(request, getText("invGoodsReceipt.added", invGoodsReceiptSession.getDocumentNumber().getDocumentNo(), locale));
				} else {
					saveMessage(request, getText("invGoodsReceipt.added", invGoodsReceiptSession.getId(), locale));
				}

				return new ModelAndView("redirect:/invGoodsReceipt").addObject("id", invGoodsReceiptSession.getId());
			} else {
				// edit
				InvGoodsReceipt invGoodsReceipt = getInvGoodsReceiptManager().get(invGoodsReceiptForm.getId());
				invGoodsReceipt.setReceiptType(invGoodsReceiptForm.getReceiptType());
				if (null != invGoodsReceiptForm.getSupplier()) {
					Supplier supplier = getSupplierManager().findBySupplierCode(invGoodsReceiptForm.getSupplier().getCode());
					invGoodsReceipt.setSupplier(supplier);
				}
				invGoodsReceipt.setReceiptDate(invGoodsReceiptForm.getReceiptDate());
				invGoodsReceipt.setMemo(invGoodsReceiptForm.getMemo());
				invGoodsReceipt.setUpdateDate(new Date());
				invGoodsReceipt.setUpdateUser(request.getRemoteUser());
				invGoodsReceipt = getInvGoodsReceiptManager().save(invGoodsReceipt, invGoodsReceiptSession.getInvGoodsReceiptItems());
				
				if (StringUtils.equalsIgnoreCase("saveToStock", request.getParameter("action"))) {
					invGoodsReceipt = getInvGoodsReceiptManager().saveToStock(invGoodsReceipt);
					saveMessage(request, getText("invGoodsReceipt.saved", invGoodsReceipt.getDocumentNumber().getDocumentNo(), locale));
				} else {
					saveMessage(request, getText("invGoodsReceipt.saved", invGoodsReceipt.getId(), locale));
				}

				request.setAttribute("invGoodsReceipt", invGoodsReceipt);
				return new ModelAndView("redirect:/invGoodsReceiptList");
			}
		}
	}
	
	@RequestMapping(value = "/addDetail", method = RequestMethod.POST)
	public ModelAndView add(InvGoodsReceipt invGoodsReceiptForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		InvGoodsReceipt invGoodsReceipt = (InvGoodsReceipt) session.getAttribute("invGoodsReceipt");
		invGoodsReceipt.setReceiptType(invGoodsReceiptForm.getReceiptType());
		invGoodsReceipt.setReceiptDate(invGoodsReceiptForm.getReceiptDate());
		invGoodsReceipt.setSupplier(invGoodsReceiptForm.getSupplier());
		invGoodsReceipt.setMemo(invGoodsReceiptForm.getMemo());
		invGoodsReceipt.setDocumentNumber(invGoodsReceiptForm.getDocumentNumber());
		return new ModelAndView("redirect:/invGoodsReceiptItem?method=Add&from=list");
	}
	
	@RequestMapping(value = "/editDetail", method = RequestMethod.POST)
	public ModelAndView edit(InvGoodsReceipt invGoodsReceiptForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		InvGoodsReceipt invGoodsReceipt = (InvGoodsReceipt) session.getAttribute("invGoodsReceipt");
		invGoodsReceipt.setReceiptType(invGoodsReceiptForm.getReceiptType());
		invGoodsReceipt.setReceiptDate(invGoodsReceiptForm.getReceiptDate());
		invGoodsReceipt.setSupplier(invGoodsReceiptForm.getSupplier());
		invGoodsReceipt.setMemo(invGoodsReceiptForm.getMemo());
		invGoodsReceipt.setDocumentNumber(invGoodsReceiptForm.getDocumentNumber());
		return new ModelAndView("redirect:/invGoodsReceiptItem?from=list&id=" + request.getParameter("id"));
	}

	@RequestMapping(value = "/deleteDetail", method = RequestMethod.POST)
	public ModelAndView delete(InvGoodsReceipt invGoodsReceiptForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		InvGoodsReceipt invGoodsReceipt = (InvGoodsReceipt) session.getAttribute("invGoodsReceipt");
		invGoodsReceipt.setReceiptType(invGoodsReceiptForm.getReceiptType());
		invGoodsReceipt.setReceiptDate(invGoodsReceiptForm.getReceiptDate());
		invGoodsReceipt.setSupplier(invGoodsReceiptForm.getSupplier());
		invGoodsReceipt.setMemo(invGoodsReceiptForm.getMemo());
		invGoodsReceipt.setDocumentNumber(invGoodsReceiptForm.getDocumentNumber());
		List<InvGoodsReceiptItem> invGoodsReceiptItemList = new ArrayList<InvGoodsReceiptItem>(invGoodsReceipt.getInvGoodsReceiptItems());

		
		Locale locale = request.getLocale();
		String[] checkbox = request.getParameterValues("checkbox");
		if (null != checkbox && checkbox.length > 0) {
			for (int i = 0; i < checkbox.length; i++) {
				InvGoodsReceiptItem invGoodsReceiptItem = invGoodsReceiptItemList.get(Integer.parseInt(checkbox[i]));
				invGoodsReceipt.getInvGoodsReceiptItems().remove(invGoodsReceiptItem);
				saveMessage(request, getText("invGoodsReceiptItem.deleted", invGoodsReceiptItem.getInvItem().getCode(), locale));
			}
		} else {
			saveError(request, getText("global.errorNoCheckboxSelectForDelete", request.getLocale()));
		}
		return new ModelAndView("redirect:/invGoodsReceipt");
	}


	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String goodsReceiptNo = request.getParameter("goodsReceiptNo");
		String method = request.getParameter("method");
		HttpSession session = request.getSession();
		
		//keep master value in session, always new object if method is add
		InvGoodsReceipt invGoodsReceipt = (InvGoodsReceipt) session.getAttribute("invGoodsReceipt");
		if (null == invGoodsReceipt || StringUtils.equalsIgnoreCase(method, "add")) {
			invGoodsReceipt = new InvGoodsReceipt();
			invGoodsReceipt.setReceiptDate(new Date());
		}
		
		if (!isFormSubmission(request)) {
			if (id != null) {
				invGoodsReceipt = getInvGoodsReceiptManager().get(Long.valueOf(id));
			} else if (goodsReceiptNo != null) {
				invGoodsReceipt = getInvGoodsReceiptManager().findByGoodsReceiptNo(goodsReceiptNo);
			}
		} else {
			invGoodsReceipt = getInvGoodsReceiptManager().get(Long.valueOf(id));
		}
		//calculate totalCost
		BigDecimal totalCost = BigDecimal.ZERO;
		for (InvGoodsReceiptItem invGoodsReceiptItem : invGoodsReceipt.getInvGoodsReceiptItems()) {
			totalCost = totalCost.add(invGoodsReceiptItem.getQty().multiply(invGoodsReceiptItem.getUnitPrice()));
		}
		invGoodsReceipt.setTotalCost(totalCost);
		
		session.setAttribute("invGoodsReceipt", invGoodsReceipt);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("invGoodsReceipt", invGoodsReceipt);
		model.put("invGoodsReceiptItemList", invGoodsReceipt.getInvGoodsReceiptItems());
		model.put("goodsReceiptTypeList", lookupManager.getAllGoodsReceiptType(request.getLocale()));
		
		return new ModelAndView("invGoodsReceipt", model);
	}

	private boolean isFormSubmission(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}

}
