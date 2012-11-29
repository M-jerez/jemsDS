package general;

import mjerez.jmicrobench.JMicrobench;
import mjerez.jmicrobench.Registry;
import mjerez.jmicrobench.TimeProfiler;
import models.Car;
import models.Estudent;
import models.House;
import models.Worker;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.jems.datastore.DsEntityBuilder;



public class TestObjectsToEntity implements JMicrobench{
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void runBench()  {		
		DatastoreService dsService = DatastoreServiceFactory.getDatastoreService();
		
		TimeProfiler tp = Registry.getTimeProfiler("objects to entity.");	
		tp.startCount("init");
		
		Estudent antony = new Estudent("Antony", "Phyton university");		
		Estudent peter = new Estudent("Peter", "Java university");		
		Worker mary = new Worker("Mary", "MegaSoft");		
		Worker harry = new Worker("Harry", "Megasoft");		
		House sharedHouse = new House();
		House marysHouse = new House();
		Car marysCar = new Car();	
		
		
		/* establish relationships */
		peter.friends.add(harry);		
		harry.boss = mary;		
		mary.friends.add(harry);
		mary.friends.add(antony);
		mary.possessions.add(marysHouse);	
		mary.possessions.add(marysCar);		
		marysHouse.owners.add(mary);
		marysCar.owners.add(mary);		
		sharedHouse.occupants.add(harry);		
		
		
		dsService.put(new DsEntityBuilder(
				peter, harry, mary, marysHouse, 
				marysCar, sharedHouse, antony).simpleBuild()
				);

		
		tp.stopCount("init");
	 }



	
	

	 
	
}
