package models;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.googlecode.objectify.annotation.EntitySubclass;

@EntitySubclass
@PersistenceCapable
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME)
public class Worker extends Person {

	@Persistent
	public String company;
	@Persistent
	public Person boss;
	
	public Worker(String name, String company){
		super(name);
		this.company = company+" "+company;
	}
	
	public Worker(){}
	
}
