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

import com.worldbestsoft.model.DocumentNumber;
import com.worldbestsoft.model.InvItem;
import com.worldbestsoft.model.LabelValue;
import com.worldbestsoft.model.criteria.InvItemLevelCriteria;
import com.worldbestsoft.service.InvItemLevelManager;
import com.worldbestsoft.service.LookupManager;

@Controller
@RequestMapping("/invItemLevelList*")
public class InvItemLevelListController extends BaseFormController {
	
	private static final String[] DATE_PATTERN = new String[] {"dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy"};

	private LookupManager lookupManager;
	private InvItemLevelManager invItemLevelManager;
	
	public InvItemLevelManager getInvItemLevelManager() {
		return invItemLevelManager;
	}

	@Autowired
	public void setInvItemLevelManager(InvItemLevelManager invItemLevelManager) {
		this.invItemLevelManager = invItemLevelManager;
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
		model.addAttribute("itemStockTransactionTypeList", lookupManager.getAllItemStockTransactionType(request.getLocale()));
		model.addAttribute("refTypeList", lookupManager.getAllRefType(request.getLocale()));
		
		InvItemLevelCriteria criteria = new InvItemLevelCriteria();
		InvItem invItem = new InvItem();
		invItem.setCode(request.getParameter("invItem.code"));
		criteria.setInvItem(invItem);
		
		DocumentNumber documentNumber = new DocumentNumber();
		documentNumber.setDocumentNo(request.getParameter("documentNumber.documentNo"));
		criteria.setDocumentNumber(documentNumber);
		String startTime = request.getParameter("transactionDateFrom");
		String endTime = request.getParameter("transactionDateTo");
		try {
			if (StringUtils.isNotBlank(startTime)) {
				Date startTimeDate = DateUtils.parseDateStrictly(startTime, DATE_PATTERN);
				criteria.setTransactionDateFrom(startTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { startTime }, request.getLocale()));
			return new ModelAndView("invItemLevelList", model.asMap());
		}
		try {
			if (StringUtils.isNotBlank(endTime)) {
				Date endTimeDate = DateUtils.parseDate(endTime, DATE_PATTERN);
				criteria.setTransactionDateTo(endTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { endTime }, request.getLocale()));
			return new ModelAndView("invItemLevelList", model.asMap());
		}

		
		int page = getPage(request);
		String sortColumn = getSortColumn(request);
		String order = getSortOrder(request);
		int size = getPageSize(request);

		log.info(request.getRemoteUser() + " is quering invItemLevelList criteria := " + criteria);

		model.addAttribute("resultSize", invItemLevelManager.querySize(criteria));
		model.addAttribute("invItemLevelList", invItemLevelManager.query(criteria, page, size, sortColumn, order));
		return new ModelAndView("invItemLevelList", model.asMap());
	}

}
