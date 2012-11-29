package general;



import mjerez.jmicrobench.JMicrobench;
import models.Estudent;

import com.jems.datastore.DsEntityBuilder;

/**
 *  SUBJECT: profile the EntityMap class, it's required to write the code inside EntityMap class
 *  to profile it.
 *  
 *  
 * @author mjerez
 *
 */
public class TestEntityProfiler implements JMicrobench {
	
	static final String tittle = "EntityMap Profiler";


	@Override
	public void runBench() {
		
		Estudent antony = new Estudent("Antony_", "LightDs school");
		new DsEntityBuilder(antony);
		
	}

}
