package com.jems.persistence;

import java.util.Collection;


public interface PropertyTranslator<F, P> {	
	
	public abstract P doTranslation(F fieldValue, PropertyContext<?,?> pContext);
	public abstract Collection<P> doTranslation(Collection<F> fieldValues, PropertyContext<?,?> pContext);
	
	public abstract F undoTranslation(P propValue, PropertyContext<?,?> pContext);
	public abstract Collection<F> undoTranslation(Collection<P> propValues, PropertyContext<?,?> pContext);

}
