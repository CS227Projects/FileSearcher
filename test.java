import java.io.FileNotFoundException;
public class test {

	public static void main(String[] args) throws FileNotFoundException {
		FileReader x = new FileReader("C:\\Users\\Kaden Neddermeyer\\eclipse-workspace\\FileSearcher\\src\\FileReaderTest.txt");
		System.out.println(x.charCount() + " characters");
		System.out.println(x.wordCount() + " words");
		String word = "Borg";
		System.out.println(x.findWord(word) + " is the line of the first occurence of " + word);
		System.out.println(x.frequency());
		System.out.println(x.frequency("Borg"));
	}

}

/*
 * File f = new File("nums.txt");
    
    // We can still create a File object to represent
    // the path to a filename, even if there is no such file
    //File f = new File("boogers");
    
    System.out.println(f.exists());
    System.out.println(f.getName());
    System.out.println(f.getAbsolutePath());
    System.out.println(f.length());
 */
