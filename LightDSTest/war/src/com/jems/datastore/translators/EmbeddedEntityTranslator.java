package com.jems.datastore.translators;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.jems.persistence.Persistent;
import com.jems.persistence.PersistentContract;
import com.jems.persistence.Property;
import com.jems.persistence.PropertyContext;
import com.jems.persistence.PropertyTranslator;

public class EmbeddedEntityTranslator implements PropertyTranslator<Persistent<Entity,Key>,EmbeddedEntity> {

	@Override
	public EmbeddedEntity doTranslation(Persistent<Entity, Key> fieldValue,
			PropertyContext<?,?> pContext) {
		return toEmbeddedEntity(fieldValue, pContext);
	}

	@Override
	public Collection<EmbeddedEntity> doTranslation(
			Collection<Persistent<Entity, Key>> fieldValues,
			PropertyContext<?,?> pContext) {
		ArrayList<EmbeddedEntity> entities = new ArrayList<EmbeddedEntity>(fieldValues.size());
		for (Persistent<Entity, Key> pojo : fieldValues) {
			entities.add(doTranslation(pojo, pContext));
		}
		return null;
	}

	@Override
	public Persistent<Entity, Key> undoTranslation(EmbeddedEntity propValue,
			PropertyContext<?,?> pContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Persistent<Entity, Key>> undoTranslation(
			Collection<EmbeddedEntity> propValues, PropertyContext<?,?> pContext) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static EmbeddedEntity toEmbeddedEntity(Persistent<Entity, Key> pojo, PropertyContext pContext){
		
		EmbeddedEntity entity = new EmbeddedEntity();	
		
		List<Property> props = PersistentContract.getContract(pojo).getStandardProperties();
		for (Property prop : props) {			
			Field f = prop.getField();			
			Object value = prop.getAccessor().get(f,pojo);
			if(value==null){
				 entity.setProperty(prop.getPropertyName(), value);
				continue;
			}				
			PropertyTranslator pt = prop.getTranslator();
			if(value instanceof Collection<?>){
				Collection<?> c = (Collection<?>) value;
				value = pt.doTranslation(c, pContext);
			}else{
				value = pt.doTranslation(value, pContext);
			}
			entity.setProperty(prop.getPropertyName(), value);
		}			
		return entity;
	}

	
	
}
