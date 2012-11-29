package com.jems.persistence.translators;

import java.util.ArrayList;
import java.util.Collection;

import com.jems.persistence.PropertyContext;
import com.jems.persistence.PropertyTranslator;

public class EnumTranslator implements PropertyTranslator<Enum<?>, String> {

	

	

	@Override
	public String doTranslation(Enum<?> fvalue, PropertyContext<?,?> propc) {	
		return fvalue.toString();
	}

	@Override
	public Collection<String> doTranslation(Collection<Enum<?>> fcollection, PropertyContext<?,?> propc) {
		ArrayList<String> enums = new ArrayList<String>(fcollection.size());
		for (Enum<?> obj : fcollection) {
			enums.add(doTranslation(obj, propc));
		}
		return enums;
	}

	@Override
	public Enum<?> undoTranslation(String propValue, PropertyContext<?,?> pContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Enum<?>> undoTranslation(Collection<String> propValues,
			PropertyContext<?,?> pContext) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
