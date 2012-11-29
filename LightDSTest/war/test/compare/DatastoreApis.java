package compare;



import mjerez.jmicrobench.JMicrobench;



public abstract class DatastoreApis implements JMicrobench {

	



	public abstract void lightDsTest();
	
	public abstract void objectifyTest();
	
	public abstract void twigTest();
	
	
	
	
	@Override
	public void runBench() {
		twigTest();		
		objectifyTest();
		lightDsTest();
		
	}
}
