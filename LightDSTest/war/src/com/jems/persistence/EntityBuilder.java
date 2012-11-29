package com.jems.persistence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public abstract class EntityBuilder<K,E> {
	
	
	
	
	private final Collection<? extends Persistent<K, E>> pojos;
	private HashMap<Persistent<K, E>, E> map;
	private KeyFactory<K, E> keyFactory;
	private boolean withKeys;
	
	@SuppressWarnings("unchecked")
	public EntityBuilder(Persistent<K, E> pojo){		
		this(Arrays.asList(pojo));
	}
	
	public EntityBuilder(Persistent<K, E>... pojos){
		this(Arrays.asList(pojos));
	}
	
	public EntityBuilder(Collection<? extends Persistent<K, E>> pojos){		
		this.pojos =  pojos;
	}
	
	
	public Collection<E> simpleBuild(){
		withKeys = false;
		this.map = new HashMap<Persistent<K, E>,E>((int) (pojos.size()*1.3));
		for (Persistent<K, E> pojo : pojos) {
			map.put(pojo, null);
			pojo.setEntity(toEntity(pojo));
			map.put(pojo, pojo.getEntity());
		}
		return map.values();
	}
	
	public Collection<E> build(){
		withKeys = true;
		this.keyFactory = getKeyFactory();
		this.keyFactory.requestForKeys(pojos);
		this.map = new HashMap<Persistent<K, E>,E>((int) (pojos.size()*1.3));
		for (Persistent<K, E> pojo : pojos) {
			map.put(pojo, null);
			pojo.setEntity(toEntityWihKey(pojo));
			map.put(pojo, pojo.getEntity());
		}
		return map.values();
	}
	
	public ArrayList<K> add(Collection<Persistent<K, E>> pojos){
		if(withKeys){			
			ArrayList<K> keys = new ArrayList<K>(pojos.size());
			for (Persistent<K, E> pojo : pojos) {
				if(!map.containsKey(pojo)){
					map.put(pojo, null);
					pojo.setEntity(toEntityWihKey(pojo));
					map.put(pojo, pojo.getEntity());
				}
				keys.add(keyFactory.getKey(pojo));
			}
			return keys;
		
		}	
		else{
			ArrayList<K> keys = new ArrayList<K>(pojos.size());
			for (Persistent<K, E> pojo : pojos) {
				if(!map.containsKey(pojo)){
					map.put(pojo, null);
					pojo.setEntity(toEntity(pojo));
					map.put(pojo, pojo.getEntity());
				}
				if(pojo.getKey()!=null)keys.add(pojo.getKey());
			}
			return keys;
		}
	}
	

	
	
	
	private E toEntity(Persistent<K,E> pojo){
		List<PropertyContext<K,E>> stdProps = getStdProperties(pojo);
		List<PropertyContext<K,E>> relProps = getRelProperties(pojo);
		E entity = (pojo.getEntity()==null)? createEntity(pojo.getKind()):pojo.getEntity();
		setProperties(entity, stdProps);
		setProperties(entity, relProps);
		pojo.setEntity(entity);
		return entity;
	}
	
	
	private E toEntityWihKey(Persistent<K,E> pojo){
		List<PropertyContext<K,E>> stdProps = getStdProperties(pojo);
		List<PropertyContext<K,E>> relProps = getRelProperties(pojo);
		E entity = (pojo.getEntity()==null)? createEntity(keyFactory.getKey(pojo)):pojo.getEntity();
		setProperties(entity, stdProps);
		setProperties(entity, relProps);
		pojo.setEntity(entity);
		return entity;
	}
	
	private List<PropertyContext<K, E>> getStdProperties(Persistent<K, E> pojo) {
		return getProps(pojo, false);		
	}

	
	private List<PropertyContext<K, E>> getRelProperties(Persistent<K, E> pojo) {
		return getProps(pojo, true);		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private  List<PropertyContext<K,E>> getProps(Persistent<K,E> pojo, boolean relationship){
		
		//TimeProfiler tp2 = Registry.getTimeProfiler("getProperties");
		/* get the entity properties */
		//tp2.startCount("getPropertylist");
		List<Property> props;
		if(relationship)
			props = PersistentContract.getContract(pojo).getRelationshipProperties();
		else
			props = PersistentContract.getContract(pojo).getStandardProperties();	
		//tp2.stopCount("getPropertylist");
		
		//tp2.startCount("propcontexInit");
		List<PropertyContext<K, E>> propContexts = new ArrayList<PropertyContext<K, E>>(props.size());	
		//tp2.stopCount("propcontexInit");
		
		for (Property prop : props) {
			//tp2.startCount("createPropContext");
			/* create the property context */
			PropertyTranslator pt = prop.getTranslator();
			PropertyAccessor pa = prop.getAccessor();
			Field f = prop.getField();			
			PropertyContext<K, E> propc = new PropertyContext<K, E>(this, prop);
			propContexts.add(propc);
			//tp2.stopCount("createPropContext");

			//tp2.startCount("getFieldValue");
			/* get field value */
			Object value = pa.get(f,pojo);
			//tp2.stopCount("getFieldValue");

			//tp2.startCount("setPropValue");
			/* set the property value */
			if(value==null){
				propc.setPropertyValue(value);
				continue;
			}			
			if(value instanceof Collection<?>){
				Collection<?> c = (Collection<?>) value;
				value = pt.doTranslation(c , propc);
			}else{
				value = pt.doTranslation(value, propc);
			}			
			propc.setPropertyValue(value);
			//tp2.stopCount("setPropValue");
		}			
		return propContexts;		
	}
	
	private void setProperties(E entity, List<PropertyContext<K, E>> pContextList) {
		for (PropertyContext<K, E> pContext : pContextList) {
			setProperty(pContext, entity);
		}
	}
	

	protected abstract void setProperty(PropertyContext<K,E> pContext, E entity );
	protected abstract E createEntity(K key);
	protected abstract E createEntity(String kind);
	protected abstract KeyFactory<K, E> getKeyFactory();
	
}
