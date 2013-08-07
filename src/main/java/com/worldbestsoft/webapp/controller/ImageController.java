package com.worldbestsoft.webapp.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.worldbestsoft.Constants;
import com.worldbestsoft.model.Catalog;
import com.worldbestsoft.service.CatalogManager;

@Controller
@RequestMapping("/img*")
public class ImageController {
	private final Log log = LogFactory.getLog(getClass());
	
	private CatalogManager catalogManager;
	
	private File tempDir = FileUtils.getTempDirectory();
	
	private String noImageFile = "/image-not-found.jpg";
	
	public File getTempDir() {
		return tempDir;
	}

	public void setTempDir(File tempDir) {
		this.tempDir = tempDir;
	}
	
	public String getNoImageFile() {
		return noImageFile;
	}

	public void setNoImageFile(String noImageFile) {
		this.noImageFile = noImageFile;
	}

	public CatalogManager getCatalogManager() {
		return catalogManager;
	}

	@Autowired
	public void setCatalogManager(CatalogManager catalogManager) {
		this.catalogManager = catalogManager;
	}

	@RequestMapping(value = "/thumbnail/{id}", method = RequestMethod.GET)
	public void getThumbnail(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String type = request.getParameter("t");
		
		File originalFile = new File(tempDir, id);
		if (!originalFile.exists()) {
			//copy image not found to temp dir,
			//change id to image_not_found
			id = "image_not_found";
			originalFile = new File(tempDir, id);
			if (!originalFile.exists()) {
				//copy from resource
				OutputStream out = new FileOutputStream(originalFile);
				IOUtils.copyLarge(getClass().getResourceAsStream(getNoImageFile()), out);
				IOUtils.closeQuietly(out);
			}
		}
		File imgFile = null;
		
		if (Constants.THUMBNAIL_TYPE_LARGE.equals(type)) {
			imgFile = new File(tempDir, id + "_large");
			if (!imgFile.exists()) {
				BufferedImage thumbnail = Scalr.resize(ImageIO.read(originalFile), Scalr.Method.QUALITY, 400);
				ImageIO.write(thumbnail, "jpg", imgFile);
			}
		} else if (Constants.THUMBNAIL_TYPE_ORIGIAL.equals(type)) {
			imgFile = new File(tempDir, id);
		} else {
			imgFile = new File(tempDir, id + "_small");
			if (!imgFile.exists()) {
				BufferedImage thumbnail = Scalr.resize(ImageIO.read(originalFile), Scalr.Method.QUALITY, 300);
				ImageIO.write(thumbnail, "jpg", imgFile);
			}
		}
		
		response.setContentType("image/jpeg");

		BufferedImage bi = ImageIO.read(imgFile);
		OutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		out.close();
	}
	
	@RequestMapping(value = "/thumbnail/catalog/{id}", method = RequestMethod.GET)
	public void getCatalog(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String type = request.getParameter("t");
		
		File catalogDir = new File(tempDir, "catalog");
		if (!catalogDir.exists()) {
			catalogDir.mkdirs();
		}
		File originalFile = new File(catalogDir, id);
		if (!originalFile.exists()) {
			boolean found = false;
			try {
				Catalog catalog = catalogManager.get(Long.valueOf(id));
				if (null != catalog) {
					OutputStream out = new FileOutputStream(originalFile);
					IOUtils.write(catalog.getImg(), out);
					IOUtils.closeQuietly(out);
					found = true;
				} 
			} catch (Exception e) {
				//do nothing
			}
			
			if (!found) {
				id = "image_not_found";
				originalFile = new File(tempDir, id);
				if (!originalFile.exists()) {
					//copy from resource
					OutputStream out = new FileOutputStream(originalFile);
					IOUtils.copyLarge(getClass().getResourceAsStream(getNoImageFile()), out);
					IOUtils.closeQuietly(out);
				}
			}
		}
		
		File imgFile = null;
		
		if (Constants.THUMBNAIL_TYPE_LARGE.equals(type)) {
			imgFile = new File(catalogDir, id + "_large");
			if (!imgFile.exists()) {
				BufferedImage thumbnail = Scalr.resize(ImageIO.read(originalFile), Scalr.Method.QUALITY, 400);
				ImageIO.write(thumbnail, "jpg", imgFile);
			}
		} else if (Constants.THUMBNAIL_TYPE_ORIGIAL.equals(type)) {
			imgFile = new File(catalogDir, id);
		} else {
			imgFile = new File(catalogDir, id + "_small");
			if (!imgFile.exists()) {
				BufferedImage thumbnail = Scalr.resize(ImageIO.read(originalFile), Scalr.Method.QUALITY, 300);
				ImageIO.write(thumbnail, "jpg", imgFile);
			}
		}
		
		response.setContentType("image/jpeg");

		BufferedImage bi = ImageIO.read(imgFile);
		OutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		out.close();
	}
}
