package com.jems.datastore.translators;

import java.util.ArrayList;
import java.util.Collection;

import com.google.appengine.api.datastore.Text;
import com.jems.persistence.PropertyContext;
import com.jems.persistence.PropertyTranslator;

public class TextTranslator implements PropertyTranslator<String, Text> {

	
	@Override
	public Text doTranslation(String fvalue, PropertyContext<?,?> propc) {
		return new Text(fvalue);
	}

	@Override
	public Collection<Text> doTranslation(Collection<String> fcollection, PropertyContext<?,?> propc) {
		ArrayList<Text> textL = new ArrayList<Text>(fcollection.size());
		for (String obj : fcollection) {
			textL.add(doTranslation(obj, propc));
		}
		return textL;
	}

	@Override
	public String undoTranslation(Text propValue, PropertyContext<?,?> pContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> undoTranslation(Collection<Text> propValues,
			PropertyContext<?,?> pContext) {
		// TODO Auto-generated method stub
		return null;
	}
	

	

	
}
