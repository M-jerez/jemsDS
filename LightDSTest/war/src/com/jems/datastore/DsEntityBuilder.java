package com.jems.datastore;

import java.util.Collection;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.jems.persistence.EntityBuilder;
import com.jems.persistence.KeyFactory;
import com.jems.persistence.Persistent;
import com.jems.persistence.PropertyContext;



public class DsEntityBuilder extends EntityBuilder<Key,Entity> {
	

	public DsEntityBuilder(Collection<? extends Persistent<Key, Entity>> pojos) {
		super(pojos);
	}

	public DsEntityBuilder(Persistent<Key, Entity>... pojos) {
		super(pojos);
	}

	public DsEntityBuilder(Persistent<Key, Entity> pojo) {
		super(pojo);
	}

	@Override
	protected void setProperty(PropertyContext<Key, Entity> pContext, Entity entity) {
		String propName = pContext.getProperty().getPropertyName();
		Object propValue = pContext.getPropertyValue();
		if (!pContext.getProperty().isIndexed())
			entity.setUnindexedProperty(propName, propValue);
		else
			entity.setProperty(propName, propValue);		
	}

	@Override
	protected Entity createEntity(Key key) {
		return new Entity(key);
	}
	
	@Override
	protected Entity createEntity(String kind) {
		return new Entity(kind);
	}

	@Override
	protected KeyFactory<Key, Entity> getKeyFactory() {
		return new DsKeyFactory();
	}

	

	

}
