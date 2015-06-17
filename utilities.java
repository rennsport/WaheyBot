import org.jibble.pircbot.*;
import org.apache.commons.lang3.*;
import java.util.*;
import java.math.*;
import java.lang.*;
import java.io.*;

public class utilities{
	public static int[] readhighscore() throws IOException{
		int [] highscores = new int [2];
    	Scanner scanner = new Scanner(new File("highscore.text"));
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
}