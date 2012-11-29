package compare;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mjerez.jmicrobench.BenchmarkRunner;
import mjerez.jmicrobench.JMicrobench;
import mjerez.jmicrobench.reports.FullWebReport;
import mjerez.jmicrobench.reports.ReportOptions;

import com.jems.persistence.accessors.AccessorsFactory;



public class EntryServlet extends HttpServlet {
	private static final long serialVersionUID = 0L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response)throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8");
		
		int profileLoops = 50;
		int warmupLoops = profileLoops*0;
		
		AccessorsFactory.enable = false;
		/* create the test */
		//JMicrobench benchmark = new TestObjectToEntity();
		JMicrobench benchmark = new PutEntityAsync();
		//JMicrobench benchmark = new TestPutEntity();
		
		
		/* create & run the benchmark */
		BenchmarkRunner bench = new BenchmarkRunner(warmupLoops, profileLoops,benchmark);
		BenchmarkRunner.setPrintPhase(false);	
		bench.run();			
		
		/* Report Configuration */
		boolean drawLoad = true, drawProfiling=true, drawCode= true ,  bars=true, smallSize= true;
		String path= "../../test/";
		ReportOptions options = new  ReportOptions(drawLoad, drawProfiling, drawCode, bars, smallSize, path);
		
		
		/* generate the report & write to file */	
		new FullWebReport(options,bench).writeFullWeb(response.getWriter());	
		response.getWriter().close();			
		

	}
	

}
