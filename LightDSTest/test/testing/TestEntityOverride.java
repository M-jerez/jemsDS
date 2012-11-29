package testing;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

/**
 * SUBJECT: To inspect the behavior of an Entity when it's updated with different properties as the original.
 * 
 * CONCLUSIONS: Each time an Entity is inserted in the DataStore it completely overrides the old entity and it's properties.
 * If the new entity has lees properties than the original these properties don't exist anymore.
 */
public class TestEntityOverride {
	

	
	public static void doTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String parameter = request.getParameter("operation");
		int operation = (parameter==null)? 0 : Integer.parseInt(parameter);
		DatastoreService dsService = DatastoreServiceFactory.getDatastoreService();
		Entity personManuel;
		switch (operation) {
		case 0:
			// create and entity and put it in the DataStore.
			personManuel = new Entity("person", 1);		
			personManuel.setProperty("name", "Manuel");
			personManuel.setProperty("age", 23);
			personManuel.setProperty("phone", "34+6624564" );	
			dsService.put(personManuel);
			try { 
				response.getWriter().print("Entidad creada:" + dsService.get(personManuel.getKey()).toString());
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}			
			break;
		case 1:
			// update the same entity with different number of properties.
			personManuel = new Entity("person", 1);		
			personManuel.setProperty("name", "Manuela");
			personManuel.setProperty("age", 45);
			dsService.put(personManuel);
			try { 
				response.getWriter().print("Entidad Actualizada:" + dsService.get(personManuel.getKey()).toString());
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			} 
			break;
		case 2:
			// update the same entity with different number of properties.
			personManuel = new Entity("person", 1);		
			personManuel.setProperty("email", "manu@gmail.com");
			dsService.put(personManuel);
			try { 
				response.getWriter().print("Entidad Actualizada:" + dsService.get(personManuel.getKey()).toString());
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			} 
			break;

		default:
			break;
		}		
	 }
}
