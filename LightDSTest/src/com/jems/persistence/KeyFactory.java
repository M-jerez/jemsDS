package com.jems.persistence;

import java.util.Collection;



/**
 * This object provide new keys to {@linkplain Persistent} Objects created by
 * first time and which requires a key prior to be inserted in the database.
 * 
 * To support Asynchronous functionality each key must be obtained in two phases: 
 * In the first phase a request for keys is made for a group of {@linkplain Persistent} Objects, 
 * in this phase is where take place the interaction or request to the Database if it would 
 * be required.
 * In the second phase one of the obtained keys is provided at time for each object of the group.
 * 
 * This class must be implemented by each specific Database implementation.
 * 
 * @author mjerez
 *
 * @param <K> the type of the Key of the Entity.
 * @param <E> the type of the Entity.
 */
public abstract class KeyFactory<K,E> {	
	
	/** 
	 * @param pojos
	 */
	public abstract void requestForKeys(Collection<? extends Persistent<K, E>> pojos);
	public abstract K getKey(Persistent<K, E> pojo)throws IllegalArgumentException;	
}
