/**
 * 			This child class extends Media, like its parent class it has two constructors and a toString method and a
 * 		toXMLString method.*/

package MoviePlacePack;

//import Calendar content
import java.util.Calendar;

//EBook extends Media to inherit parent class attributes
public class EBook extends Media{

	//child class private attribute
	private int numChapters;
	
	
	//constructor using parent class constructor, adds own attribute
	public EBook(int id, String title, int year, int numChapters, boolean inStore) {
		super(id, title, year, inStore);
		this.numChapters = numChapters;
	}

	
	//constructor that parses information from a String with xml tags, uses parent constructor as well
	public EBook(String line) {
		super(line);
		
		numChapters = Integer.parseInt(line.substring(line.indexOf("<numChapters>") + 13, line.indexOf("</numChapters>")));
	}
	
	
	//method to get number of chapters
	public int getNumChapters() {
		return numChapters;
	}

	
	//method to set number of chapters
	public void setNumChapters(int numChapters) {
		this.numChapters = numChapters;
	}

	
	//method to calculate ebook rental fee overrides parent class method
	@Override
	public double calculateRentalFee() {
		double fee = numChapters * .1;//set fee as number of chapters * .1
		
		
		//if current year is same as year published
		if (getYear() == Calendar.getInstance().get(Calendar.YEAR)) {
			fee++;//increase fee by 1
		}
		
		return fee;//return fee
	}
	
	
	//method to convert media information to a string with xml tags, overrides parent method, also calls parent method
	@Override
	public String toXMLString() {
		return "<EBook>" + super.toXMLString() + "<numChapters>" + numChapters + "</numChapters></EBook>";
	}
	
	
	//method to convert media information to a string, overrides parent method, also calls parent method.
	@Override
	public String toString() {
		return "EBook " + super.toString() + ", chapters=" + numChapters + ", in store=" + getInStore() + "]";
	}
}