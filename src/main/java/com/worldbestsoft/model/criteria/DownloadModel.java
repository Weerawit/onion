package com.worldbestsoft.model.criteria;

import java.util.Collection;
import java.util.Iterator;
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
	@Override
    public String toString() {
	    final int maxLen = 10;
	    StringBuilder builder = new StringBuilder();
	    builder.append("DownloadModel [type=");
	    builder.append(type);
	    builder.append(", jrxml=");
	    builder.append(jrxml);
	    builder.append(", outputFileName=");
	    builder.append(outputFileName);
	    builder.append(", params=");
	    builder.append(params != null ? toString(params.entrySet(), maxLen) : null);
	    builder.append(", jrDataSource=");
	    builder.append(jrDataSource);
	    builder.append(", response=");
	    builder.append(response);
	    builder.append(", token=");
	    builder.append(token);
	    builder.append("]");
	    return builder.toString();
    }
	private String toString(Collection<?> collection, int maxLen) {
	    StringBuilder builder = new StringBuilder();
	    builder.append("[");
	    int i = 0;
	    for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
		    if (i > 0)
			    builder.append(", ");
		    builder.append(iterator.next());
	    }
	    builder.append("]");
	    return builder.toString();
    }
	
	
}
