package com.jems.datastore.translators;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.google.appengine.api.datastore.ShortBlob;
import com.jems.persistence.PropertyContext;
import com.jems.persistence.PropertyTranslator;

public class ShortBlobTranslator implements PropertyTranslator<Serializable, ShortBlob> {

	

	@Override
	public ShortBlob doTranslation(Serializable fvalue, PropertyContext<?,?> propc) {
		return toShortBlob(fvalue);
	}

	@Override
	public Collection<ShortBlob> doTranslation(Collection<Serializable> fcollection, PropertyContext<?,?> propc) {
		ArrayList<ShortBlob> shortBlobL = new ArrayList<ShortBlob>(fcollection.size());
		for (Serializable obj : fcollection) {
			shortBlobL.add(doTranslation(obj, propc));
		}
		return shortBlobL;
	}
	
	@Override
	public Serializable undoTranslation(ShortBlob propValue,
			PropertyContext<?,?> pContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Serializable> undoTranslation(
			Collection<ShortBlob> propValues, PropertyContext<?,?> pContext) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/** Transforms an {@link Serializable} object to a {@link ShortBlob}.
	 * @param object The object to transform.
	 * @return the object as {@link ShortBlob}.
	 */
	private static ShortBlob toShortBlob(Object fvalue){
		ByteArrayOutputStream bos ;
		ObjectOutputStream oos ;
		ShortBlob blobValue;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos); 
			oos.writeObject(fvalue);
			blobValue = new ShortBlob( bos.toByteArray());
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
