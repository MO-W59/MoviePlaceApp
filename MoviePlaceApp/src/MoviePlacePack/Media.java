/**
 * 			This is the parent class of all media objects. It has various get and set methods along with two constructors
 * 		one for user inputs and one to read from a file, additionally it has a toString and toXMLString method
 * 		for printing media information*/

package MoviePlacePack;

//media class is parent of other classes such as EBook, MovieDVD, MusicCD
//media is made abstract so that a child class instance must be made
abstract public class Media {

	//attributes for parent class, inherited by children
	private int id;
	private String title;
	private int year;
	private boolean inStore;
	
	
	//constructor for media, is passed values by children classes
	public Media(int id, String title, int year, boolean inStore) {
		
		//if passed ID value is 0 or below throw an exception.
		if(id <= 0) {
			throw new IllegalArgumentException("ID values cannot be less than or equal to 0.");
		}
		
		//set variables as passed values
		this.id = id;
		this.title = title;
		this.year = year;
		this.inStore = inStore;
	}
	
	
	//constructor for media that parses information from a string
	public Media(String line) {
		
		//value to hold weather or not Media object is in the store or not
		String rentalState;
		
		//get id value from XML string in file set as inputID
		int inputId = Integer.parseInt(line.substring(line.indexOf("<id>") + 4, line.indexOf("</id>")));
		
		//if inputId is less than or equal to 0 throw an exception
		if(inputId <= 0) {
			throw new IllegalArgumentException("ID values cannot be less than or equal to 0.");
		}
		
		//set values as xml lines read from file
		id = Integer.parseInt(line.substring(line.indexOf("<id>") + 4, line.indexOf("</id>")));
		title = line.substring(line.indexOf("<title>") + 7, line.indexOf("</title>"));
		year = Integer.parseInt(line.substring(line.indexOf("<year>") + 6, line.indexOf("</year>")));
		rentalState = line.substring(line.indexOf("<inStore>") + 9, line.indexOf("</inStore>"));
		
		//if read rental state is true inStore boolean is set to true (ie. available to rent)
		if (rentalState.equalsIgnoreCase("true")) {
			inStore = true;
		}
		
		//if read rental state is false inStore boolean is set to false (ie. not available to rent)
		if (rentalState.equalsIgnoreCase("false")) {
			inStore = false;
		}
	}
	
	
	//method to calculated rental fee, EBook and MusicCD child classes override this method
	//if not overridden by a child class method this method will just return the flat 3.50 rental fee
	public double calculateRentalFee() {
		return 3.50;//flat rental fee
	}
	
	
	//method to set title
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	//method to set year
	public void setYear(int year) {
		this.year = year;
	}
	
	
	//method to set if the media is in the store
	public void setInStore(boolean inStore) {
		this.inStore = inStore;
	}
	
	
	//method to get id (NOTE) this id cannot be changed after instance creation, thus no setter method
	public int getId() {
		return id;
	}
	
	
	//method to get title
	public String getTitle() {
		return title;
	}
	
	
	//method to get year
	public int getYear() {
		return year;
	}
	
	
	//method to get if the media is in the store
	public boolean getInStore() {
		return inStore;
	}
	
	
	//method to convert media information to a string with xml tags.
	public String toXMLString() {
		return "<id>" + this.getId() + "</id><title>" + this.getTitle() +
			 "</title><year>" + this.getYear() + "</year><inStore>" + this.getInStore() + "</inStore>";
		}
	
	
	//method to convert media to a string to display to a user
	public String toString() {
		return "[id=" + getId() + ", title=" + getTitle() + ", year=" + getYear();
	}
}