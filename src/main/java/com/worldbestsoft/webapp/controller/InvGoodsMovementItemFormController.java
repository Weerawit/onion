package com.worldbestsoft.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.worldbestsoft.model.InvGoodsMovement;
import com.worldbestsoft.model.InvGoodsMovementItem;
import com.worldbestsoft.model.InvItem;
import com.worldbestsoft.service.InvGoodsMovementItemManager;
import com.worldbestsoft.service.InvItemManager;

@Controller
@RequestMapping("/invGoodsMovementItem*")
public class InvGoodsMovementItemFormController extends BaseFormController {
	
	private InvGoodsMovementItemManager invGoodsMovementItemManager;
	private InvItemManager invItemManager;
	
	
	public InvGoodsMovementItemManager getInvGoodsMovementItemManager() {
		return invGoodsMovementItemManager;
	}

	@Autowired
	public void setInvGoodsMovementItemManager(InvGoodsMovementItemManager invGoodsMovementItemManager) {
		this.invGoodsMovementItemManager = invGoodsMovementItemManager;
	}

	public InvItemManager getInvItemManager() {
		return invItemManager;
	}

	@Autowired
	public void setInvItemManager(InvItemManager invItemManager) {
		this.invItemManager = invItemManager;
	}

    @RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(InvGoodsMovementItem invGoodsMovementItemForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/invGoodsMovement");
		}

		if (validator != null) { // validator is null during testing
			validator.validate(invGoodsMovementItemForm, errors);
			if (null != invGoodsMovementItemForm.getInvItem()) {
				InvItem invItem = getInvItemManager().findByInvItemCode(invGoodsMovementItemForm.getInvItem().getCode());
				invGoodsMovementItemForm.setInvItem(invItem);
				if (null == invItem) {
					errors.rejectValue("invItem.code", "errors.invalid",new Object[] { getText("invGoodsMovementItem.invItem.code", request.getLocale())}, "errors.invalid");	
				}
			}

			if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting
				return new ModelAndView("invGoodsMovementItem", "invGoodsMovementItem", invGoodsMovementItemForm);
			}
		}
		log.info(request.getRemoteUser() + " is saving InvGoodsMovementItem := " + invGoodsMovementItemForm);

		Locale locale = request.getLocale();
		HttpSession session = request.getSession();
		
		InvGoodsMovement invGoodsMovement = (InvGoodsMovement) session.getAttribute("invGoodsMovement");
//        List<InvGoodReceiptItem> invGoodsMovementItemList = (List<InvGoodReceiptItem>) session.getAttribute("invGoodsMovementItemList");
		Set<InvGoodsMovementItem> invGoodsMovementItemList = invGoodsMovement.getInvGoodsMovementItems();
		
		String rowNum = request.getParameter("rowNum");

		if (request.getParameter("delete") != null) {
			//remove from session list.
			InvGoodsMovementItem invGoodsMovementItem = (InvGoodsMovementItem) CollectionUtils.find(invGoodsMovementItemList, new BeanPropertyValueEqualsPredicate("invItem.code", invGoodsMovementItemForm.getInvItem().getCode()));
			invGoodsMovementItemList.remove(invGoodsMovementItem);
			
			saveMessage(request, getText("invGoodsMovementItem.deleted", invGoodsMovementItemForm.getInvItem().getCode(), locale));
			return new ModelAndView("redirect:/invGoodsMovement");
		} else {
			
			
			if (StringUtils.isBlank(rowNum)) {
				//add to list
				//check dup from session
				InvGoodsMovementItem invGoodsMovementItem = (InvGoodsMovementItem) CollectionUtils.find(invGoodsMovementItemList, new BeanPropertyValueEqualsPredicate("invItem.code", invGoodsMovementItemForm.getInvItem().getCode()));
				if (null != invGoodsMovementItem) {
					saveError(request, getText("invGoodsMovementItem.duplicate", rowNum, locale));
					return new ModelAndView("invGoodsMovementItem", "invGoodsMovementItem", invGoodsMovementItemForm).addObject("invItemList", invItemManager.getAll());
				}
				
				if (null != invGoodsMovementItemForm.getInvItem()) {
					InvItem invItem = getInvItemManager().findByInvItemCode(invGoodsMovementItemForm.getInvItem().getCode());
					invGoodsMovementItemForm.setInvItem(invItem);
				}
				
				invGoodsMovementItemList.add(invGoodsMovementItemForm);
				
				saveMessage(request, getText("invGoodsMovementItem.added", invGoodsMovementItemForm.getInvItem().getCode(), locale));
				return new ModelAndView("redirect:/invGoodsMovement");
			} else {
				//update old object
				List<InvGoodsMovementItem> invGoodsMovementItemList2 = new ArrayList<InvGoodsMovementItem>(invGoodsMovement.getInvGoodsMovementItems());
				try {
					InvGoodsMovementItem invGoodsMovementItem = invGoodsMovementItemList2.get(Integer.parseInt(rowNum));
					
					if (null != invGoodsMovementItemForm.getInvItem()) {
						InvItem invItem = getInvItemManager().findByInvItemCode(invGoodsMovementItemForm.getInvItem().getCode());
						invGoodsMovementItemForm.setInvItem(invItem);
					}
					
					invGoodsMovementItem.setQty(invGoodsMovementItemForm.getQty());
					invGoodsMovementItem.setMemo(invGoodsMovementItemForm.getMemo());
					
					saveMessage(request, getText("invGoodsMovementItem.saved", invGoodsMovementItemForm.getInvItem().getCode(), locale));
					return new ModelAndView("redirect:/invGoodsMovement");
					
				} catch (Exception e) {
					saveError(request, getText("errors.invalid", rowNum, locale));
					return new ModelAndView("redirect:/invGoodsMovement?from=list");
				} 
				
			}
		}
	}
	
	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		
		//parameter 'id' is index of array list from session object
		HttpSession session = request.getSession();
		InvGoodsMovement invGoodsMovement = (InvGoodsMovement) session.getAttribute("invGoodsMovement");
		if (null != invGoodsMovement) {
			List<InvGoodsMovementItem> invGoodsMovementItemList = new ArrayList<InvGoodsMovementItem>(invGoodsMovement.getInvGoodsMovementItems());
			InvGoodsMovementItem invGoodsMovementItem = new InvGoodsMovementItem();
			try {
				Integer index = Integer.parseInt(id);
				if (index < invGoodsMovementItemList.size()) {
					invGoodsMovementItem = invGoodsMovementItemList.get(index);
				}
			} catch (Exception e) {
				//no handle exception; any exception mean new object.
			}
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("invGoodsMovementItem", invGoodsMovementItem);
			return new ModelAndView("invGoodsMovementItem", model);
		} else {
			//since no object in session, redirect to Inv Good Receipt page
			return new ModelAndView("redirect:/invGoodsMovement");
		}
	}

}
