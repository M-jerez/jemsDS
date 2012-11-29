package com.jems.persistence.translators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.jems.persistence.EntityBuilder;
import com.jems.persistence.Persistent;
import com.jems.persistence.PojoBuilder;
import com.jems.persistence.PropertyContext;
import com.jems.persistence.PropertyTranslator;


public class RelationshipTranslator<K,E> implements PropertyTranslator<Persistent<K, E>, K> {

	@SuppressWarnings("unchecked")
	@Override
	public K doTranslation(Persistent<K, E> fieldValue,
			PropertyContext<?, ?> pContext) {
		ArrayList<K> keys = getEntityBuilder(pContext).add(Arrays.asList(fieldValue));
		return (keys.size()>0)?keys.get(0):null;
	}

	@Override
	public Collection<K> doTranslation(
			Collection<Persistent<K, E>> fieldValues,
			PropertyContext<?, ?> pContext) {
		return getEntityBuilder(pContext).add(fieldValues);
	}

	@Override
	public Persistent<K, E> undoTranslation(K propValue,
			PropertyContext<?, ?> pContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Persistent<K, E>> undoTranslation(
			Collection<K> propValues, PropertyContext<?, ?> pContext) {
		// TODO Auto-generated method stub
		return null;
	}


	@SuppressWarnings("unchecked")
	protected PojoBuilder<K, E> getPojoBuilder(PropertyContext<?, ?> pContext){
		return (PojoBuilder<K, E>) pContext.getPojoBuilder();
	}
	
	@SuppressWarnings("unchecked")
	protected EntityBuilder<K, E> getEntityBuilder(PropertyContext<?, ?> pContext){
		return (EntityBuilder<K, E>) pContext.getEntityBuilder();
	}
	



	
	
	

	
	
}
