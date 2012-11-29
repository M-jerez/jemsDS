package models;

import java.util.Random;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.PersistenceCapable;

import com.googlecode.objectify.annotation.EntitySubclass;

@EntitySubclass
@PersistenceCapable
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME)
public class Car extends Tangible {
	
	public enum MotorType{ ELECTRIC, GAS, OIL}

	public String brand;
	public MotorType motorType;
	
	
	public Car() {
		super("Car", getValue());
		Random r = new Random();
		String[] brands = {"BMW","Chevrolet","Ford","Renault","Toyota","Honda"};
		this.brand = brands[r.nextInt(brands.length)];
		this.motorType = MotorType.values()[(r.nextInt(MotorType.values().length))];
	}
	
	
	private static int getValue(){
		int x = new Random().nextInt(60000);
		if(x<0) x=-1*x;
		return 5000+x;
	}
	

}
