package com.jems.persistence;


/**
 * Any object that must be persisted in the DataStore have to implement this interface. 
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
	
	/** Gets the Key "ID" of the Entity.
	 * @return the Key or null if the object doesn't have an associated Entity yet or 
	 * or the entity doesn't have an id assigned yet.
	 */
	public K getKey();
	
	

}
