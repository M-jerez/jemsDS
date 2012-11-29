package com.jems.persistence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;

import com.jems.persistence.accessors.AccessorsFactory;
import com.jems.persistence.translators.PropertyTranslatorFactory;
import com.jems.persistence.translators.RelationshipTranslator;



public class PersistentContract<K,E> {
	
	
	@SuppressWarnings("rawtypes")
	private final static Hashtable<String,PersistentContract> contracts = 
			new Hashtable<String, PersistentContract>();
	
	
	private final ArrayList<Property> standardProperties;
	private final ArrayList<Property> relationshipProperties;
	private final String kind;
	private final Class<?> klazz;
	
	
	public ArrayList<Property> getStandardProperties(){return standardProperties;}


	public ArrayList<Property> getRelationshipProperties(){return relationshipProperties;}	

	
	public String getKind(){return kind;}

	
	public Class<?> getClazz(){return klazz;}



	
	@SuppressWarnings("unchecked")
	public static<K,E> PersistentContract<K,E> getContract(Persistent<K,E> pojo){
		String key = pojo.getClass().getName();
		if(contracts.containsKey(key))
			return (PersistentContract<K, E>) contracts.get(key);
		
		PersistentContract<K,E> contract = new PersistentContract<K,E>(pojo);
		contracts.put(key, contract);
		return contract;
		
	}
	
	
	private PersistentContract(Persistent<K,E> pojo){
		this.klazz = pojo.getClass();
		this.kind = pojo.getKind();
		this.standardProperties = new ArrayList<Property>();
		this.relationshipProperties = new ArrayList<Property>();
		setProperties(pojo.getClass(),pojo, standardProperties, relationshipProperties);
	}
	
	
	
	/** Gets the {@link PropertyImpl}  of a {@link Class} including all inherited fields.
	 * @param klazz the class to be inspected.
	 * @return all the fields of the class.
	 */
	
	private void setProperties(Class<?> klazz, Persistent<K, E> pojo, ArrayList<Property> stdProps,  ArrayList<Property> relProps){
 
		Field[] fields = klazz.getDeclaredFields();
        for (Field field : fields) {
        	if(!Property.isPersistent(field)) continue;         	
			try {
				PropertyAccessor<?,?> a = AccessorsFactory.getAccessor(klazz, field);
				PropertyTranslator<?,?> t = PropertyTranslatorFactory.getTranslator(pojo, field);
				if(t instanceof RelationshipTranslator)
					relProps.add(new Property(field,a, t));
				else
					stdProps.add(new Property(field,a, t));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}        	
		}
        if (klazz.getSuperclass() != null) {
            setProperties(klazz.getSuperclass(),pojo,stdProps, relProps);
        }
    }
	

}
