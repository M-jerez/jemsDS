package general;



import mjerez.jmicrobench.JMicrobench;
import mjerez.jmicrobench.Registry;
import mjerez.jmicrobench.TimeProfiler;

import com.google.appengine.api.datastore.Entity;

/**
 *  SUBJECT: profile the EntityMap class, it's required to write the code inside EntityMap class
 *  to profile it.
 *  
 *  
 * @author mjerez
 *
 */
public class TestCreateEntity implements JMicrobench {
	
	


	@Override
	public void runBench() {
		
		TimeProfiler tp = Registry.getTimeProfiler("create entity"); 
		
		tp.startCount("create entity");
		Entity ent = new Entity("foo", 3);
		tp.stopCount("create entity");
		
		ent.setProperty("foo_prop", "foo value");
		
	}

}
