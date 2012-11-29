package com.jems.persistence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public abstract class BuilderPojo<K,E> {
	
	
	
	private final HashMap<Persistent<K, E>, E> map;
	
	@SuppressWarnings("unchecked")
	public BuilderPojo(Persistent<K, E> pojo){
		this(Arrays.asList(pojo));
	}
	
	public BuilderPojo(Persistent<K, E>... pojos){
		this(Arrays.asList(pojos));
	}
	
	public BuilderPojo(Collection<Persistent<K, E>> pojos){		
		getKeyFactory().requestForKeys(pojos);
		map = new HashMap<Persistent<K, E>,E>((int) (pojos.size()*1.3));
		for (Persistent<K, E> pojo : pojos) {
			map.put(pojo, null);
			pojo.setEntity(toEntity(pojo));
			map.put(pojo, pojo.getEntity());
		}
	}
	
	public ArrayList<K> add(Collection<Persistent<K, E>> pojos){
		ArrayList<K> keys = new ArrayList<K>();
		for (Persistent<K, E> pojo : pojos) {
			if(!map.containsKey(pojo)){
				pojo.setEntity(toEntity(pojo));
				map.put(pojo, pojo.getEntity());
			}
			keys.add(pojo.getKey());
		}
		return keys;
	}
	
	
	public Collection<E> getEntities(){
		//TODO
		return null;
	}
	
	
	protected abstract KeyFactory<K, E> getKeyFactory();
	
	
	
	
	private E toEntity(Persistent<K,E> pojo){
		List<PropertyContext<K,E>> stdProps = getStdProperties(pojo);
		E entity = (pojo.getEntity()==null)? createEntity(getKeyFactory().getKey(pojo)):pojo.getEntity();
		setProperties(entity, stdProps);
		pojo.setEntity(entity);
		
		/* Important getRelProperties(pojo) method must be called after insert the pojo in the map to
		 * avoid infinite loop since getRelProperties(pojo) will cause recursion; */
		List<PropertyContext<K,E>> relProps = getRelProperties(pojo);
		setProperties(entity, relProps);
		return entity;
	}

	protected void setProperties(E entity, List<PropertyContext<K, E>> pContextList) {
		for (PropertyContext<K, E> pContext : pContextList) {
			setProperty(pContext, entity);
		}
	}
	
	protected List<PropertyContext<K, E>> getStdProperties(Persistent<K, E> pojo) {
		return getProps(pojo, false);		
	}

	
	protected List<PropertyContext<K, E>> getRelProperties(Persistent<K, E> pojo) {
		return getProps(pojo, true);		
	}

	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private  List<PropertyContext<K,E>> getProps(Persistent<K,E> pojo, boolean relationship){
		
		//TimeProfiler tp2 = Registry.getTimeProfiler("getProperties");
		/* get the entity properties */
		//tp2.startCount("getPropertylist");
		List<Property> props;
		if(relationship)
			props = PersistentImage.getContract(pojo).getRelationships();
		else
			props = PersistentImage.getContract(pojo).getProperties();	
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
	

	protected abstract void setProperty(PropertyContext<K,E> pContext, E entity );
	protected abstract E createEntity(K key);
	
}
