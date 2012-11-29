package models;


import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.datanucleus.annotations.Unowned;
import com.google.code.twig.annotation.Embedded;
import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Id;
import com.jems.datastore.DsPersistent;
import com.jems.datastore.translators.EmbeddedEntityTranslator;
import com.jems.persistence.annotations.DsProperty;

@com.googlecode.objectify.annotation.Entity
@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public class Tangible extends DsPersistent {

	
	@DsProperty(persistent=false)		// LightData
	@Id 								// Objectify 
	@com.google.code.twig.annotation.Id	// twig
	@PrimaryKey							//JDO
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY) //JDO
	Long id;
	
	@DsProperty(translator=EmbeddedEntityTranslator.class)// LightData
	@Embed//goes in the embedded class			// Objectify 
	@Embedded									// twig
	@Persistent									//JDO
	public Identification objectId;
	@Persistent
	public int aproxValueUSD;
	@Persistent
	@Unowned
	public Set<Person> owners;			
	
	
	public Tangible(String type, int aproxValueUSD){
		this.aproxValueUSD = aproxValueUSD;	
		owners = new HashSet<Person>();
		objectId = new Identification();
	}
	
	
	//---------------DatastorePersistent--------------------	
	@Override public String getKind(){return this.getClass().getSimpleName();}
	public Tangible(){}
}
