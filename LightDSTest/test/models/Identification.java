package models;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.googlecode.objectify.annotation.Id;
import com.jems.datastore.DsPersistentAbstract;

@com.googlecode.objectify.annotation.Entity
@PersistenceCapable
@EmbeddedOnly	
public class Identification extends DsPersistentAbstract{
	
	static Random r = new Random();
	
	
	@Id  // Objectify 
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	public Long logicId;
	@Persistent
	public Date creationDate;
	
	public Identification(){
		logicId = r.nextLong();
		if(logicId<0) logicId = -1 * logicId;
		creationDate = Calendar.getInstance().getTime();		
	}

	
	
	
	@Override public String getKind(){return this.getClass().getSimpleName();}
	

}
