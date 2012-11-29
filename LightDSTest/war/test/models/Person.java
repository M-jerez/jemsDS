package models;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.datanucleus.annotations.Unowned;
import com.google.code.twig.annotation.Index;
import com.google.code.twig.annotation.Type;
import com.googlecode.objectify.annotation.AlsoLoad;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Serialize;
import com.googlecode.objectify.annotation.Unindex;
import com.jems.datastore.DsPersistent;
import com.jems.datastore.translators.BlobTratslator;
import com.jems.datastore.translators.TextTranslator;
import com.jems.persistence.annotations.DsProperty;


/**
 *
 */ 
@com.googlecode.objectify.annotation.Entity(name="obj_Person")
@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
@com.googlecode.objectify.annotation.Index
public class Person extends DsPersistent {
	
	public static int idSimulator = 1;
	
	/*--- Properties ---*/
	
	@DsProperty(propName="firstName") 	// LightData
	@AlsoLoad("firstName")				// Objectify 
	// no similar						// twig
	@Column(name="firstName")@Persistent //JDO
	public String name;
	
	@Persistent
	public Date birthDay;
	
	@DsProperty(persistent=false)			// LightData
	@Id 								// Objectify 
	@com.google.code.twig.annotation.Id // twig
	@PrimaryKey							//JDO
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY) //JDO
	Long id;
	
	/*--- Relationships ---*/	
	
	@DsProperty(indexed=false)			// LightData
	@Unindex							// Objectify
	@Index(false)						// twig
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value="true") //JDO
	@Unowned
	@NotPersistent
	public Set<Person> friends;		
	
	@DsProperty(indexed=false)			// LightData
	@Unindex							// Objectify
	@Index(false)						// twig
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value="true") //JDO
	@Unowned
	@NotPersistent
	public List<Tangible> possessions;	
	
	
	
	//Transformations. 
	@DsProperty (translator=BlobTratslator.class)// LightData
	@Serialize@Unindex							// Objectify
	@Type(Blob.class)							// twig
	@Persistent(serialized = "true")			//JDO
	public URL personalWeb; 
	
	@DsProperty (translator=TextTranslator.class)	// LightData
	@Serialize@Unindex					 		// Objectify
	@Type(Text.class)							// twig
	@Persistent(serialized = "true")			// JDO
	public String personalDescription; 
	
	
	
	
	public Person(String name){
		friends = new HashSet<Person>();
		possessions = new ArrayList<Tangible>();
		this.name = name;		
		birthDay = getBirthDay();
		personalWeb = getPersonalWeb(this.name);
		personalDescription = " name : "+name+"\n birthDay:"+birthDay.toString()+"\n personalWeb:"+ personalWeb.toString();
	}
	
	
	private static Date getBirthDay(){		
		Random r = new Random();
		int year = 1970+r.nextInt(34);
		int month = 1+r.nextInt(12);
		int day = 1+r.nextInt(30);
		Calendar c = Calendar.getInstance();
		c.set(year, month, day);
		return c.getTime();
	}
	
	public static URL getPersonalWeb(String name){
		URL url = null;
		try {
			String dir = "http://www."+name+".me";
			url = new URL(dir);
		} catch (MalformedURLException e) {
		}
		return url;
	}
	
	protected Person(){}
	
	
	//---------------DatastorePersistent--------------------
	@Override public String getKind(){return this.getClass().getSimpleName()+"_lighDS";}
	


}
