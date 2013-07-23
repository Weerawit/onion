package com.worldbestsoft.webapp.controller;

import java.util.ArrayList;
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
import com.worldbestsoft.model.InvItem;
import com.worldbestsoft.model.Supplier;
import com.worldbestsoft.service.InvGoodsReceiptManager;
import com.worldbestsoft.service.SupplierManager;

@Controller
@RequestMapping("/invGoodsReceipt*")
public class InvGoodsReceiptFormController extends BaseFormController {
	
	private InvGoodsReceiptManager invGoodsReceiptManager;
	private SupplierManager supplierManager;

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

	@RequestMapping(value="/save" ,method = RequestMethod.POST)
	public ModelAndView save(InvGoodsReceipt invGoodReceiptForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/invGoodsReceiptList");
		}

		if (validator != null) { // validator is null during testing
			validator.validate(invGoodReceiptForm, errors);

			if ((errors.hasErrors() && request.getParameter("delete") == null) && (errors.hasErrors() && request.getParameter("id") == null)) { // don't
				// validate
				// when
				// deleting
				return new ModelAndView("invGoodsReceipt", "invGoodsReceipt", invGoodReceiptForm);
			}
		}
		log.info(request.getRemoteUser() + " is saving InvGoodsReceipt := " + invGoodReceiptForm);

		Locale locale = request.getLocale();

		if (request.getParameter("delete") != null) {
			//since code input is readonly, no value pass to form then we need to query from db.
//			InvGoodReceipt InvGoodReceipt = getInvGoodReceiptManager().get(InvGoodReceiptForm.getId());
			getInvGoodsReceiptManager().remove(invGoodReceiptForm.getId());
			saveMessage(request, getText("invGoodsReceipt.deleted", invGoodReceiptForm.getRunningNo(), locale));
			return new ModelAndView("redirect:/invGoodsReceiptList");
		} else {
			
			HttpSession session = request.getSession();
			InvGoodsReceipt invGoodsReceiptSession = (InvGoodsReceipt) session.getAttribute("invGoodsReceipt");
			
			if (null == invGoodReceiptForm.getId()) {
				
				invGoodsReceiptSession.setReceiptDate(invGoodReceiptForm.getReceiptDate());
				invGoodsReceiptSession.setSupplier(invGoodReceiptForm.getSupplier());
				invGoodsReceiptSession.setRemark(invGoodReceiptForm.getRemark());
				
				// add
				if (null != invGoodReceiptForm.getSupplier()) {
					Supplier supplier = getSupplierManager().findBySupplierCode(invGoodReceiptForm.getSupplier().getCode());
					invGoodsReceiptSession.setSupplier(supplier);
				}

				invGoodsReceiptSession = getInvGoodsReceiptManager().save(invGoodsReceiptSession);

				saveMessage(request, getText("invGoodsReceipt.added", invGoodsReceiptSession.getRunningNo(), locale));
				return new ModelAndView("redirect:/invGoodsReceipt").addObject("id", invGoodsReceiptSession.getId());
			} else {
				// edit
				InvGoodsReceipt invGoodsReceipt = getInvGoodsReceiptManager().get(invGoodReceiptForm.getId());
				if (null != invGoodReceiptForm.getSupplier()) {
					Supplier supplier = getSupplierManager().findBySupplierCode(invGoodReceiptForm.getSupplier().getCode());
					invGoodsReceipt.setSupplier(supplier);
				}
				invGoodsReceipt.setReceiptDate(invGoodReceiptForm.getReceiptDate());
				invGoodsReceipt.setRemark(invGoodReceiptForm.getRemark());
				invGoodsReceipt.setInvGoodReceiptItems(invGoodsReceiptSession.getInvGoodReceiptItems());
				invGoodsReceipt = getInvGoodsReceiptManager().save(invGoodsReceipt);

				request.setAttribute("invGoodsReceipt", invGoodsReceipt);
				saveMessage(request, getText("invGoodsReceipt.saved", invGoodsReceipt.getRunningNo(), locale));
				return new ModelAndView("redirect:/invGoodsReceiptList");
			}
		}
	}
	
	@RequestMapping(value = "/addDetail", method = RequestMethod.POST)
	public ModelAndView add(InvGoodsReceipt invGoodReceiptForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		InvGoodsReceipt invGoodsReceipt = (InvGoodsReceipt) session.getAttribute("invGoodsReceipt");
		invGoodsReceipt.setReceiptDate(invGoodReceiptForm.getReceiptDate());
		invGoodsReceipt.setSupplier(invGoodReceiptForm.getSupplier());
		invGoodsReceipt.setRemark(invGoodReceiptForm.getRemark());
		invGoodsReceipt.setRunningNo(invGoodReceiptForm.getRunningNo());
		return new ModelAndView("redirect:/invGoodsReceiptItem?method=Add&from=list");
	}
	
	@RequestMapping(value = "/editetail", method = RequestMethod.POST)
	public ModelAndView edit(InvGoodsReceipt invGoodReceiptForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		InvGoodsReceipt invGoodsReceipt = (InvGoodsReceipt) session.getAttribute("invGoodsReceipt");
		invGoodsReceipt.setReceiptDate(invGoodReceiptForm.getReceiptDate());
		invGoodsReceipt.setSupplier(invGoodReceiptForm.getSupplier());
		invGoodsReceipt.setRemark(invGoodReceiptForm.getRemark());
		invGoodsReceipt.setRunningNo(invGoodReceiptForm.getRunningNo());
		return new ModelAndView("redirect:/invGoodsReceiptItem?from=list&id=" + request.getParameter("id"));
	}

	@RequestMapping(value = "/deleteDetail", method = RequestMethod.POST)
	public ModelAndView delete(InvGoodsReceipt invGoodReceiptForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		InvGoodsReceipt invGoodsReceipt = (InvGoodsReceipt) session.getAttribute("invGoodsReceipt");
		invGoodsReceipt.setReceiptDate(invGoodReceiptForm.getReceiptDate());
		invGoodsReceipt.setSupplier(invGoodReceiptForm.getSupplier());
		invGoodsReceipt.setRemark(invGoodReceiptForm.getRemark());
		invGoodsReceipt.setRunningNo(invGoodReceiptForm.getRunningNo());
		List<InvGoodsReceiptItem> invGoodsReceiptItemList = new ArrayList<InvGoodsReceiptItem>(invGoodsReceipt.getInvGoodReceiptItems());

		
		Locale locale = request.getLocale();
		String[] checkbox = request.getParameterValues("checkbox");
		if (null != checkbox && checkbox.length > 0) {
			for (int i = 0; i < checkbox.length; i++) {
				InvGoodsReceiptItem invGoodsReceiptItem = invGoodsReceiptItemList.get(Integer.parseInt(checkbox[i]));
				invGoodsReceipt.getInvGoodReceiptItems().remove(invGoodsReceiptItem);
				saveMessage(request, getText("invGoodsReceiptItem.deleted", invGoodsReceiptItem.getInvItem().getCode(), locale));
			}
		} else {
			saveError(request, "global.errorNoCheckboxSelectForDelete");
		}
		return new ModelAndView("redirect:/invGoodsReceipt");
	}


	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String method = request.getParameter("method");
		HttpSession session = request.getSession();
		
		//keep master value in session, always new object if method is add
		InvGoodsReceipt invGoodsReceipt = (InvGoodsReceipt) session.getAttribute("invGoodsReceipt");
		if (null == invGoodsReceipt || StringUtils.equalsIgnoreCase(method, "add")) {
			invGoodsReceipt = new InvGoodsReceipt();
		}
		
		if (!isFormSubmission(request)) {
			if (id != null) {
				invGoodsReceipt = getInvGoodsReceiptManager().get(Long.valueOf(id));
			}
		} else {
			invGoodsReceipt = getInvGoodsReceiptManager().get(Long.valueOf(id));
		}
		session.setAttribute("invGoodsReceipt", invGoodsReceipt);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("invGoodsReceipt", invGoodsReceipt);
		model.put("invGoodsReceiptItemList", invGoodsReceipt.getInvGoodReceiptItems());
		model.put("supplierList", supplierManager.getAll());
		
		return new ModelAndView("invGoodsReceipt", model);
	}

	private boolean isFormSubmission(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}

}
