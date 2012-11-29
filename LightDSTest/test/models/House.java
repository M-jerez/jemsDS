package models;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.googlecode.objectify.annotation.EntitySubclass;


@EntitySubclass
@PersistenceCapable
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME)
public class House extends Tangible {
	
	@Persistent
	public String Address;
	@Persistent
	public int roomsNumber;
	@Persistent
	public Set<Person> occupants;
	
	
	public House(){
		super("House", getValue());
		this.Address = getAddress();
		occupants = new HashSet<Person>();
		roomsNumber = new Random().nextInt(5)+1;
	}

	private static int getValue(){
		int x = new Random().nextInt(600000);
		if(x<0) x=-1*x;
		return 200000+x;
	}
	
	private static String getAddress(){
		String[] prefix = {"Street","Avenue","Boulevard","Road"};
		String[] avName = {"New Wark", "Manhattan", "Brooklyn","Queens","Bronx","Staten Island", "Jersey City"};
		Random r  = new Random();
		return prefix[r.nextInt(prefix.length)]+" "+avName[r.nextInt(avName.length)]+" #"+r.nextInt(100);
	}

	
}
