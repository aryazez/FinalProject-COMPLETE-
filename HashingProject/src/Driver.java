/***************************************************
 * This is a web page spell checker.
 * Follow the prompts to check for suggestions
 * based on probability theory and simple edits.
 * Please open the "READ FIRST" file before running.
 **************************************************/

import java.io.*;

public class Driver {

	public static void main(String[] args) throws IOException {
		System.out.println("Let's get started!");
		System.out.println("Enter the URL for an english website, starting with http://");
		parser.htmlParser(); // Parse the page and put into a clean String
		checker.setParser(parser); 
		System.out.println("Searching for spelling errors ... "); 
		checker.spellChecker(); // Spell check the clean String
		System.out.println("Thanks for using the spell checker!");	
	}
// create objects of other classes to be used in main
	static WebScrape parser = new WebScrape();
	static Correct checker = new Correct();
	
}
