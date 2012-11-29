package com.jems.persistence.translators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.jems.persistence.BuilderEntity;
import com.jems.persistence.Persistent;
import com.jems.persistence.BuilderPojo;
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
	protected BuilderPojo<K, E> getPojoBuilder(PropertyContext<?, ?> pContext){
		return (BuilderPojo<K, E>) pContext.getPojoBuilder();
	}
	
	@SuppressWarnings("unchecked")
	protected BuilderEntity<K, E> getEntityBuilder(PropertyContext<?, ?> pContext){
		return (BuilderEntity<K, E>) pContext.getEntityBuilder();
	}
	



	
	
	

	
	
}
