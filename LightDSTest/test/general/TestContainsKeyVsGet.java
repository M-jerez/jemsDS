package general;

import java.util.HashMap;

import mjerez.jmicrobench.JMicrobench;
import mjerez.jmicrobench.Registry;
import mjerez.jmicrobench.TimeProfiler;

public class TestContainsKeyVsGet implements JMicrobench {

	static final String title = "Hashmap.containsKey() vs Hashmap.get() ";
	
	@Override
	public void runBench() {

		HashMap<Object, Object> map = new HashMap<Object, Object>();
		TimeProfiler tp = Registry.getTimeProfiler(title); 
		String a = "fooA", b="fooB";
		
		
		tp.startCount("Put a");
		map.put(a, a);
		tp.stopCount("Put a");
		
		boolean foo = false;
		
		tp.startCount("containsKey a");
		/* containskey() existing object a, return true */
		foo = map.containsKey(a);
		tp.stopCount("containsKey a");
		
		tp.startCount("get a");
		/* get() existing object + comparison, return a */
		foo = (map.get(a)==null)? false: true;
		tp.stopCount("get a");
		
		tp.startCount("containsKey b");
		/* containskey() not existing object b, return false */
		foo = map.containsKey(b);
		tp.stopCount("containsKey b");

		
		tp.startCount("get b");
		/* get() not existing object + comparison, return null */
		foo = (map.get(b)==null)? false: true;
		tp.stopCount("get b");
		
		map.put(a, foo);
	}

}
