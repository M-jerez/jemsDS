package testing;

import mjerez.jmicrobench.JMicrobench;
import mjerez.jmicrobench.Registry;
import mjerez.jmicrobench.TimeProfiler;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;


/**
 * SUBJECT: Test the performance of the DatastoreService.allocateIds() function, and to bear out the variance of the performance  between
 * DatastoreService.allocateIds("Person", 1) and DatastoreService.allocateIds("Person", 100000). 
 * 
 * CONCLUSIONS: the performance DatastoreService.allocateIds(keyName, quantity) doesn't depend on the number of id to get.
 * Example:   DatastoreService.allocateIds("Person", 1) and DatastoreService.allocateIds("Person", 100000) consumes the same CPU time.
 * The keyRange object Returned by allocateIds() consist of 2 Keys one with a start id number and other with a end id number,
 * all the keys between this numbers never will be used by the DataStore when generates new keys so all keys between the numbers can be 
 * used and never will be override when the DataStore generate a key automatically.
 */
public class TestAllocateIds implements JMicrobench {
	
	final static String tittle = "Performance test of DatastoreService.AllocateIds()";
	static final int idSize = 1000;


	@Override
	public void runBench() {
	
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		String 	s1 = "Multiple calls to Datastore for one single key.",
				s2 = "Single call to Datastore for multiple keys.";	
		
		TimeProfiler tp = Registry.getTimeProfiler(tittle);
		
		/* ---------- TEST 1 ------------ */		
		tp.startCount(s1);		
		for (int ii = 0; ii < idSize; ii++) 
			ds.allocateIds("test", 1);
		tp.stopCount(s1);				
		
		/* -------- TEST 2 -------------- */
		tp.startCount(s2);
			ds.allocateIds("test", idSize);
		tp.stopCount(s2);
	
	}	
	

}
