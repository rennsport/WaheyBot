import java.util.*;
import java.math.*;
import java.lang.*;
import java.io.*;
public abstract class PrintHighScore
{
    public static void print(int messages, int individual) throws IOException{
        PrintWriter writer = new PrintWriter("highscore.text", "UTF-8");
        writer.println(messages);
        writer.println(individual);
        writer.close();
    }
}