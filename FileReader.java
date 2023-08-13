import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class FileReader {
	private File f;	
	private Scanner scan;
	
	public FileReader(String filename) throws FileNotFoundException{
		f = new File(filename);
	}
	
	/*
	 * @returns the number of words  
	 * 
	 * counts spaces and making sure that the previous char wasn't a space and that this isn't 
	 * a space at the end of a line
	 */
	public int wordCount() throws FileNotFoundException {
		refresh();
		int count = 0;
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			if (line.length() >= 1) {
				count++;
			}
			for (int i = 1; i < line.length(); i++) {
				// if current item is space, not at the end of a line (since space at end of line does nothing), and prev was not a space
				if (isPunctuation(line.substring(i, i+1)) && i != line.length()-1 && !isPunctuation(line.substring(i-1, i))) {
					count++;
				}
				// current system causes false positives for double spaces at ends of lines. Subtracts one if end of line, equals space, and previous was a space
				if(i == line.length()-1 && isPunctuation(line.substring(i, i+1)) && isPunctuation(line.substring(i-1, i))){
					count --;
				}
			}
		}
		scan.close();
		return count;
	}
	
	/*
	 * iterates through, counting the length of every line or character count
	 * 
	 * @return number of characters
	 */
	public int charCount() throws FileNotFoundException {
		refresh();
		int count = 0;
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			count += line.length();
		}
		scan.close();
		return count;
	}
	
	/*
	 * @param word
	 * the word we are looking to find
	 * 
	 * @return the row number of the 1st occurrence of a word
	 * returns -1 if not found
	 * 
	 * iterates through the list comparing substrings of length of word to see
	 * if matches word
	 * each line loop adds a row to the count
	 */
	public int findWord(String word) throws FileNotFoundException{
		refresh();
		word = word.toLowerCase();
		int len = word.length();
		int row = 0;
		while (scan.hasNextLine()) {
			row++;
			String line = scan.nextLine();
			for (int i = 0; i < line.length()-len+1; i++) {
				if (line.substring(i, i+len).toLowerCase().equals(word)) {
					scan.close();
					return row;
				}
			}
		}
		scan.close();
		return -1;
	}
	
	/*
	 * Every time it comes across a new space or end line it makes the substring from
	 * previous space to this space/end line a new word and adds it to dual array
	 * dual array keeps track of the count of each word given to it increasing tally of duplicates
	 * return the top 10 most common words
	 * 
	 * @return the top 10 most frequent words and their number of occurrences
	 */
	public String frequency() throws FileNotFoundException{
		refresh();
		DualArrayList words = new DualArrayList();
		while (scan.hasNextLine()) {
			int firstSpace = 0;
			int secondSpace = 0;
			String line = scan.nextLine();
			for (int i = 1; i < line.length(); i++) {
				//make sure this is a space and the previous char wasnt
				if (isPunctuation(line.substring(i, i+1)) && !isPunctuation(line.substring(i-1,i))) {
					if (firstSpace != 0) {
						firstSpace++;
					}
					secondSpace = i;
					words.add(line.substring(firstSpace, secondSpace));
					firstSpace = secondSpace;
				}
				// if theres two space chars this will move the start of the next word to the second space occurrence
				else if (isPunctuation(line.substring(i, i+1))) {
					secondSpace = i;
					firstSpace = secondSpace;
				}
			}
			// used to cover the edge case of the final character, since there's no final space for
			// the loop to detect
			if (line.length() > 0 && !isPunctuation(line.substring(line.length()-1, line.length()))) {
				firstSpace++;
				words.add(line.substring(firstSpace, line.length()));
			}
		}
		scan.close();
		return words.getInfo();
	}
	
	/*
	 * @param word
	 * the word we are looking to count
	 * 
	 * Uses same logic as basic frequency but checks to make sure it is
	 * the same as the parameter word before adding to dual array
	 * 
	 * @returns the word's number of occurrences
	 */
	public String frequency(String word) throws FileNotFoundException{
		refresh();
		word = word.toLowerCase();
		DualArrayList words = new DualArrayList();
		while (scan.hasNextLine()) {
			int firstSpace = 0;
			int secondSpace = 0;
			String line = scan.nextLine();
			for (int i = 0; i < line.length(); i++) {
				if (isPunctuation(line.substring(i, i+1))) {
					if (firstSpace != 0) {
						firstSpace++;
					}
					secondSpace = i;
					if (line.substring(firstSpace, secondSpace).equals(word)) {
						words.add(line.substring(firstSpace, secondSpace));
					}
					firstSpace = secondSpace;
				}
				else if (isPunctuation(line.substring(i, i+1))) {
					secondSpace = i;
					firstSpace = secondSpace;
				}
			}
			if (line.length() > 0 && !isPunctuation(line.substring(line.length()-1, line.length()))) {
				firstSpace++;
				if (line.substring(firstSpace, line.length()).equals(word)) {
					words.add(line.substring(firstSpace, line.length()));
				}
			}
		}
		scan.close();
		return words.getInfo();
	}
	
	private void refresh() throws FileNotFoundException {
		scan = new Scanner(f);
	}
	
	/*
	 * @param x
	 * the character we want to compare
	 * 
	 * compares character to determine if it is equal to one of the punctuation items
	 * that signifies an end of a word.
	 */
	private boolean isPunctuation(String x) {
		if(x.equals("!")) {
			return true;
		}
		if(x.equals("?")) {
			return true;
		}
		if(x.equals(" ")) {
			return true;
		}
		if(x.equals(",")) {
			return true;
		}
		if(x.equals(".")) {
			return true;
		}
		return false;
	}
	
}
