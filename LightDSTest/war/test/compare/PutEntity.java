package compare;



import mjerez.jmicrobench.Registry;
import mjerez.jmicrobench.TimeProfiler;
import models.Estudent;
import models.Person;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.code.twig.ObjectDatastore;
import com.google.code.twig.annotation.AnnotationObjectDatastore;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.jems.datastore.DsEntityBuilder;

public class PutEntity extends DatastoreApis{
	
	private static enum Select{jemsDs,objectify,jdo,twig};
	final static String tittle = "Put Entity into datastore";
	final static Select sel = Select.jemsDs;
	final static int loops = 10;
	

	@Override
	public void lightDsTest() {
		
		TimeProfiler tp = Registry.getTimeProfiler(tittle);
		
		tp.startCount("LightDs");
		DatastoreService dsService = DatastoreServiceFactory.getDatastoreService();	
		Estudent antony = new Estudent("Antony_", "LightDs school");		
		dsService.put(new DsEntityBuilder(antony).simpleBuild());	
		tp.stopCount("LightDs");
	}

	@Override
	public void objectifyTest() {
		
		TimeProfiler tp = Registry.getTimeProfiler(tittle);
		
		tp.startCount("Objectify");	
		ObjectifyService.register(Person.class);
		ObjectifyService.register(Estudent.class);	
		Objectify obj = ObjectifyService.ofy();			
		Estudent antony = new Estudent("Antony_", "Objectify university");
		obj.save().entity(antony).now();		
		tp.stopCount("Objectify");
	}

	@Override
	public void twigTest() {
		
		TimeProfiler tp = Registry.getTimeProfiler(tittle);
		
		tp.startCount("Twig");
		ObjectDatastore twigDs = new AnnotationObjectDatastore();			
		Estudent antony = new Estudent("Antony_", "Twig university");
		twigDs.store().instance(antony).now();
		tp.stopCount("Twig");

	}

	


	
}
