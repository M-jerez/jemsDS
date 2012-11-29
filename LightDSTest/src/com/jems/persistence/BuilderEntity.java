package com.jems.persistence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public abstract class BuilderEntity<K,E> {
	
	
	
	
	private final Collection<? extends Persistent<K, E>> pojos;
	private HashMap<Persistent<K, E>, E> map;
	private KeyFactory<K, E> keyFactory;
	private boolean withKeys;
	
	@SuppressWarnings("unchecked")
	public BuilderEntity(Persistent<K, E> pojo){		
		this(Arrays.asList(pojo));
	}
	
	public BuilderEntity(Persistent<K, E>... pojos){
		this(Arrays.asList(pojos));
	}
	
	public BuilderEntity(Collection<? extends Persistent<K, E>> pojos){		
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
		
		List<Property> props;
		if(relationship)
			props = PersistentImage.getContract(pojo).getRelationships();
		else
			props = PersistentImage.getContract(pojo).getProperties();	
		
		List<PropertyContext<K, E>> propContexts = new ArrayList<PropertyContext<K, E>>(props.size());	
		
		for (Property prop : props) {
			PropertyTranslator pt = prop.getTranslator();
			PropertyAccessor pa = prop.getAccessor();
			Field f = prop.getField();			
			PropertyContext<K, E> propc = new PropertyContext<K, E>(this, prop);
			propContexts.add(propc);

			Object value = pa.get(f,pojo);

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
