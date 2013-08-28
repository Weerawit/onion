package com.worldbestsoft.webapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.worldbestsoft.model.Catalog;
import com.worldbestsoft.model.CatalogItem;
import com.worldbestsoft.model.CatalogType;
import com.worldbestsoft.model.criteria.CatalogForm;
import com.worldbestsoft.service.CatalogImageChangedEvent;
import com.worldbestsoft.service.CatalogManager;
import com.worldbestsoft.service.CatalogTypeManager;

@Controller
@RequestMapping("/catalog*")
public class CatalogFormController extends BaseFormController implements ApplicationContextAware {

	private CatalogManager catalogManager;
	private CatalogTypeManager catalogTypeManager;
	private ApplicationContext context;

	public CatalogManager getCatalogManager() {
		return catalogManager;
	}

	@Autowired
	public void setCatalogManager(CatalogManager catalogManager) {
		this.catalogManager = catalogManager;
	}

	public CatalogTypeManager getCatalogTypeManager() {
		return catalogTypeManager;
	}

	@Autowired
	public void setCatalogTypeManager(CatalogTypeManager catalogTypeManager) {
		this.catalogTypeManager = catalogTypeManager;
	}
	

	@Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
	    this.context = arg0;
    }

	@RequestMapping(value = "/upload")
	public @ResponseBody Map upload(FileUpload fileUpload, BindingResult errors, HttpServletRequest request, HttpServletResponse response) {

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");

		List<Map<String, String>> fileJsonList = new ArrayList<Map<String,String>>();
		try {
			String fileId = RandomStringUtils.randomAlphanumeric(10);
			File tmpFile = new File(FileUtils.getTempDirectory(), fileId);
			OutputStream out = new FileOutputStream(tmpFile);
			InputStream in = file.getInputStream();

			IOUtils.copyLarge(in, out);

			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
			
			Map<String, String> fileAttribute = new HashMap<String, String>();
			fileAttribute.put("name", fileId);
			fileAttribute.put("size", file.getSize() + "");
			fileAttribute.put("thumbnailUrl", response.encodeRedirectURL(request.getContextPath() + "/img/thumbnail/" + fileId + "?t=200"));
			fileAttribute.put("url", response.encodeRedirectURL(request.getContextPath() + "/img/thumbnail/" + fileId + "?t=800"));

			fileJsonList.add(fileAttribute);
		} catch (IOException e) {

		}
		
		Map jsonMap = new HashMap();
		jsonMap.put("files", fileJsonList);
		return jsonMap;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("catalog") CatalogForm catalogForm, BindingResult errors, HttpServletRequest request) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/catalogList");
		}
		
		HttpSession session = request.getSession();
		CatalogForm catalogFormSession = (CatalogForm) session.getAttribute("catalogForm");
		

		if (validator != null) { // validator is null during testing
			validator.validate(catalogForm, errors);

			if (null == catalogForm.getId()) {
				if (getCatalogManager().checkDuplicate(catalogForm.getCode(), null)) {
					errors.rejectValue("code", "catalog.duplicate", new Object[] { getText("catalog.code", request.getLocale()) }, "catalog.duplicate");
				}
			}

			if (errors.hasErrors() && !StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) { // don't validate when deleting
				return new ModelAndView("catalog", "catalog", catalogForm).addObject("catalogTypeList", catalogTypeManager.getAllCatalogType()).addObject("catalogItemList", catalogFormSession.getCatalogItems());
			}
		}
		log.info(request.getRemoteUser() + " is saving Catalog := " + catalogForm);

		Locale locale = request.getLocale();

		if (StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) {
			// since code input is readonly, no value pass to form then we need
			// to query from db.
			// Catalog catalog = getCatalogManager().get(catalogForm.getId());
			getCatalogManager().remove(catalogForm.getId());
			saveMessage(request, getText("catalog.deleted", catalogForm.getCode(), locale));
			return new ModelAndView("redirect:/catalogList");
		} else {

			if (null == catalogForm.getId()) {
				
				Catalog catalog = new Catalog();
				PropertyUtils.copyProperties(catalog, catalogForm);
				// add
				catalog.setCreateDate(new Date());
				catalog.setCreateUser(request.getRemoteUser());

				if (null != catalogForm.getCatalogType()) {
					CatalogType catalogType = getCatalogTypeManager().findByCatalogTypeCode(catalogForm.getCatalogType().getCode());
					catalog.setCatalogType(catalogType);
				}
				

				// update img & thunbnail if exist
				if (StringUtils.isNotBlank(catalogForm.getFilename())) {
					
					//read to byte[]
					File tmpFile = new File(FileUtils.getTempDirectory(), catalogForm.getFilename());
					
					if (tmpFile.exists()) {
						catalog.setImg(FileUtils.readFileToByteArray(tmpFile));
					}
				}

				catalog = getCatalogManager().save(catalog, catalogFormSession.getCatalogItems());

				saveMessage(request, getText("catalog.added", catalog.getCode(), locale));
				return new ModelAndView("redirect:/catalog").addObject("id", catalog.getId());
			} else {
				// edit

				Catalog catalog = getCatalogManager().get(catalogForm.getId());
				catalog.setName(catalogForm.getName());
				if (null != catalogForm.getCatalogType()) {
					CatalogType catalogType = getCatalogTypeManager().findByCatalogTypeCode(catalogForm.getCatalogType().getCode());
					catalog.setCatalogType(catalogType);
				}
				catalog.setFinalPrice(catalogForm.getFinalPrice());
				catalog.setEstPrice(catalogForm.getEstPrice());
				// update img & thunbnail if exist
				if (StringUtils.isNotBlank(catalogForm.getFilename())) {
					
					//read to byte[]
					File tmpFile = new File(FileUtils.getTempDirectory(), catalogForm.getFilename());
					
					if (tmpFile.exists()) {
						InputStream in = new FileInputStream(tmpFile);
						catalog.setImg(FileUtils.readFileToByteArray(tmpFile));
						
						
						//delete old image.
						CatalogImageChangedEvent event = new CatalogImageChangedEvent(catalog);
						context.publishEvent(event);
					}
				}

				catalog.setUpdateDate(new Date());
				catalog.setUpdateUser(request.getRemoteUser());
				
				
				catalog = getCatalogManager().save(catalog, catalogFormSession.getCatalogItems());

				request.setAttribute("catalog", catalog);
				saveMessage(request, getText("catalog.saved", catalog.getCode(), locale));
				return new ModelAndView("redirect:/catalogList");
			}
		}
	}

	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String method = request.getParameter("method");
		HttpSession session = request.getSession();
		
		CatalogForm catalogForm = (CatalogForm) session.getAttribute("catalogForm");
		if (null == catalogForm || StringUtils.equalsIgnoreCase(method, "add")) {
			catalogForm = new CatalogForm();
		}
		
		Catalog catalog = new Catalog();
		if (!isFormSubmission(request)) {
			if (id != null) {
				catalog = getCatalogManager().get(Long.valueOf(id));
				PropertyUtils.copyProperties(catalogForm, catalog);
			}
		} else {
			catalog = getCatalogManager().get(Long.valueOf(id));
			PropertyUtils.copyProperties(catalogForm, catalog);
		}
		session.setAttribute("catalogForm", catalogForm);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("catalog", catalogForm);
		model.put("catalogTypeList", catalogTypeManager.getAllCatalogType());
		model.put("catalogItemList", catalogForm.getCatalogItems());
		return new ModelAndView("catalog", model);
	}

	private boolean isFormSubmission(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}
	
	@RequestMapping(value = "/addDetail", method = RequestMethod.POST)
	public ModelAndView add(CatalogForm catalogForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		CatalogForm catalogSession = (CatalogForm) session.getAttribute("catalogForm");
		catalogSession.setCatalogType(catalogForm.getCatalogType());
		catalogSession.setCode(catalogForm.getCode());
		catalogSession.setName(catalogForm.getName());
		catalogSession.setFilename(catalogForm.getFilename());
		catalogSession.setEstPrice(catalogForm.getEstPrice());
		catalogSession.setFinalPrice(catalogForm.getFinalPrice());
		return new ModelAndView("redirect:/catalogItem?method=Add&from=list");
	}
	
	@RequestMapping(value = "/editDetail", method = RequestMethod.POST)
	public ModelAndView edit(CatalogForm catalogForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		CatalogForm catalogSession = (CatalogForm) session.getAttribute("catalogForm");
		catalogSession.setCatalogType(catalogForm.getCatalogType());
		catalogSession.setCode(catalogForm.getCode());
		catalogSession.setName(catalogForm.getName());
		catalogSession.setFilename(catalogForm.getFilename());
		catalogSession.setEstPrice(catalogForm.getEstPrice());
		catalogSession.setFinalPrice(catalogForm.getFinalPrice());
		return new ModelAndView("redirect:/catalogItem?from=list&id=" + request.getParameter("id"));
	}

	@RequestMapping(value = "/deleteDetail", method = RequestMethod.POST)
	public ModelAndView delete(CatalogForm catalogForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		CatalogForm catalogSession = (CatalogForm) session.getAttribute("catalogForm");
		catalogSession.setCatalogType(catalogForm.getCatalogType());
		catalogSession.setCode(catalogForm.getCode());
		catalogSession.setName(catalogForm.getName());
		catalogSession.setFilename(catalogForm.getFilename());
		catalogSession.setEstPrice(catalogForm.getEstPrice());
		catalogSession.setFinalPrice(catalogForm.getFinalPrice());
		List<CatalogItem> catalogItemList = new ArrayList<CatalogItem>(catalogSession.getCatalogItems());

		
		Locale locale = request.getLocale();
		String[] checkbox = request.getParameterValues("checkbox");
		if (null != checkbox && checkbox.length > 0) {
			for (int i = 0; i < checkbox.length; i++) {
				CatalogItem catalogItem = catalogItemList.get(Integer.parseInt(checkbox[i]));
				catalogSession.getCatalogItems().remove(catalogItem);
				saveMessage(request, getText("catalogItem.deleted", catalogItem.getInvItem().getCode(), locale));
			}
		} else {
			saveError(request, getText("global.errorNoCheckboxSelectForDelete", request.getLocale()));
		}
		return new ModelAndView("redirect:/catalog");
	}


}
