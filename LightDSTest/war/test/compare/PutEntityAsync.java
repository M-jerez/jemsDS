package compare;

import mjerez.jmicrobench.Registry;
import mjerez.jmicrobench.TimeProfiler;
import models.Estudent;
import models.Person;

import com.google.appengine.api.datastore.AsyncDatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.code.twig.ObjectDatastore;
import com.google.code.twig.annotation.AnnotationObjectDatastore;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.jems.datastore.DsEntityBuilder;

public class PutEntityAsync extends DatastoreApis{
	
	private static enum Select{jemsDs,objectify,jdo,twig};
	final static String tittle = "Put Entity into datastore - Async";
	final static Select sel = Select.jemsDs;
	final static int loops = 10;
	

	@Override
	public void lightDsTest() {
		
		System.err.println("lightDS");
		TimeProfiler tp = Registry.getTimeProfiler(tittle);
		
		tp.startCount("LightDs");
		AsyncDatastoreService dsService = DatastoreServiceFactory.getAsyncDatastoreService();	
		Estudent antony = new Estudent("Antony_", "LightDs school");		
		dsService.put(new DsEntityBuilder(antony).simpleBuild());	
		tp.stopCount("LightDs");
	}

	@Override
	public void objectifyTest() {
		System.err.println("objectify");
		
		TimeProfiler tp = Registry.getTimeProfiler(tittle);
		
		tp.startCount("Objectify");	
		ObjectifyService.register(Person.class);
		ObjectifyService.register(Estudent.class);	
		Objectify obj = ObjectifyService.ofy();			
		Estudent antony = new Estudent("Antony_", "Objectify university");
		obj.save().entity(antony);		
		tp.stopCount("Objectify");
	}

	@Override
	public void twigTest() {
		System.err.println("twig");
		
		TimeProfiler tp = Registry.getTimeProfiler(tittle);
		
		tp.startCount("Twig");
		ObjectDatastore twigDs = new AnnotationObjectDatastore();			
		Estudent antony = new Estudent("Antony_", "Twig university");
		twigDs.store().instance(antony).later();
		tp.stopCount("Twig");

	}

	


	
}
