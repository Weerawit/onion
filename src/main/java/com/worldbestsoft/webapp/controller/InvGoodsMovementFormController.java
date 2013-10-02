package com.worldbestsoft.webapp.controller;

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

import com.worldbestsoft.model.InvGoodsMovement;
import com.worldbestsoft.model.InvGoodsMovementItem;
import com.worldbestsoft.service.InvGoodsMovementManager;
import com.worldbestsoft.service.LookupManager;

@Controller
@RequestMapping("/invGoodsMovement*")
public class InvGoodsMovementFormController extends BaseFormController {
	
	private InvGoodsMovementManager invGoodsMovementManager;
	
	private LookupManager lookupManager;
	
	public InvGoodsMovementManager getInvGoodsMovementManager() {
		return invGoodsMovementManager;
	}

	@Autowired
	public void setInvGoodsMovementManager(InvGoodsMovementManager invGoodsMovementManager) {
		this.invGoodsMovementManager = invGoodsMovementManager;
	}
	
	public LookupManager getLookupManager() {
		return lookupManager;
	}

	@Autowired
	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}

	@RequestMapping(value="/save" ,method = RequestMethod.POST)
	public ModelAndView save(InvGoodsMovement invGoodsMovementForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/invGoodsMovementList");
		}

		if (validator != null) { // validator is null during testing
			validator.validate(invGoodsMovementForm, errors);

			if (errors.hasErrors() && !StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) { // don't validate when deleting
				return new ModelAndView("invGoodsMovement", "invGoodsMovement", invGoodsMovementForm);
			}
		}
		log.info(request.getRemoteUser() + " is saving InvGoodsMovement := " + invGoodsMovementForm);

		Locale locale = request.getLocale();

		if (StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) {
			//since code input is readonly, no value pass to form then we need to query from db.
//			InvGoodReceipt InvGoodReceipt = getInvGoodReceiptManager().get(invGoodsMovementForm.getId());
			getInvGoodsMovementManager().remove(invGoodsMovementForm.getId());
			saveMessage(request, getText("invGoodsMovement.deleted", invGoodsMovementForm.getId(), locale));
			return new ModelAndView("redirect:/invGoodsMovementList");
		} else {
			
			HttpSession session = request.getSession();
			InvGoodsMovement invGoodsMovementSession = (InvGoodsMovement) session.getAttribute("invGoodsMovement");
			
			if (null == invGoodsMovementForm.getId()) {
				
				invGoodsMovementSession.setMovementDate(invGoodsMovementForm.getMovementDate());
				invGoodsMovementSession.setOwner(invGoodsMovementForm.getOwner());
				invGoodsMovementSession.setMovementType(invGoodsMovementForm.getMovementType());
				invGoodsMovementSession.setMemo(invGoodsMovementForm.getMemo());
				

				invGoodsMovementSession.setCreateDate(new Date());
				invGoodsMovementSession.setCreateUser(request.getRemoteUser());
				invGoodsMovementSession.setUpdateDate(new Date());
				invGoodsMovementSession.setUpdateUser(request.getRemoteUser());
				invGoodsMovementSession = getInvGoodsMovementManager().save(invGoodsMovementSession, invGoodsMovementSession.getInvGoodsMovementItems());
				
				if (StringUtils.equalsIgnoreCase("saveToStock", request.getParameter("action"))) {
					invGoodsMovementSession = getInvGoodsMovementManager().saveToStock(invGoodsMovementSession);
					saveMessage(request, getText("invGoodsMovement.added", invGoodsMovementSession.getDocumentNumber().getDocumentNo(), locale));
				} else {
					saveMessage(request, getText("invGoodsMovement.added", invGoodsMovementSession.getId(), locale));
				}

				return new ModelAndView("redirect:/invGoodsMovement").addObject("id", invGoodsMovementSession.getId());
			} else {
				// edit
				InvGoodsMovement invGoodsMovement = getInvGoodsMovementManager().get(invGoodsMovementForm.getId());
				invGoodsMovement.setMovementDate(invGoodsMovementForm.getMovementDate());
				invGoodsMovement.setOwner(invGoodsMovementForm.getOwner());
				invGoodsMovement.setMovementType(invGoodsMovementForm.getMovementType());
				invGoodsMovement.setMemo(invGoodsMovementForm.getMemo());
				invGoodsMovement.setUpdateDate(new Date());
				invGoodsMovement.setUpdateUser(request.getRemoteUser());
				invGoodsMovement = getInvGoodsMovementManager().save(invGoodsMovement, invGoodsMovementSession.getInvGoodsMovementItems());
				
				if (StringUtils.equalsIgnoreCase("saveToStock", request.getParameter("action"))) {
					invGoodsMovement = getInvGoodsMovementManager().saveToStock(invGoodsMovement);
					saveMessage(request, getText("invGoodsMovement.saved", invGoodsMovement.getDocumentNumber().getDocumentNo(), locale));
				} else {
					saveMessage(request, getText("invGoodsMovement.saved", invGoodsMovement.getId(), locale));
				}

				request.setAttribute("invGoodsMovement", invGoodsMovement);
				return new ModelAndView("redirect:/invGoodsMovementList");
			}
		}
	}
	
	@RequestMapping(value = "/addDetail", method = RequestMethod.POST)
	public ModelAndView add(InvGoodsMovement invGoodsMovementForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		InvGoodsMovement invGoodsMovement = (InvGoodsMovement) session.getAttribute("invGoodsMovement");
		invGoodsMovement.setMovementDate(invGoodsMovementForm.getMovementDate());
		invGoodsMovement.setOwner(invGoodsMovementForm.getOwner());
		invGoodsMovement.setMovementType(invGoodsMovementForm.getMovementType());
		invGoodsMovement.setMemo(invGoodsMovementForm.getMemo());
		invGoodsMovement.setDocumentNumber(invGoodsMovementForm.getDocumentNumber());
		return new ModelAndView("redirect:/invGoodsMovementItem?method=Add&from=list");
	}
	
	@RequestMapping(value = "/editDetail", method = RequestMethod.POST)
	public ModelAndView edit(InvGoodsMovement invGoodsMovementForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		InvGoodsMovement invGoodsMovement = (InvGoodsMovement) session.getAttribute("invGoodsMovement");
		invGoodsMovement.setMovementDate(invGoodsMovementForm.getMovementDate());
		invGoodsMovement.setOwner(invGoodsMovementForm.getOwner());
		invGoodsMovement.setMovementType(invGoodsMovementForm.getMovementType());
		invGoodsMovement.setMemo(invGoodsMovementForm.getMemo());
		invGoodsMovement.setDocumentNumber(invGoodsMovementForm.getDocumentNumber());
		return new ModelAndView("redirect:/invGoodsMovementItem?from=list&id=" + request.getParameter("id"));
	}

	@RequestMapping(value = "/deleteDetail", method = RequestMethod.POST)
	public ModelAndView delete(InvGoodsMovement invGoodsMovementForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		InvGoodsMovement invGoodsMovement = (InvGoodsMovement) session.getAttribute("invGoodsMovement");
		invGoodsMovement.setMovementDate(invGoodsMovementForm.getMovementDate());
		invGoodsMovement.setOwner(invGoodsMovementForm.getOwner());
		invGoodsMovement.setMovementType(invGoodsMovementForm.getMovementType());
		invGoodsMovement.setMemo(invGoodsMovementForm.getMemo());
		invGoodsMovement.setDocumentNumber(invGoodsMovementForm.getDocumentNumber());
		List<InvGoodsMovementItem> invGoodsMovementItemList = new ArrayList<InvGoodsMovementItem>(invGoodsMovement.getInvGoodsMovementItems());

		
		Locale locale = request.getLocale();
		String[] checkbox = request.getParameterValues("checkbox");
		if (null != checkbox && checkbox.length > 0) {
			for (int i = 0; i < checkbox.length; i++) {
				InvGoodsMovementItem invGoodsMovementItem = invGoodsMovementItemList.get(Integer.parseInt(checkbox[i]));
				invGoodsMovement.getInvGoodsMovementItems().remove(invGoodsMovementItem);
				saveMessage(request, getText("invGoodsMovementItem.deleted", invGoodsMovementItem.getInvItem().getCode(), locale));
			}
		} else {
			saveError(request, getText("global.errorNoCheckboxSelectForDelete", request.getLocale()));
		}
		return new ModelAndView("redirect:/invGoodsMovement");
	}


	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String goodsMovementNo = request.getParameter("goodsMovementNo");
		String method = request.getParameter("method");
		HttpSession session = request.getSession();
		
		//keep master value in session, always new object if method is add
		InvGoodsMovement invGoodsMovement = (InvGoodsMovement) session.getAttribute("invGoodsMovement");
		if (null == invGoodsMovement || StringUtils.equalsIgnoreCase(method, "add")) {
			invGoodsMovement = new InvGoodsMovement();
			invGoodsMovement.setMovementDate(new Date());
		}
		
		if (!isFormSubmission(request)) {
			if (id != null) {
				invGoodsMovement = getInvGoodsMovementManager().get(Long.valueOf(id));
			} else if (goodsMovementNo != null) {
				invGoodsMovement = getInvGoodsMovementManager().findByGoodsMovementNo(goodsMovementNo);
			}
		} else {
			invGoodsMovement = getInvGoodsMovementManager().get(Long.valueOf(id));
		}
		
		session.setAttribute("invGoodsMovement", invGoodsMovement);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("invGoodsMovement", invGoodsMovement);
		model.put("invGoodsMovementItemList", invGoodsMovement.getInvGoodsMovementItems());
		model.put("movementTypeList", lookupManager.getAllInvGoodsMovementType(request.getLocale()));
		return new ModelAndView("invGoodsMovement", model);
	}

	private boolean isFormSubmission(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}

}
