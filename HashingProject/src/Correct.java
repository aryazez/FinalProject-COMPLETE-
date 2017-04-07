import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Correct {
	static HashMap<String, String> dictionary;
	static boolean suggestWord;
	WebScrape parser = new WebScrape();

	void setParser(WebScrape parser) {
		this.parser = parser;
	}

	/**
	 * Compares user input to the dictionary
	 */

	Suggest suggest = new Suggest("wordprobabilityDatabase.txt");

	public void spellChecker() throws IOException {

		dictionary = new HashMap<String, String>();

		try {
			// Read and store the words of the dictionary
			BufferedReader dictReader = new BufferedReader(new FileReader("dictionary.txt"));

			while (dictReader.ready()) {
				String dictInput = dictReader.readLine();
				String[] dict = dictInput.split("\\s"); // create an array of
														// dictionary words

				for (int i = 0; i < dict.length; i++) {
					// key and value are identical
					dictionary.put(dict[i], dict[i]);
				}
			}
			dictReader.close();
			String userText = "";

			userText = parser.getCleanWords();
			String[] words = userText.split(" ");
			// Remove repetitive words by storing them in a HashSet
			Set<String> wordSet = new HashSet<>();
			int error = 0;
			for (String word : words) {
				if (!wordSet.contains(word)) {
					punctRemover(word);
					suggestWord = true;
					String outputWord = punctRemover(word);
					if (suggestWord) {
						System.out.println("Suggestions for " + word + " are:  " + suggest.correct(outputWord) + "\n");
						error++;
					}
				}
				// If a word appears more than once, store inside the
				// HashSet to avoid re-checking
				wordSet.add(word);

				if (error == 0) {
					System.out.println("No mistakes found");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * REMOVES PUNCTUATION AND COMPARES TO DICTIONARY
	 */

	public static String punctRemover(String wordToBeChecked) {
		String wordInDictionary, unpunctWord;
		String wordEntered = wordToBeChecked.toLowerCase();

		// if the word is found in the dictionary, it is correct
		if ((wordInDictionary = dictionary.get(wordEntered)) != null) {
			suggestWord = false; // no suggestions if correct
			return wordInDictionary;
		}

		// Removing punctuation at end of word and giving it a shot (". or "."
		// or "?!")
		int length = wordEntered.length();

		// Checking for the beginning of quotes(example: "she )
		if (length > 1 && wordEntered.substring(0, 1).equals("\"")) {
			unpunctWord = wordEntered.substring(1, length);

			if ((wordInDictionary = dictionary.get(unpunctWord)) != null) {
				suggestWord = false; // word is correct
				return wordInDictionary;
			} else // not found
				return unpunctWord;
		}

		// Checking if punctuation at the end is the problem (example: book.)
		// when book is present in the dictionary).
		if (wordEntered.substring(length - 1).equals(".") || wordEntered.substring(length - 1).equals(",")
				|| wordEntered.substring(length - 1).equals("!") || wordEntered.substring(length - 1).equals(";")
				|| wordEntered.substring(length - 1).equals(":") || wordEntered.substring(length - 1).equals("\"")
				|| wordEntered.substring(length - 1).equals("\",") || wordEntered.substring(length - 1).equals("\".")) {
			unpunctWord = wordEntered.substring(0, length - 1);

			if ((wordInDictionary = dictionary.get(unpunctWord)) != null) {
				suggestWord = false; // no suggestions if correct
				return wordInDictionary;
			} else {
				return unpunctWord; // removing the punctuation and returning it
									// clean
			}
		}

		// Checking for (!,\,",etc) ... (i.e.: watch!" when
		// watch is present in the dictionary)

		if (length > 2 && (wordEntered.substring(length - 2).equals(",\"")
				|| wordEntered.substring(length - 2).equals(".\"") || wordEntered.substring(length - 2).equals("?\"")
				|| wordEntered.substring(length - 2).equals("!\"") || wordEntered.substring(length - 2).equals("\""))) {
			unpunctWord = wordEntered.substring(0, length - 2);

			if ((wordInDictionary = dictionary.get(unpunctWord)) != null) {
				suggestWord = false; // no suggestions if correct
				return wordInDictionary;
			} else { // not found
				suggestWord = false;
				return unpunctWord;
			}
		}
		// If word could not be corrected, return as is
		return wordEntered;
	}
}
