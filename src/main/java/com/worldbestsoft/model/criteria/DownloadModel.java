package com.worldbestsoft.model.criteria;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;

import com.worldbestsoft.webapp.util.ReportUtil.ExportType;

public class DownloadModel {
	private ExportType type;
	private String jrxml;
	private String outputFileName;
	private Map<String, Object> params;
	private JRDataSource jrDataSource;
	private HttpServletResponse response;
	private String token;
	public ExportType getType() {
		return type;
	}
	public void setType(ExportType type) {
		this.type = type;
	}
	public String getJrxml() {
		return jrxml;
	}
	public void setJrxml(String jrxml) {
		this.jrxml = jrxml;
	}
	public String getOutputFileName() {
		return outputFileName;
	}
	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public JRDataSource getJrDataSource() {
		return jrDataSource;
	}
	public void setJrDataSource(JRDataSource jrDataSource) {
		this.jrDataSource = jrDataSource;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getFinalFilename() {
		if (ExportType.PDF.equals(type)) {
			return getOutputFileName() + ".pdf";
		} else if (ExportType.XLS.equals(type)) {
			return getOutputFileName() + ".xls";
		}
		return null;
	}
}
