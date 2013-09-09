package com.worldbestsoft.service;

import com.worldbestsoft.model.DocumentNumber;


public interface DocumentNumberGenerator {

	public abstract DocumentNumber newDocumentNumber();

	public abstract void nextDocumentNumber(Class klass,  Long internalNo, DocumentNumberFormatter formatter);

}