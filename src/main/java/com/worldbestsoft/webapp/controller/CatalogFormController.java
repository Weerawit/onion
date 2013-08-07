package com.worldbestsoft.webapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.worldbestsoft.model.Catalog;
import com.worldbestsoft.model.CatalogType;
import com.worldbestsoft.model.criteria.CatalogForm;
import com.worldbestsoft.service.CatalogManager;
import com.worldbestsoft.service.CatalogTypeManager;

@Controller
@RequestMapping("/catalog*")
public class CatalogFormController extends BaseFormController {

	private CatalogManager catalogManager;
	private CatalogTypeManager catalogTypeManager;

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
	public ModelAndView save(CatalogForm catalogForm, BindingResult errors, HttpServletRequest request) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/catalogList");
		}

		if (validator != null) { // validator is null during testing
			validator.validate(catalogForm, errors);

			if (null == catalogForm.getId()) {
				if (getCatalogManager().checkDuplicate(catalogForm.getCode(), null)) {
					errors.rejectValue("code", "catalog.duplicate", new Object[] { getText("catalog.code", request.getLocale()) }, "catalog.duplicate");
				}
			}

			if (errors.hasErrors() && request.getParameter("delete") == null) { // don't
																				// validate
																				// when
																				// deleting
				return new ModelAndView("catalog", "catalog", catalogForm).addObject("catalogTypeList", catalogTypeManager.getAllCatalogType());
			}
		}
		log.info(request.getRemoteUser() + " is saving Catalog := " + catalogForm);

		Locale locale = request.getLocale();

		if (request.getParameter("delete") != null) {
			// since code input is readonly, no value pass to form then we need
			// to query from db.
			// Catalog catalog = getCatalogManager().get(catalogForm.getId());
			getCatalogManager().remove(catalogForm.getId());
			saveMessage(request, getText("catalog.deleted", catalogForm.getCode(), locale));
			return new ModelAndView("redirect:/catalogList");
		} else {

			if (null == catalogForm.getId()) {
				// add
				catalogForm.setCreateDate(new Date());
				catalogForm.setCreateUser(request.getRemoteUser());

				if (null != catalogForm.getCatalogType()) {
					CatalogType catalogType = getCatalogTypeManager().findByCatalogTypeCode(catalogForm.getCatalogType().getCode());
					catalogForm.setCatalogType(catalogType);
				}
				
				Catalog catalog = new Catalog();
				PropertyUtils.copyProperties(catalog, catalogForm);

				// update img & thunbnail if exist
				if (StringUtils.isNotBlank(catalogForm.getFilename())) {
					
					//read to byte[]
					File tmpFile = new File(FileUtils.getTempDirectory(), catalogForm.getFilename());
					
					if (tmpFile.exists()) {
						InputStream in = new FileInputStream(tmpFile);
						catalog.setImg(FileUtils.readFileToByteArray(tmpFile));
					}
				}

				catalog = getCatalogManager().save(catalog, null);

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
					}
				}

				catalog.setUpdateDate(new Date());
				catalog.setUpdateUser(request.getRemoteUser());
				
				
				catalog = getCatalogManager().save(catalog, null);

				request.setAttribute("catalog", catalog);
				saveMessage(request, getText("catalog.saved", catalog.getCode(), locale));
				return new ModelAndView("redirect:/catalogList");
			}
		}
	}

	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Catalog catalog = new Catalog();
		if (!isFormSubmission(request)) {
			if (id != null) {
				catalog = getCatalogManager().get(Long.valueOf(id));
			}
		} else {
			catalog = getCatalogManager().get(Long.valueOf(id));
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("catalog", catalog);
		model.put("catalogTypeList", catalogTypeManager.getAllCatalogType());
		return new ModelAndView("catalog", model);
	}

	private boolean isFormSubmission(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}

}
