import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class WebScrape {
	Scanner urlInput = new Scanner(System.in);
	private String cleanString = "";
	private String url = "";
	boolean isDone = false;
	public void htmlParser() {

		while (!isDone) {
			try {
				url = urlInput.nextLine();
				// This creates a document out of the HTML on the web page
				Document doc = Jsoup.connect(url).get();
				// This converts the document into a string to be cleaned
				cleanString = doc.toString();
				setCleanWords(Jsoup.clean(cleanString, Whitelist.none()));
				isDone = true;
			} catch (Exception e) {
				System.out.println("Incorrect format for a URL. Please try again.");
			}
		}
	}

	public void setCleanWords(String cleanString) {
		this.cleanString = cleanString;
	}

	public String getCleanWords() {
		return cleanString;
	}
}
