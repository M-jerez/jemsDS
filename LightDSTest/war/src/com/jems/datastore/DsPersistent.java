package com.jems.datastore;


import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.jems.persistence.Persistent;
import com.jems.persistence.annotations.DsProperty;

public abstract class DsPersistent implements Persistent<Key, Entity>{	
	
	
	@DsProperty(persistent=false) Entity entity;	
	@Override public Entity getEntity() {return entity;}
	@Override public void setEntity(Entity ent) { entity = ent;}
	@Override public Key getKey(){
		if(getEntity()==null) return null;
		else if(!getEntity().getKey().isComplete()) return null;
		else return getEntity().getKey();
	}
	

}
