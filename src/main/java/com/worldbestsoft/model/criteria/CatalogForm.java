package com.worldbestsoft.model.criteria;

import com.worldbestsoft.model.Catalog;

public class CatalogForm extends Catalog {

	private String filename;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
    public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("CatalogForm [filename=");
	    builder.append(filename);
	    builder.append("]");
	    return builder.toString();
    }
	
	
	
}
