package models;

import java.util.Arrays;
import java.util.List;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.googlecode.objectify.annotation.EntitySubclass;

@EntitySubclass
@PersistenceCapable
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME)
public class Estudent extends Person {
	
	@Persistent
	public String collegeName;	
	@Persistent
	public List<String> courses;
	
	
	public Estudent(String name, String collegeName) {
		super(name);
		this.collegeName = name+" "+collegeName;
		this.courses = Arrays.asList(name+"'s Cousre 1", name+"'s Cousre 2");
	}
	
	protected Estudent(){}
 
}
