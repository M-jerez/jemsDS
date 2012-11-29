package testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mjerez.jmicrobench.JMicrobench;
import mjerez.jmicrobench.Registry;
import mjerez.jmicrobench.TimeProfiler;




/**
 * SUBJECT: To test the cost of create a list through {@link Arrays#asList(Object...)}.
 * 
 * CONCLUSION: the List returned by this method is a very light object, only contain the array an a variable with the size
 * so create it is inexpensive.
 */
public class TestArraysAsList implements JMicrobench {
	
	
	final static String tittle = "Arrays.aslist(...) creation overhead";
	
	
	Double[] test ={
			3.5,34.65,34.5,56.4,342.3,2.0,23442.234,2.3456,3455.454,3453.6,345.35,34.545,3456.76,323.23674,2362.672,26342.3,
			3.5,34.65,34.5,56.4,342.3,2.0,23442.234,2.3456,3455.454,3453.6,345.35,34.545,3456.76,323.23674,2362.672,26342.3,
			3.5,34.65,34.5,56.4,342.3,2.0,23442.234,2.3456,3455.454,3453.6,345.35,34.545,3456.76,323.23674,2362.672,26342.3,
			3.5,34.65,34.5,56.4,342.3,2.0,23442.234,2.3456,3455.454,3453.6,345.35,34.545,3456.76,323.23674,2362.672,26342.3,
			3.5,34.65,34.5,56.4,342.3,2.0,23442.234,2.3456,3455.454,3453.6,345.35,34.545,3456.76,323.23674,2362.672,26342.3,
			3.5,34.65,34.5,56.4,342.3,2.0,23442.234,2.3456,3455.454,3453.6,345.35,34.545,3456.76,323.23674,2362.672,26342.3
	};
	
	
	
	public void runBench(){	
		// ----------------------------------- TEST 1 TRAERSE ARRAY -----------------------------------		
		
		TimeProfiler tp = Registry.getTimeProfiler(tittle);
		
		List<Double> aux = new ArrayList<Double>();
		tp.startCount("Arrays.asList(...)");
		
		aux = Arrays.asList(test);
	
		tp.stopCount("Arrays.asList(...)");
		aux.isEmpty();
		
	}

	
	
	

}
