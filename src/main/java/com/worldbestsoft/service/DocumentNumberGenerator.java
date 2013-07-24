package com.worldbestsoft.service;


public interface DocumentNumberGenerator {

	public abstract Long nextDocumentNumber(Class klass) throws DocumentNumberGeneratorException;

}