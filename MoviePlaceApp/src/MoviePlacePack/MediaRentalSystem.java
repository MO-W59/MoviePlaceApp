/**
 * 			This program creates an GUI for a user to interact with the Manager class and media objects. Depending
 * 		on user inputs the GUI will display a message if input actions are successful or if a certain title
 * 		or id was not found. 
 * 
 * 			-->>(NOTE)<<-- the standard directory is set as this package's "src\\NitchGregory_Project\\Files"
 * 		folder, the user can select a directory to load objects from but the program will always save files to the
 * 		standard directory!!!*/

package MoviePlacePack;

//import needed content
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;

//Start of MediaRentalSystem class
public class MediaRentalSystem extends JPanel implements ActionListener {

	private static Manager manager = new Manager();//make a new manager from the Manager class
	private static JFrame window;//JFrame to hold buttons etc
	private static String loadMedia = "Load Media Objects";//label for load button
	private static String findMedia = "Find Media Object";//label for finding media based on title
	private static String rentMedia = "Rent Media Object";//label for renting media based on id
	private static String quit = "Quit";//label for quit button
	final int WIDTH = 500;//width of gui window, set as constant
	final int HEIGHT = 300;//height of gui window, set as constant
	//standard directory below
	private static String standardDirectory = "src\\MoviePlacePack\\Files";
	
	
	//method to construct media rental system
	public MediaRentalSystem() {
		
		//make window and set its title and default close operations
		window = new JFrame("Welcome to the Media Rental System");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//make menu bar and menu, label menu as "Menu"
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		
		//make buttons for menu
		JMenuItem loadButton = new JMenuItem(loadMedia);
		JMenuItem findButton = new JMenuItem(findMedia);
		JMenuItem rentButton = new JMenuItem(rentMedia);
		JMenuItem quitButton = new JMenuItem(quit);
		
		//set actions commands to buttons
		loadButton.setActionCommand(loadMedia);
		findButton.setActionCommand(findMedia);
		rentButton.setActionCommand(rentMedia);
		quitButton.setActionCommand(quit);
		
		//add action listeners to buttons
		loadButton.addActionListener(this);
		findButton.addActionListener(this);
		rentButton.addActionListener(this);
		quitButton.addActionListener(this);	
		
		//add buttons and a separator to menu
		menu.add(loadButton);
		menu.add(findButton);
		menu.add(rentButton);
		menu.addSeparator();
		menu.add(quitButton);
		
		//add menu to menu bar, then add menu bar to window
		menuBar.add(menu);
		window.setJMenuBar(menuBar);
		
		//set window size an start location
		window.setSize(WIDTH, HEIGHT);
		window.setLocation(600, 600);
	}
	
	
	//method to show the media rental system object to the user
	public void showRentalSystem(MediaRentalSystem mediaRentalSystem) {
		window.setVisible(true);//sets the window to visible, shows the user the GUI
	}
	
	
	//method to handle user inputs overrides base Java actionPerformed method
	@Override
	public void actionPerformed(ActionEvent event) {
		
		String command = event.getActionCommand();//set command as passed button action command
		
		//if load media button
		if (loadMedia.equals(command)) {
			
			//make a file chooser object, set window title
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Select File to Load");
			//set file selection mode to directories only
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			//set window input text to empty 
			fileChooser.setSelectedFile(null);
			//shows the open file window
			int option = fileChooser.showOpenDialog(this);
			
			//if user does not approve a file to open close the folder selection window back to the GUI
			if (option != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			//create a file object and set it as the folder chosen by the user
			File selectedFile = fileChooser.getSelectedFile();
			
			//try this
			try {
				//load media files in the path chosen by the user by passing that directory to the manager
				//validFileWasFound is returned a boolean value from the loadMediaFileMethod
				boolean validFileWasFound = manager.loadMediaFile(selectedFile.getAbsolutePath());
				
				//if no media file was found in the directory selected inform the user
				if(!validFileWasFound) {
					JOptionPane.showMessageDialog(window, "No files were found in that directory!");
					
					//otherwise the files in the folder selected were loaded
				} else {
					JOptionPane.showMessageDialog(window, "Directory was successfully loaded to the system.");
				}
			
			//if there is an error, throw exception, inform the user, and print the stackTrace of the exception
			} catch (IOException e){
				
				JOptionPane.showMessageDialog(window, "ERROR: A problem occured while scanning files.");
				e.printStackTrace();
				
			}
			
		//if the user clicked the find media button
		} else if (findMedia.equals(command)) {
			//ask user to input title and set as targetTitle
			String targetTitle = JOptionPane.showInputDialog("Enter the title");
			
			String matchingList = "";//empty string to fill with Media information
			
			//try this
			try {
				//if targetTitle is null/empty/blank inform the user and return to main GUI
				if (targetTitle == null || targetTitle.isBlank() || targetTitle.isEmpty()) {
					JOptionPane.showMessageDialog(window, "No title was entered, returning to main menu.");
					return;
				}
				
				//make a empty Media ArrayList set its values as those returned by the managers searchTitle method
				ArrayList<Media> matchingTitles =  manager.searchTitle(targetTitle);
				
				//for each Media object in the matchingTitles ArrayList
				for (int count = 0; count < matchingTitles.size(); count++) {
					//add its information to empty string created above and start a new line, via the objects toString
					//method
					matchingList += String.format(matchingTitles.get(count).toString() + "\n");
				}
				
				//if the string created is null, blank or empty, inform the user no Media with that title
				//exists
				if (matchingList == null || matchingList.isBlank() || matchingList.isEmpty()) {
					JOptionPane.showMessageDialog(window, "No media found with that title: " + targetTitle);
					
				//otherwise print the string created by the for loop
				} else {
					JOptionPane.showMessageDialog(window, matchingList);
				}
				
			//if an error occurs
			} catch (Exception e) {
				//inform the user via a message window and print the stackTrace
				JOptionPane.showMessageDialog(window, "ERROR: a problem occured while looking for title: " + targetTitle);
				e.printStackTrace();
			}
			
			
		//if the button press was the rent media button
		} else if (rentMedia.equals(command)) {
			
			//ask the user to input an id and set it as inputId
			String inputId = JOptionPane.showInputDialog(window, "Enter the ID:");
			//initialize targetId as 0 to hold the parsed input 
			int targetId = 0;
			
			//try this
			try {
				
				//if there was no input value inform the user and return to the main GUI
				if (inputId == null || inputId.isBlank() || inputId.isEmpty()) {
					JOptionPane.showMessageDialog(window, "You did not input a value, returning to main menu.");
					return;
				}
				
				//try to parse an int from the targetId String
				try {
					targetId = Integer.parseInt(inputId);
					
				//if a problem occurs such as entering a letter inform the user and return to the main GUI
				} catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(window, "ERROR: '" + inputId + "' is not a valid input!");
					return;
				}
				
				//if the targetId is less than or equal to 0, inform the user via a message and return to
				//the main GUI
				if (targetId <= 0) {
					JOptionPane.showMessageDialog(window, "ERROR: ID cannot be equal to or less than 0!");
					return;
				}
					
				//pass the targetId an standard directory to the rentMedia method and set fee as the returned
				//rental fee calculation
				double fee = manager.rentMedia(targetId, standardDirectory);
				//show the fee to the user
				JOptionPane.showMessageDialog(window, 
						String.format("Media was successfully rented. Rental fee = $%.2f", fee));
				
			//if unable
			} catch (Exception e){
				//inform the user with a message, message closing will return the user to the main GUI
				JOptionPane.showMessageDialog(window, "There is no media with the ID: " + inputId);
			}
			
			
		//if button was the quit button
		} else if (quit.equals(command)) {
			//acknowledge to the user that the system is closing
			JOptionPane.showMessageDialog(window, "Exiting System . . .");
			System.exit(0);//end the program
		}
	}
	
	
	//start of main method
	public static void main(String[] args) {
		
		//create the MediaRentalSystem object and then show it to the user via the showRentalSystem method
		MediaRentalSystem mediaRentalSystem = new MediaRentalSystem();
		mediaRentalSystem.showRentalSystem(mediaRentalSystem);

	}
}