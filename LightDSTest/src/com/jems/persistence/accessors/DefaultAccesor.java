package com.jems.persistence.accessors;

import java.lang.reflect.Field;

import com.jems.persistence.PropertyAccessor;


public class DefaultAccesor implements PropertyAccessor<Object, Object> {

	@Override
	public void set(Field f, Object source, Object value) {
		if(!f.isAccessible())f.setAccessible(true);
		try {
			f.set(source, value);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Object get(Field f, Object source) {
		Object x = null;
		try {
			if(!f.isAccessible())f.setAccessible(true);
			x = f.get(source);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return x;
	}

	
	

}
