package com.jems.persistence;

import java.lang.reflect.Field;

public interface PropertyAccessor<S, V> {
	   public void set(Field f, S source, V value);
	   public V get(Field f, S source);
}	
