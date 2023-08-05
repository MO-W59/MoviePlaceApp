/**
 * 			This child class extends Media, like its parent class it has two constructors and a toString method and a
 * 		toXMLString method.*/

package MoviePlacePack;

//import Calendar content to use
import java.util.Calendar;


//MusicCD extends Media its parent class
public class MusicCD extends Media{

	//private child attribute
	private int length;
	
	
	//constructor uses parent constructor, then adds child attribute
	public MusicCD(int id, String title, int year, int length, boolean inStore) {
		super(id, title, year, inStore);
		this.length = length;
	}

	
	//constructor that parses informations from a string line, (uses parent class constructor as well)
	public MusicCD(String line) {
		super(line);
		
		length = Integer.parseInt(line.substring(line.indexOf("<length>") + 8, line.indexOf("</length>")));
	}
	

	//method to set length
	public void setLength(int length) {
		this.length = length;
	}
	
	
	//method to get length
	public int getLength() {
		return length;
	}
	
	
	//method to calculate rental fee, overrides parent method
	@Override
	public double calculateRentalFee(){
		double fee = length * 0.02;//set fee as length * .02
		
		//if year published is same as current year
		if (getYear() == Calendar.getInstance().get(Calendar.YEAR)) {
			fee++;//increase fee by 1
		}
		
		return fee;
	}
	
	
	//method to convert media information to a string with xml tags, overrides parent method, also calls parent method
	@Override
	public String toXMLString() {
		return "<MusicCD>" + super.toXMLString() + "<length>" + length + "</length></MusicCD>";
	}
	
	
	//method to print string to user, overrides parent method, calls parent method as well.
	@Override
	public String toString() {
		return "MusicCD " + super.toString() + ", length=" + length + "min, in store=" + getInStore() + "]";
	}
}