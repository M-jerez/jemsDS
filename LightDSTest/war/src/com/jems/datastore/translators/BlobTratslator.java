package com.jems.datastore.translators;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.google.appengine.api.datastore.Blob;
import com.jems.persistence.PropertyContext;
import com.jems.persistence.PropertyTranslator;

public class BlobTratslator implements PropertyTranslator<Serializable, Blob> {

	
	@Override
	public Blob doTranslation(Serializable fieldValue, PropertyContext<?,?> propc) {
		return toBlob(fieldValue);
	}


	@Override
	public Collection<Blob> doTranslation(Collection<Serializable> fieldValues,
			PropertyContext<?,?> pContext) {
		ArrayList<Blob> blobL = new ArrayList<Blob>(fieldValues.size());
		for (Serializable obj : fieldValues) {
			blobL.add(doTranslation(obj, pContext));
		}
		return blobL;
	}


	@Override
	public Serializable undoTranslation(Blob propValue, PropertyContext<?,?> propc) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Collection<Serializable> undoTranslation(
			Collection<Blob> propValues, PropertyContext<?,?> pContext) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/** Transforms an {@link Serializable} object to a {@link Blob}.
	 * @param object The object to transform.
	 * @return the object as {@link Blob}.
	 */
	private static Blob toBlob(Serializable object){
		ByteArrayOutputStream bos ;
		ObjectOutputStream oos ;
		Blob blobValue;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos); 
			oos.writeObject(object);
			blobValue = new Blob( bos.toByteArray());
			oos.close();
			bos.close();	
			return blobValue;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}


	
	
}
