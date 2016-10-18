import java.io.*;
import java.util.*;

public class RenItem implements Serializable {

	String name; 
	double sqft, ppSqft;
	double totalPrice;
	
	public RenItem(String name, double ppSqft, double sqft, double totalPrice) {
		this.name = name;
		this.sqft = sqft;
		this.ppSqft = ppSqft;
		this.totalPrice = totalPrice;
	}
	
}
