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

import com.worldbestsoft.model.InvGoodsReceipt;
import com.worldbestsoft.model.InvGoodsReceiptItem;
import com.worldbestsoft.model.InvItem;
import com.worldbestsoft.service.InvGoodsReceiptItemManager;
import com.worldbestsoft.service.InvItemManager;

@Controller
@RequestMapping("/invGoodsReceiptItem*")
public class InvGoodsReceiptItemFormController extends BaseFormController {
	
	private InvGoodsReceiptItemManager invGoodsReceiptItemManager;
	private InvItemManager invItemManager;
	
	public InvGoodsReceiptItemManager getInvGoodsReceiptItemManager() {
		return invGoodsReceiptItemManager;
	}

	@Autowired
	public void setInvGoodsReceiptItemManager(InvGoodsReceiptItemManager invGoodsReceiptItemManager) {
		this.invGoodsReceiptItemManager = invGoodsReceiptItemManager;
	}
	
	public InvItemManager getInvItemManager() {
		return invItemManager;
	}

	@Autowired
	public void setInvItemManager(InvItemManager invItemManager) {
		this.invItemManager = invItemManager;
	}

    @RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(InvGoodsReceiptItem invGoodsReceiptItemForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/invGoodsReceipt");
		}

		if (validator != null) { // validator is null during testing
			validator.validate(invGoodsReceiptItemForm, errors);

			if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting
				return new ModelAndView("invGoodsReceiptItem", "invGoodsReceiptItem", invGoodsReceiptItemForm);
			}
		}
		log.info(request.getRemoteUser() + " is saving InvGoodsReceiptItem := " + invGoodsReceiptItemForm);

		Locale locale = request.getLocale();
		HttpSession session = request.getSession();
		
		InvGoodsReceipt invGoodsReceipt = (InvGoodsReceipt) session.getAttribute("invGoodsReceipt");
//        List<InvGoodReceiptItem> invGoodsReceiptItemList = (List<InvGoodReceiptItem>) session.getAttribute("invGoodsReceiptItemList");
		Set<InvGoodsReceiptItem> invGoodsReceiptItemList = invGoodsReceipt.getInvGoodsReceiptItems();
		
		String rowNum = request.getParameter("rowNum");

		if (request.getParameter("delete") != null) {
			//remove from session list.
			InvGoodsReceiptItem invGoodsReceiptItem = (InvGoodsReceiptItem) CollectionUtils.find(invGoodsReceiptItemList, new BeanPropertyValueEqualsPredicate("invItem.code", invGoodsReceiptItemForm.getInvItem().getCode()));
			invGoodsReceiptItemList.remove(invGoodsReceiptItem);
//			try {
//				invGoodsReceiptItemList.remove(Integer.parseInt(rowNum));
//			} catch (Exception e) {
//				saveError(request, getText("errors.invalid", rowNum, locale));
//				return new ModelAndView("redirect:/invGoodsReceiptItem?from=list");
//			} 
			
			saveMessage(request, getText("invGoodsReceiptItem.deleted", invGoodsReceiptItemForm.getInvItem().getCode(), locale));
			return new ModelAndView("redirect:/invGoodsReceipt");
		} else {
			
			
			if (StringUtils.isBlank(rowNum)) {
				//add to list
				//check dup from session
				InvGoodsReceiptItem invGoodsReceiptItem = (InvGoodsReceiptItem) CollectionUtils.find(invGoodsReceiptItemList, new BeanPropertyValueEqualsPredicate("invItem.code", invGoodsReceiptItemForm.getInvItem().getCode()));
				if (null != invGoodsReceiptItem) {
					saveError(request, getText("invGoodsReceiptItem.duplicate", rowNum, locale));
					return new ModelAndView("invGoodsReceiptItem", "invGoodsReceiptItem", invGoodsReceiptItemForm).addObject("invItemList", invItemManager.getAll());
				}
				
				if (null != invGoodsReceiptItemForm.getInvItem()) {
					InvItem invItem = getInvItemManager().findByInvItemCode(invGoodsReceiptItemForm.getInvItem().getCode());
					invGoodsReceiptItemForm.setInvItem(invItem);
				}
				
				invGoodsReceiptItemList.add(invGoodsReceiptItemForm);
				
				saveMessage(request, getText("invGoodsReceiptItem.added", invGoodsReceiptItemForm.getInvItem().getCode(), locale));
				return new ModelAndView("redirect:/invGoodsReceipt");
			} else {
				//update old object
				List<InvGoodsReceiptItem> invGoodsReceiptItemList2 = new ArrayList<InvGoodsReceiptItem>(invGoodsReceipt.getInvGoodsReceiptItems());
				try {
					InvGoodsReceiptItem invGoodsReceiptItem = invGoodsReceiptItemList2.get(Integer.parseInt(rowNum));
					
					if (null != invGoodsReceiptItemForm.getInvItem()) {
						InvItem invItem = getInvItemManager().findByInvItemCode(invGoodsReceiptItemForm.getInvItem().getCode());
						invGoodsReceiptItemForm.setInvItem(invItem);
					}
					
					invGoodsReceiptItem.setQty(invGoodsReceiptItemForm.getQty());
					invGoodsReceiptItem.setUnitPrice(invGoodsReceiptItemForm.getUnitPrice());
					invGoodsReceiptItem.setUnitType(invGoodsReceiptItemForm.getUnitType());
					invGoodsReceiptItem.setMemo(invGoodsReceiptItemForm.getMemo());
					
					saveMessage(request, getText("invGoodsReceiptItem.saved", invGoodsReceiptItemForm.getInvItem().getCode(), locale));
					return new ModelAndView("redirect:/invGoodsReceipt");
					
				} catch (Exception e) {
					saveError(request, getText("errors.invalid", rowNum, locale));
					return new ModelAndView("redirect:/invGoodsReceipt?from=list");
				} 
				
			}
		}
	}
	
	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		
		//parameter 'id' is index of array list from session object
		HttpSession session = request.getSession();
		InvGoodsReceipt invGoodsReceipt = (InvGoodsReceipt) session.getAttribute("invGoodsReceipt");
		List<InvGoodsReceiptItem> invGoodsReceiptItemList = new ArrayList<InvGoodsReceiptItem>(invGoodsReceipt.getInvGoodsReceiptItems());
		InvGoodsReceiptItem invGoodsReceiptItem = new InvGoodsReceiptItem();
		try {
			Integer index = Integer.parseInt(id);
			if (index < invGoodsReceiptItemList.size()) {
				invGoodsReceiptItem = invGoodsReceiptItemList.get(index);
			}
		} catch (Exception e) {
			//no handle exception; any exception mean new object.
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("invGoodsReceiptItem", invGoodsReceiptItem);
		model.put("invItemList", invItemManager.getAll());
		return new ModelAndView("invGoodsReceiptItem", model);
	}

}
