package compare;


import mjerez.jmicrobench.Registry;
import mjerez.jmicrobench.TimeProfiler;
import models.Estudent;
import models.Person;

import com.googlecode.objectify.ObjectifyService;
import com.jems.datastore.DsEntityBuilder;

public class ToEntity extends DatastoreApis {
	
	final static String tittle = "Object to Entity.";
	static final int loops = 100;

	

	@Override
	public void lightDsTest() {
		//TODO finish the test	
		TimeProfiler tp = Registry.getTimeProfiler(tittle);
		Estudent antony = new Estudent("Antony_", "LightDs school");
		
		tp.startCount("LightDs");
		/* Estudent antony = new Estudent(...); */
		new DsEntityBuilder(antony);
		tp.stopCount("LightDs");
	}

	@Override
	public void objectifyTest() {
		
		TimeProfiler tp = Registry.getTimeProfiler(tittle);
		ObjectifyService.register(Person.class);
		ObjectifyService.register(Estudent.class);	
		Estudent antony = new Estudent("Antony_", "Objectify school");
		
		tp.startCount("Objectify");				
		/* Estudent antony = new Estudent(...); */
		ObjectifyService.ofy().toEntity(antony);
		tp.stopCount("Objectify");
	}

	@Override
	public void twigTest() {
		// TODO Auto-generated method stub
		
	}

	

	

}
