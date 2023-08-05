/**
 * 			This child class extends Media, like its parent class it has two constructors and a toString method and a
 * 		toXMLString method.*/

package MoviePlacePack;

//MovieDVD extends its parent Media class
public class MovieDVD extends Media{

	//private child attribute 
	private double size;
	
	
	//constructor that uses parent class method, adds own attribute
	public MovieDVD(int id, String title, int year, double size, boolean inStore) {
		super(id, title, year, inStore);
		this.size = size;
		
	}

	
	//constructor to parse information from a String, uses parent class constructor as well
	public MovieDVD(String line) {
		super(line);
		
		size = Double.parseDouble(line.substring(line.indexOf("<size>") + 6, line.indexOf("</size>")));
	}
	
	
	//method to get the size
	public double getSize() {
		return size;
	}

	
	//method to set size
	public void setSize(double size) {
		this.size = size;
	}
	
	
	//method to convert media information to a string in xml format, overrides parent method but calls it as well
	@Override
	public String toXMLString() {
		return "<MovieDVD>" + super.toXMLString() + "<size>" + size + "</size></MovieDvd>";
	}
	
	
	//method to convert media information to a string, overrides parent class, also calls parent method
	@Override
	public String toString() {
		return "MovieDVD " + super.toString() + ", size=" + size + "MB, in store=" + getInStore() + "]";
		}

}