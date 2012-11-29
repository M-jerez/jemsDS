package com.jems.persistence.translators;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Hashtable;

import com.jems.persistence.Persistent;
import com.jems.persistence.PropertyTranslator;
import com.jems.persistence.annotations.DsProperty;

public class PropertyTranslatorFactory {
	private static final Hashtable<String, PropertyTranslator<?,?>> translators = new Hashtable<String, PropertyTranslator<?,?>>(40);
	
	public static<K,E> PropertyTranslator<?,?> getTranslator(Persistent<K,E> pojo,Field field) throws Exception{
		String key = makekey(pojo, field);
		if(translators.containsKey(key))
			return translators.get(key);
		PropertyTranslator<?,?> trans  = createTranslator(pojo, field);
		translators.put(key, trans);
		return trans;
	}	
	
	private static<K,E> String makekey(Persistent<K,E> pojo,Field field){
		return pojo.getClass().getName()+"."+field.getName();
	}
	
	private static<K,E> PropertyTranslator<?,?> createTranslator(Persistent<K,E> pojo,Field f){
		
		DsProperty dsprop = f.getAnnotation(DsProperty.class);
		if(dsprop==null || dsprop.translator().equals(PropertyTranslator.class)){
			if(Enum.class.isAssignableFrom(f.getType()))
				return new EnumTranslator();
			else if( Persistent.class.isAssignableFrom(f.getType()) || checkGenericType(f, Persistent.class))
				return new RelationshipTranslator<K,E>();
			else
				return new DefaultTranslator();				
		}else{
			try {
				return (PropertyTranslator<?,?>) dsprop.translator().newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}
	
	/** Gets the generic type of a {@link Field} if the field is a {@link Collection}. </br> 
	 * i.e: {@code ArrayList<Long>  ->} returns the {@link Long} class. {@code String ->} returns null. 
	 * @param field the field that contains the information about the generic type.
	 * @return the class of the generic type or null if the field doesn't represent a Collection.
	 * @see Field#getGenericType()
	 */
	private static Class<?> getGenericType(Field field){
		if(!Collection.class.isAssignableFrom(field.getType())) return null;
		ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
        return (Class<?>) stringListType.getActualTypeArguments()[0];		
	}
	
	
	
	private static boolean checkGenericType(Field f, Class<?> genTypeClass){
		Class<?> clazz = getGenericType(f);
		if(clazz==null) return false;
		return  genTypeClass.isAssignableFrom(clazz);
	}

}
