package com.jems.persistence.translators;

import java.util.Collection;

import com.jems.persistence.PropertyContext;
import com.jems.persistence.PropertyTranslator;



public class DefaultTranslator implements PropertyTranslator<Object, Object> {

	@Override
	public Object doTranslation(Object fieldValue, PropertyContext<?, ?> pContext) {
		return fieldValue;
	}

	@Override
	public Collection<Object> doTranslation(Collection<Object> fieldValues, PropertyContext<?, ?> pContext) {
		return fieldValues;
	}

	@Override
	public Object undoTranslation(Object propValue, PropertyContext<?, ?> pContext) {
		return propValue;
	}

	@Override
	public Collection<Object> undoTranslation(Collection<Object> propValues, PropertyContext<?, ?> pContext) {
		return propValues;
	}

	

	

	
}
