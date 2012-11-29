package com.jems.datastore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.google.appengine.api.datastore.AsyncDatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyRange;
import com.jems.persistence.KeyFactory;
import com.jems.persistence.Persistent;

public class DsKeyFactory extends KeyFactory<Key, Entity> {
	
	static final AsyncDatastoreService AsyncdsService = DatastoreServiceFactory.getAsyncDatastoreService();

	
	private HashMap<String, Future<KeyRange>> keyFutures;
	private HashMap<String, Iterator<Key>> keyIterators;
	private HashMap<Persistent<Key, Entity>, Key> pojos;



	@Override
	public void requestForKeys(Collection<? extends Persistent<Key, Entity>> pojos) {
		// TODO Auto-generated method stub
		this.pojos = new HashMap<Persistent<Key,Entity>, Key>(pojos.size());
		HashMap<String, Integer> keyCounter = new HashMap<String, Integer>(Math.min(pojos.size(),8));	
		for (Persistent<Key,Entity> pojo : pojos) {
			if(pojo.getEntity()!=null && pojo.getKey()!=null) continue;						
			Integer total = keyCounter.get(pojo.getKind());
			if(total==null)
				keyCounter.put(pojo.getKind(), 1); 
			else
				keyCounter.put(pojo.getKind(), ++total);
			
		}	
		keyFutures = new HashMap<String, Future<KeyRange>>(keyCounter.size());
		for (String kind : keyCounter.keySet()) {
			int num = keyCounter.get(kind);
			keyFutures.put(kind, AsyncdsService.allocateIds(kind, num));
		}	
		keyIterators = new HashMap<String, Iterator<Key>>(keyCounter.size());
	}

	@Override
	public Key getKey(Persistent<Key, Entity> pojo)throws IllegalArgumentException {
		if(pojo.getEntity()!=null && pojo.getKey()!=null) return pojo.getKey();
		
		Key k = pojos.get(pojo);
		if(k!=null) return k;

		Iterator<Key> keyIt = keyIterators.get(pojo.getKind());		
		if(keyIt==null){
			try {				
				keyIt = keyFutures.get(pojo.getKind()).get().iterator();
				keyIterators.put(pojo.getKind(), keyIt);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		k =  keyIt.next();
		pojos.put(pojo, k);
		return k;
	}

	
	
	
}
