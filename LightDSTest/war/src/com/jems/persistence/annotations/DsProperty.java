/**
 * Contains the annotations used to configure how one object is translated to and entity.
 */
package com.jems.persistence.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jems.persistence.PropertyTranslator;


/**
 * Configures how a field of an object must be translated to a property of an entity.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DsProperty {
	
	public static boolean 	defaultIndexed 		= false;
	public static boolean 	defaultPersistent 	= true;
	public static String  	defaultPropname 	= "";
	

	
	/** Sets the name of the property. 
	 * By default the property name is the same as the field name in the object.
	 * @return the name of the property.
	 */
	String propName() default defaultPropname;
	
	/** Sets the field as persistent or not.
	 * @return true if the field must be persistent of false if not.
	 */
	boolean persistent() default defaultPersistent;
	
	/** Sets the field to create a DataStore indexed property or not.
	 * @return true if the property must be indexed or false if not.
	 */
	boolean indexed() default defaultIndexed;	
	
	 
	/** Sets the translator for this field.
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Class<? extends PropertyTranslator> translator() default PropertyTranslator.class;
}
