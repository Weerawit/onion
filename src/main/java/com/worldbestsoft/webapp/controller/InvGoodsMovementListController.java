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

import com.worldbestsoft.model.criteria.InvGoodsMovementCriteria;
import com.worldbestsoft.service.InvGoodsMovementManager;
import com.worldbestsoft.service.LookupManager;

@Controller
@RequestMapping("/invGoodsMovementList*")
public class InvGoodsMovementListController extends BaseFormController {
	
	private static final String[] DATE_PATTERN = new String[] {"dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy"};

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

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Model model = new ExtendedModelMap();

		InvGoodsMovementCriteria criteria = new InvGoodsMovementCriteria();
		criteria.setRunningNo(request.getParameter("runningNo"));
		criteria.setOwner(request.getParameter("owner"));
		criteria.setMovementType(request.getParameter("movementType"));
		String startTime = request.getParameter("movementDateFrom");
		String endTime = request.getParameter("movementDateTo");
		try {
			if (StringUtils.isNotBlank(startTime)) {
				Date startTimeDate = DateUtils.parseDateStrictly(startTime, DATE_PATTERN);
				criteria.setMovementDateFrom(startTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { startTime }, request.getLocale()));
			return new ModelAndView("invGoodsMovementList", model.asMap());
		}
		try {
			if (StringUtils.isNotBlank(endTime)) {
				Date endTimeDate = DateUtils.parseDate(endTime, DATE_PATTERN);
				criteria.setMovementDateTo(endTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { endTime }, request.getLocale()));
			return new ModelAndView("invGoodsMovementList", model.asMap());
		}

		
		int page = getPage(request);
		String sortColumn = getSortColumn(request);
		String order = getSortOrder(request);
		int size = getPageSize(request);

		log.info(request.getRemoteUser() + " is quering InvGoodsMovement criteria := " + criteria);

		model.addAttribute("resultSize", invGoodsMovementManager.querySize(criteria));
		model.addAttribute("invGoodsMovementList", invGoodsMovementManager.query(criteria, page, size, sortColumn, order));
		model.addAttribute("movementTypeList", lookupManager.getAllInvGoodsMovementType(request.getLocale()));
		return new ModelAndView("invGoodsMovementList", model.asMap());
	}

}
