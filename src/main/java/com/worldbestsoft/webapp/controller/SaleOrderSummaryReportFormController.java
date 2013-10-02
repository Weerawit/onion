package com.worldbestsoft.webapp.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.worldbestsoft.model.Catalog;
import com.worldbestsoft.model.Employee;
import com.worldbestsoft.model.JobOrder;
import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.model.SaleReceipt;
import com.worldbestsoft.model.criteria.DownloadModel;
import com.worldbestsoft.service.CatalogManager;
import com.worldbestsoft.service.EmployeeManager;
import com.worldbestsoft.service.JobOrderManager;
import com.worldbestsoft.service.LookupManager;
import com.worldbestsoft.service.SaleOrderManager;
import com.worldbestsoft.webapp.util.ReportUtil.ExportType;

@Controller
@RequestMapping("/report/saleOrderSummaryReport*")
public class SaleOrderSummaryReportFormController extends BaseFormController {
	
	private LookupManager lookupManager;
	private JobOrderManager jobOrderManager;
	private EmployeeManager employeeManager;
	private SaleOrderManager saleOrderManager;
	private CatalogManager catalogManager;
	
	public LookupManager getLookupManager() {
		return lookupManager;
	}

	@Autowired
	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}

	public JobOrderManager getJobOrderManager() {
		return jobOrderManager;
	}

	@Autowired
	public void setJobOrderManager(JobOrderManager jobOrderManager) {
		this.jobOrderManager = jobOrderManager;
	}
	
	public EmployeeManager getEmployeeManager() {
		return employeeManager;
	}

	@Autowired
	public void setEmployeeManager(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}

	public SaleOrderManager getSaleOrderManager() {
		return saleOrderManager;
	}

	@Autowired
	public void setSaleOrderManager(SaleOrderManager saleOrderManager) {
		this.saleOrderManager = saleOrderManager;
	}
	
	public CatalogManager getCatalogManager() {
		return catalogManager;
	}

	@Autowired
	public void setCatalogManager(CatalogManager catalogManager) {
		this.catalogManager = catalogManager;
	}
	

	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
//		JobOrder jobOrder = new JobOrder();
//		if (!isFormSubmission(request)) {
//			if (id != null) {
//				jobOrder = getJobOrderManager().get(Long.valueOf(id));
//			}
//		} else {
//			jobOrder = getJobOrderManager().get(Long.valueOf(id));
//		}
		Map<String, Object> model = new HashMap<String, Object>();
//		model.put("jobOrder", jobOrder);
//		model.put("jobOrderStatusList", lookupManager.getAllJobOrderStatus(request.getLocale()));
		return new ModelAndView("saleOrderSummaryReport", model);
	}
	
	@RequestMapping(value = "/displayTable")
	protected ModelAndView displayTable(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String catalogCode = request.getParameter("catalog.code");
		
		Catalog catalog = getCatalogManager().findByCatalogCode(catalogCode);
		Map<String, Object> model = new HashMap<String, Object>();
		if (null != catalog) { 
			model.put("catalogItemList", catalog.getCatalogItems());
		}
		return new ModelAndView("jobOrderTable", model);
	}

	private boolean isFormSubmission(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}
	
	@RequestMapping(value = "/download")
	public void download(@RequestParam("type") String type,
			@RequestParam("token") String token, 
			@RequestParam("id") String id,
			HttpServletResponse response) throws JRException, IOException {
		JobOrder jobOrder = getJobOrderManager().get(Long.valueOf(id));
		ExportType exportType = ExportType.fromString(type);
		String jrxmlFile = "/reports/JobOrder.jrxml";
//		String jrxmlFile = "/Users/Weerawit/Documents/Projects/company_wbs/wbs/projects/inventory_wood/git/onion/src/main/resources/reports/JobOrder.jrxml";
		String downloadFileName = "job";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jobOrder", jobOrder);
		
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
		
		downloadModel.setJrDataSource(new JRBeanCollectionDataSource(jobOrder.getCatalog().getCatalogItems()));
		
		getReportUtil().download(downloadModel);
	}

}
