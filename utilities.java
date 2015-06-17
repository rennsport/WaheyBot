import org.jibble.pircbot.*;
import org.apache.commons.lang3.*;
import java.util.*;
import java.math.*;
import java.lang.*;
import java.io.*;

public class utilities{
	public static int[] readhighscore() throws IOException{
		int [] highscores = new int [2];
    	Scanner scanner = new Scanner(new File("highscore.txt"));
		int i = 0;
		while(scanner.hasNextInt()){
			highscores[i++] = scanner.nextInt();
		}
		return highscores;
    }
    public static String readfile(String fileName) {
    	String st = "";
    	try {
    		Scanner scanner = new Scanner(new File(fileName));
    		while (scanner.hasNext()) {
    			st = scanner.next();
    		}
    		scanner.close();
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
    	return st;
    }
    public static void print(int messages, int individual, String docname) throws IOException{
    	PrintWriter writer = new PrintWriter(docname, "UTF-8");
        writer.println(messages);
        writer.println(individual);
        writer.close();
    }
}