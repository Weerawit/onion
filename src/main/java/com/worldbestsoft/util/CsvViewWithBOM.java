package com.worldbestsoft.util;

import org.displaytag.export.CsvView;

public class CsvViewWithBOM extends CsvView {

	@Override
    protected String getDocumentStart() {
		// Write Byte Order Mark 
		return "\ufeff"; 
    }



}
