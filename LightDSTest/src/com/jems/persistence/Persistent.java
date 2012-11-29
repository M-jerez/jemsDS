package com.jems.persistence;


/**
 * All objects implementing this interface are capable to be persisted.
 * 
 * @author mjerez
 *
 * @param <K> the type of the Key of the Entity.
 * @param <E> the type of the Entity.
 */
public interface Persistent<K,E> {
	
	
	/** Gets the Entity associated to this object.
	 * @return the Entity or null if this object doesn't have an associated Entity yet.
	 */
	public E getEntity();
	
	/** Sets the Entity associated to this object.
	 * @param The Entity to be associated.
	 */
	public void setEntity(E ent);

	/** Gets the Kind of Entity.
	 * @return A string that represent the kin of  Entity.
	 */
	public String getKind();
	
	/** Gets the Key or ID of the Entity.
	 * @return the Key or null if the object doesn't have an associated Entity yet 
	 * or the associated Entity doesn't have an Key or ID assigned yet.
	 */
	public K getKey();

}
