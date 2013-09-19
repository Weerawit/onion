package com.worldbestsoft.webapp.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.worldbestsoft.model.criteria.DownloadModel;
import com.worldbestsoft.util.DownloadToken;

@Component("reportUtil")
public class ReportUtil {
	
	private DownloadToken downloadToken;
	
	public DownloadToken getDownloadToken() {
		return downloadToken;
	}

	@Autowired
	public void setDownloadToken(DownloadToken downloadToken) {
		this.downloadToken = downloadToken;
	}

	public static enum ExportType {
		XLS("xls"),
		PDF("pdf");
		
		private final String code;
		
		private ExportType(String code) {
			this.code = code;
		}
		
		public String getCode() {
			return code;
		}
		
		public static ExportType fromString(String v) {
			if (null != v) {
				for (ExportType exportType : ExportType.values()) {
					if (exportType.code.equalsIgnoreCase(v)) {
						return exportType;
					}
				}
			}
			return null;
		}
	};

	public final String MEDIA_TYPE_EXCEL = "application/vnd.ms-excel";
	public final String MEDIA_TYPE_PDF = "application/pdf";

	public void download(DownloadModel downloadModel) throws JRException, IOException {
		
		InputStream reportStream = ReportUtil.class.getResourceAsStream(downloadModel.getJrxml());
//		InputStream reportStream = new FileInputStream(new File(downloadModel.getJrxml()));
		
		JasperDesign jd = JRXmlLoader.load(reportStream);
		
		JasperReport jr = JasperCompileManager.compileReport(jd);
		
		JasperPrint jp = null;
		Map<String, Object> params = new HashMap<String, Object>(downloadModel.getParams());
//		params.put("net.sf.jasperreports.extension.registry.factory.fonts", "net.sf.jasperreports.engine.fonts.SimpleFontExtensionsRegistryFactory");
//		params.put("net.sf.jasperreports.extension.simple.font.families.ireport", "irfont.xml");
		
		
		
		if (null != downloadModel.getJrDataSource()) {
			jp = JasperFillManager.fillReport(jr, params, downloadModel.getJrDataSource());
		} else {
			jp = JasperFillManager.fillReport(jr, params, new JREmptyDataSource());
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		downloadModel.getResponse().setHeader("Content-Disposition", "inline; filename=" + downloadModel.getFinalFilename());
		
		if (ExportType.XLS.equals(downloadModel.getType())) {
			exportXls(jp, baos);
			downloadModel.getResponse().setContentType(MEDIA_TYPE_EXCEL);
		} else if (ExportType.PDF.equals(downloadModel.getType())) {
			exportPdf(jp, baos);
			downloadModel.getResponse().setContentType(MEDIA_TYPE_PDF);
		}
		downloadModel.getResponse().setContentLength(baos.size());
		
		// Retrieve output stream
		ServletOutputStream outputStream = downloadModel.getResponse().getOutputStream();
		// Write to output stream
		baos.writeTo(outputStream);
		// Flush the stream
		outputStream.flush();
		
		downloadToken.remove(downloadModel.getToken());
	}
	
	public File generate(DownloadModel downloadModel) throws JRException, FileNotFoundException {
		InputStream reportStream = ReportUtil.class.getResourceAsStream(downloadModel.getJrxml()); 	
		
		JasperDesign jd = JRXmlLoader.load(reportStream);
		
		JasperReport jr = JasperCompileManager.compileReport(jd);
		
		JasperPrint jp = null;
		if (null != downloadModel.getJrDataSource()) {
			jp = JasperFillManager.fillReport(jr, downloadModel.getParams(), downloadModel.getJrDataSource());
		} else {
			jp = JasperFillManager.fillReport(jr, downloadModel.getParams());
		}
		
		File reportFile = new File(downloadModel.getFinalFilename());
		FileOutputStream baos = new FileOutputStream(reportFile);
		
		if (ExportType.XLS.equals(downloadModel.getType())) {
			exportXls(jp, baos);
		} else if (ExportType.PDF.equals(downloadModel.getType())) {
			exportPdf(jp, baos);
		}
		
		return reportFile;
		
	}
	

	public static void exportXls(JasperPrint jp, OutputStream baos) {
		// Create a JRXlsExporter instance
		JRXlsExporter exporter = new JRXlsExporter();
		 
		// Here we assign the parameters jp and baos to the exporter
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		 
		// Excel specific parameters
		exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		 
		try {
			exporter.exportReport();
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void exportPdf(JasperPrint jp, OutputStream baos) {
		// Create a JRXlsExporter instance
		JRPdfExporter exporter = new JRPdfExporter();
		 
		// Here we assign the parameters jp and baos to the exporter
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		 
		try {
			exporter.exportReport();
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, JRException {
		Map<String, Object> params = new HashMap<String, Object>();
//		File outpuptFile = generate(ExportType.PDF, "/reports/SaleReceipt.jrxml", "/tmp/abvc.pdf", params, new JREmptyDataSource());
//		System.out.println(outpuptFile);
	} 
}
