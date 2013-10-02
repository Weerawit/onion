package com.worldbestsoft.webapp.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.worldbestsoft.model.ConstantModel;
import com.worldbestsoft.model.InvItem;
import com.worldbestsoft.model.InvItemLevel;
import com.worldbestsoft.model.InvStock;
import com.worldbestsoft.service.InvItemManager;
import com.worldbestsoft.service.InvStockManager;

@Controller
@RequestMapping("/invStock*")
public class InvStockFormController extends BaseFormController {

	private InvStockManager invStockManager;
	private InvItemManager invItemManager;
	public InvStockManager getInvStockManager() {
		return invStockManager;
	}

	@Autowired
	public void setInvStockManager(InvStockManager invStockManager) {
		this.invStockManager = invStockManager;
	}
	
	public InvItemManager getInvItemManager() {
		return invItemManager;
	}

	@Autowired
	public void setInvItemManager(InvItemManager invItemManager) {
		this.invItemManager = invItemManager;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(InvStock invStockForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/invStockList");
		}

		if (validator != null) { // validator is null during testing
			validator.validate(invStockForm, errors);
			
			if (null != invStockForm.getInvItem()) {
				InvItem invItem = getInvItemManager().findByInvItemCode(invStockForm.getInvItem().getCode());
				invStockForm.setInvItem(invItem);
				if (null == invItem) {
					errors.rejectValue("invItem.code", "errors.invalid",new Object[] { getText("invStock.invItem.code", request.getLocale())}, "errors.invalid");	
				}
			}

			if (errors.hasErrors() && request.getParameter("delete") == null) { // don't
				                                                                // validate
				                                                                // when
				                                                                // deleting
				return new ModelAndView("invStock", "invStock", invStockForm);
			}
		}
		log.info(request.getRemoteUser() + " is saving InvStock := " + invStockForm);

		Locale locale = request.getLocale();
		
		InvItemLevel invItemLevel = new InvItemLevel();
		
		invItemLevel.setInvItem(invStockForm.getInvItem());
		invItemLevel.setQtyAdjust(invStockForm.getQty());
		invItemLevel.setQtyAvailableAdjust(invStockForm.getQty());
		invItemLevel.setTransactionDate(new Date());
		invItemLevel.setUpdateUser(request.getRemoteUser());
		invItemLevel.setRefType(ConstantModel.RefType.ADJUST.getCode());
		invItemLevel.setTransactionType(ConstantModel.ItemStockTransactionType.COMMIT.getCode());

		InvStock invStock = getInvStockManager().updateStock(invItemLevel);

		request.setAttribute("invStock", invStock);
		saveMessage(request, getText("invStock.saved", invStockForm.getInvItem().getCode(), locale));
		return new ModelAndView("redirect:/invStockList");
	}

	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		InvStock invStock = new InvStock();
		if (!isFormSubmission(request)) {
			if (id != null) {
				invStock = getInvStockManager().get(Long.valueOf(id));
			}
		} else {
			invStock = getInvStockManager().get(Long.valueOf(id));
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("invStock", invStock);
		return new ModelAndView("invStock", model);
	}

	private boolean isFormSubmission(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}

}
