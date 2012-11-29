package com.jems.datastore;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.jems.persistence.Persistent;

public interface DsPersistent extends Persistent<Key, Entity> {

}
