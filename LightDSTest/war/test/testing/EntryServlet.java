package testing;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class EntryServlet extends HttpServlet {
	private static final long serialVersionUID = 0L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8");
		
		
		TestEntityOverride.doTest(request, response);
		
		
		
		
		

	}
	

}
