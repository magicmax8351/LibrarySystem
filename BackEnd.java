import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class BackEnd {

	public static void main(String[] args) throws Exception {
		
		ArrayList<User> LOU = new ArrayList<User>(); // LOU = list of users
		
		User sample = new User("skyler","1"); // just for testing
		LOU.add(sample);					  // also just for testing
		
		boolean cont = true; // cont as in continue  
		
		System.out.println("What would you like to do?");
		System.out.println();
		
		Scanner scan = new Scanner(System.in);
		
		int input;
		
		do 
		{
		
		System.out.println("1: Create new user");
		System.out.println("2: Assign book to user");
		System.out.println("3: Return book for user");
		System.out.println("4: Lookup a user by ID");
		System.out.println("5: Lookup user by name"); 
		System.out.println("6: Lookup book by ISBN"); 
		
		input = scan.nextInt();
		
		
		if(input == 1) {
			System.out.println("1 selected");
			System.out.println();
			System.out.println("What is this user's name?");
			String nameInput = scan.next();
			System.out.println("What is this user's ID?");
			String IDInput = scan.next();
			User x = new User(nameInput,IDInput); //the x variable doesn't matter since it will be overwritten every time a user is added - but the info
												  // from the last user is saved into the arraylist
			LOU.add(x);
			
			}
		
		else if(input == 2) {
			System.out.println("2 selected");
			System.out.println();
			System.out.println("What user do you want to assign a book to?");
			String nameInput = scan.next();
			for(int i = 0; i<LOU.size(); i++) {
				if(LOU.get(i).getName().equalsIgnoreCase(nameInput)) {
					System.out.println("We have found the user - what book should we add?");
					scan.nextLine(); //skips to a new line so that the menu number input line doesn't get goofed
					String rBookTitle = scan.nextLine(); //allows for title with spaces 
					LOU.get(i).addBook(rBookTitle);
					System.out.println("Book " + rBookTitle + " has been added. ");
					i = LOU.size();
				}
			}
		}
		
		else if(input == 3) {
			System.out.println("3 selected");		
			System.out.println();
		}
		
		else if(input == 4) {
			System.out.println("4 selected");		
			System.out.println();
			System.out.println("Enter ID you are looking for: ");
			String IDInput = scan.next();
			for(int i = 0; i<LOU.size(); i++) {
				if(LOU.get(i).getID().equals(IDInput)) {
					System.out.println("We found user with ID " + LOU.get(i).getID());
					System.out.println("Their name is " + LOU.get(i).getName());
					LOU.get(i).getBooks();
				}
			}
		}
		
		else if(input == 5) {
			System.out.println("5 selected");		
			System.out.println();
			System.out.println("What name are you looking for?");
			String nameInput = scan.next();
			for(int i = 0; i<LOU.size(); i++) {
				if(LOU.get(i).getName().equalsIgnoreCase(nameInput)) {
					System.out.println("We found user " + LOU.get(i).getName());
					System.out.println("Their ID is " + LOU.get(i).getID());
					LOU.get(i).getBooks();
				}
			}
		}
		
		else if(input == 6) {
			System.out.println("Enter ISBN:");
			String ISBN = scan.next();
			ISBNLookup(ISBN);
		}
		else {
			System.out.println("oop try again");		
			System.out.println();
		}
		
		}while(cont);
		
	
	}


public static void click(int x, int y) throws AWTException{ //takes care of click to verify - actually no it doesn't :( 
    Robot bot = new Robot();								//shoudln't have spent time even trying to do this obviously 
    bot.mouseMove(x, y);    								//it wouldn't work, otherwise reCAPTHA would be worthless
    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);			//I ended up using an IBSN lookup that doesn't screen out robots 
    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);			//but i thought I'd leave this as a little reminder haha
	
}


public static void ISBNLookup(String ISBN) throws Exception {
	String lookup = "https://isbndb.com/book/" + ISBN;
	try {
		URL myURL = new URL(lookup);
		BufferedReader in = new BufferedReader(new InputStreamReader(myURL.openStream()));
		String inputLine;
		String bookTitle;

        while ((inputLine = in.readLine()) != null) {
        	if(inputLine.contains("<h1>")) {
        		inputLine = inputLine.replaceAll("&#039", "'");
        		inputLine = inputLine.replaceAll(";", "");
        		inputLine = inputLine.replaceAll("<h1>", "");
        		inputLine = inputLine.replaceAll("</h1>", "");		        	
        		bookTitle = inputLine;
        		System.out.println(bookTitle);
    	}
        }
        in.close();
	}
	catch (MalformedURLException e) {
		System.out.println("Failed to connect to URL"); //doubt it'll ever catch this exception
	}
	catch (FileNotFoundException f) {
		System.out.println("Failed to find ISBN");
	}
	
	
}
}

