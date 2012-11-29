package main;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import mjerez.jmicrobench.BenchmarkRunner;
import mjerez.jmicrobench.JMicrobench;
import mjerez.jmicrobench.reports.FullWebReport;
import mjerez.jmicrobench.reports.ReportOptions;



public class BenchServlet extends HttpServlet {
	private static final long serialVersionUID = 0L;
	
	int warmupLoops = -1;	
	int profileLoops = -1;
	String className;
	JMicrobench benchmark;
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8");
		
		setParameters(request);
		
		/* create & run the benchmark */
		BenchmarkRunner bench = new BenchmarkRunner(warmupLoops, profileLoops,benchmark);
		BenchmarkRunner.setPrintPhase(true);
		bench.run();			
		
		/* Report Configuration */
		boolean drawLoad = true, drawProfiling=true, drawCode= true ,  bars=true, smallSize= true;
		String path= "../../test/";
		ReportOptions options = new  ReportOptions(drawLoad, drawProfiling, drawCode, bars, smallSize, path);
		
		
		/* generate the report & write to file */	
		new FullWebReport(options,bench).writeFullWeb(response.getWriter());	
		response.getWriter().close();
	}
	

	private void setParameters(HttpServletRequest request){
		String[] params = request.getRequestURI().split("/");
		className = params[2];		
		warmupLoops = Integer.parseInt(params[3]);
		profileLoops = Integer.parseInt(params[4]);
		
		try {
			Class<?> benchClass = Class.forName(className);
			benchmark = (JMicrobench) benchClass.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
