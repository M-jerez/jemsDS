package com.jems.persistence;

import java.util.Collection;


public interface KeyFactory<K,E> {
	
	
	
	public void requestForKeys(Collection<? extends Persistent<K, E>> pojos);
	public K getKey(Persistent<K, E> pojo)throws IllegalArgumentException;	
}
