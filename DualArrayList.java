import java.util.ArrayList;
public class DualArrayList {
	ArrayList<Integer> count = new ArrayList<Integer>();
	ArrayList<String> words = new ArrayList<String>();
	public DualArrayList() {
		
	}
	
	/*
	 * 
	 * @param name
	 * word to be added 
	 * 
	 * searches the words for name. If name is not in words it is added, along with a 1 being added 
	 * in the same index as in words
	 * 
	 * if it is present add +1 to the index of the word in words to the count of that index
	 */
	public void add(String name) {
		for (String x: words) {
			if (x.equals(name.toLowerCase())) {
				int index = words.indexOf(x);
				count.set(index, count.get(index)+1);
				return;
			}
		}
		count.add(1);
		words.add(name.toLowerCase());
		
	}
	/*
	 * selection sort algorithm of count. Index is reflected onto words arraylist
	 */
	public void sort() {
		for (int i = 0; i < count.size(); i++) {
			int max = count.get(i);
			int index = i;
			for (int j = i; j < count.size(); j++) {
				if (count.get(j) > max) {
					index = j;
					max = count.get(j);
				}
			}
			String maxWord = words.get(index);
			count.set(index, count.get(i));
			words.set(index, words.get(i));
			count.set(i, max);
			words.set(i, maxWord);
		}
	}
	/*
	 * Sorts to find the highest numbers/most used words
	 * @returns top 10 and their occurrence count
	 */
	public String getInfo() {
		sort();
		if (count.size() == 0) {
			return "No occurrences of that word.";
		}
		String response = "";
		int range = count.size();
		if (count.size() > 10) {
			range = 10;
		}
		for (int i = 0; i < range; i++) {
			response += "\"" + words.get(i) + "\"" + " occurs " + count.get(i) + " times.\n";
		}
		return response;
	}
}
