package com.worldbestsoft.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.worldbestsoft.model.InvItem;
import com.worldbestsoft.model.InvStock;
import com.worldbestsoft.service.InvStockManager;

@Controller
@RequestMapping("/invStockList*")
public class InvStockListController extends BaseFormController {

	private InvStockManager invStockManager;
	
	public InvStockManager getInvStockManager() {
		return invStockManager;
	}

	@Autowired
	public void setInvStockManager(InvStockManager invStockManager) {
		this.invStockManager = invStockManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		InvStock criteria = new InvStock();
		InvItem invItem = new InvItem();
		invItem.setCode(request.getParameter("invItem.code"));
		criteria.setInvItem(invItem);
		int page = getPage(request);
		String sortColumn = getSortColumn(request);
		String order = getSortOrder(request);
		int size = getPageSize(request);

		log.info(request.getRemoteUser() + " is quering InvStock criteria := " + criteria);

		Model model = new ExtendedModelMap();
		model.addAttribute("resultSize", invStockManager.querySize(criteria));
		model.addAttribute("invStockList", invStockManager.query(criteria, page, size, sortColumn, order));
		return new ModelAndView("invStockList", model.asMap());
	}

}
