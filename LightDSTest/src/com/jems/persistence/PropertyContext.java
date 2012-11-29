package com.jems.persistence;


public class PropertyContext<K,E> {
	
	private final BuilderEntity<K,E> entityBuilder;
	private final BuilderPojo<K,E> pojoBuilder;
	private final Property property;
	private Object propertyValue;
	private Object fieldValue;
	
	
	
	
	/** Default constructor of {@link PropertyContext}.
	 * @param entityBuilder
	 * @param property
	 */
	public PropertyContext(BuilderEntity<K, E> entityBuilder, Property property){
		this.entityBuilder = entityBuilder;
		this.property = property;
		this.pojoBuilder = null;
	}	
	
	/** Default constructor of {@link PropertyContext}.
	 * @param entityBuilder
	 * @param property
	 */
	public PropertyContext(BuilderPojo<K, E> pojoBuilder, Property property){
		this.entityBuilder = null;
		this.property = property;
		this.pojoBuilder = pojoBuilder;
	}	

	
	/**
	 * @return the entityBuilder
	 */
	public BuilderEntity<K, E> getEntityBuilder() {
		return entityBuilder;
	}

	/**
	 * @return the pojoBuilder
	 */
	public BuilderPojo<K, E> getPojoBuilder() {
		return pojoBuilder;
	}

	/**
	 * @return the propertyValue */
	public Object getPropertyValue() {
		return propertyValue;
	}

	/**
	 * @param propertyValue the propertyValue to set */
	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}

	/**
	 * @return the fieldValue */
	public Object getFieldValue() {
		return fieldValue;
	}

	/**
	 * @param fieldValue the fieldValue to set */
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}

	/**
	 * @return the property */
	public Property getProperty() {
		return property;
	}

	



}
