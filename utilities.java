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
		while(scanner.hasNextInt())
		{
			highscores[i++] = scanner.nextInt();
		}
		return highscores;
    }
}