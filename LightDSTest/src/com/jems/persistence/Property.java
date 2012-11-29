package com.jems.persistence;

import static java.lang.reflect.Modifier.isStatic;
import static java.lang.reflect.Modifier.isTransient;
import static java.lang.reflect.Modifier.isVolatile;

import java.lang.reflect.Field;

import com.jems.persistence.annotations.DsProperty;



@SuppressWarnings("rawtypes")
public class Property {
	
	
	
	private final Field field;
	private final PropertyAccessor accessor;
	private final PropertyTranslator translator;
	private final String propName;
	private final boolean indexed;
	
	public static final boolean isPersistent(Field field){
		boolean synt = field.isSynthetic();
		int mod = field.getModifiers();
		if(isStatic(mod)|| isVolatile(mod)||isTransient(mod)|| synt) return false;		
		DsProperty config = (DsProperty) field.getAnnotation(DsProperty.class);		
		if(config!=null)return config.persistent();
		
		return DsProperty.defaultPersistent;
	}

	public Property(Field field, PropertyAccessor accessor, PropertyTranslator translator){
		this.field = field;
		this.accessor = accessor;
		this.translator = translator;
		DsProperty config = field.getAnnotation(DsProperty.class);
		this.propName = (config!=null && !config.propName().equals(DsProperty.defaultPropname))
						? config.propName() : field.getName() ;
		this.indexed =(config!=null)? config.indexed() : DsProperty.defaultIndexed;		
	}
	
	
	public Field getField(){return field;}
	public PropertyAccessor getAccessor(){return accessor;}
	public PropertyTranslator getTranslator(){return translator;}
	public String getPropertyName(){return this.propName;}
	public boolean isIndexed(){return indexed;}


}
