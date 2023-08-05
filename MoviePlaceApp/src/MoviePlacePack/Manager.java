/**
 * 			This class serves as a manager of Media objects and has the ability to scan a directory for files, and then
 * 		read those files adding those media objects contained within to a list of media objects. It also can create
 * 		and update files, as well as search for media with a specific title, and it can search for a Media ID and then
 * 		update its rental status.*/

package MoviePlacePack;

//import needed content
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;


//Start of Manager Class
public class Manager {

	//private ArrayList attribute, holds a list of Media objects
	private ArrayList<Media> mediaList; 
	
	
	//Constructor for Manager class, mediaList is empty after creation.
	public Manager() {
		
		mediaList = new ArrayList<Media>();
	}
	
	
	//method to load a media object from a file and add it to the Managers mediaList
	public boolean loadMediaFile(String directory) throws IOException {
		
		File directoryPath = new File(directory);//take passed directory and make file object
		
		File fileList[] = directoryPath.listFiles();//make a file array of all the files in the directory
		
		boolean validFileWasFound = false;//set valid file to false, if a valid file is found it will change to true
		
		//variables to fill by below try/catch
		Media media = null;//media object
		String line = null;//line of text in file
		Scanner scan = null;//scanner
		
		
		//try this
		try {
			//for each file in the list
			for (File file : fileList) {
			
				//check if it has MusicCD, MovieDVD or EBook in its title.
				if (file.getName().contains("MusicCD") || file.getName().contains("MovieDVD")
						|| file.getName().contains("EBook")) {
					
					validFileWasFound = true;//if one of the above named files was found set validFileWasFound to true
				
					scan = new Scanner(file);//open file in file list
					line = scan.nextLine();//scan the line in file
				
					if (file.getName().contains("MusicCD")) {//if MusicCD file make MusicCD object
						media = new MusicCD(line);//read line and set as object variables
					}
				
					if (file.getName().contains("MovieDVD")) {//if MovieDVD file make MovieDVD object
						media = new MovieDVD(line);//read line and set as object variables
					}
				
					if (file.getName().contains("EBook")) {//if EBook file make EBook object
						media = new EBook(line);//read line and set as object variables
					}
					
					addMediaToList(media);//add media to manager media list via addMediaToList method
				}
			}
			
			
			return validFileWasFound;//after going through file list return boolean validFileWasFound
			
			//if an error occurs throw exception
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("An error occured while scanning files.");
		}
	}
	
	
	//method to create a file which holds information of a media object
	public void createMediaFile(String directory, Media mediaToFile) throws Exception {
		
		PrintWriter out = null;//create PrintWriter object
		
		//try this
		try {
			//to make file at the directory + class name + id value
			String filename = directory + "/" + mediaToFile.getClass().getSimpleName() + "-" +
					mediaToFile.getId() + ".txt";
			
			out = new PrintWriter(new FileWriter(filename));//open file
			
			out.println(mediaToFile.toXMLString());//print Media object xml tag information
			
		//if an error occurs
		} catch (Exception e) {
			//throw this
			throw new Exception("ERROR: A problem occured while trying to make/update the file.");
			
			//finally
		} finally {
			
			out.flush();//ensure file is flushed
			out.close();//ensure file is closed
		}
	}
	
	
	//method to add a media object to the list of media
	public void addMediaToList(Media media) {
		mediaList.add(media);//take passed media object and add it to the mediaList
	}
	
	
	//method to create a list of Media objects with the targeted title and return that list
	public ArrayList<Media> searchTitle(String targetTitle) {
		
		//create a new Media ArrayList to populate with media
		ArrayList<Media> generatedList = new ArrayList<Media>();
		
		//for each Media object in the media list
		for (int count = 0; count < mediaList.size(); count++) {
			
			//if its title matches the passed target title
			if (mediaList.get(count).getTitle().equalsIgnoreCase(targetTitle)) {
				
				generatedList.add(mediaList.get(count));//add that object to the generatedList
			}
		}
		
		return generatedList;
	}
	
	
	//method to set targeted media object to the rented status and return the rental fee
	public double rentMedia(int targetId, String directory) throws Exception {
		
		Media targetMedia = null;//create empty media object
		
		//For each Media object in the list check its ID value
		for (int count = 0; count < mediaList.size(); count++) {
			
			//if the ID matches the targetId 
			if (mediaList.get(count).getId() == targetId) {
				
				//updated that objects information to not in the store
				mediaList.get(count).setInStore(false);
				
				//set targetMedia as the media object at that point in the list
				targetMedia = mediaList.get(count);
				
				//make a new media file for that targetMedia, this will overwrite the old file
				createMediaFile(directory, targetMedia);
			}
		}
		
		//return the targeted media object's rental fee via the object's calculateRentalFee method
		return targetMedia.calculateRentalFee();
	}
}