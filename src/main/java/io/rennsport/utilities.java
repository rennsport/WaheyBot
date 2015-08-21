package io.rennsport;

import org.jibble.pircbot.*;
import org.apache.commons.lang3.*;
import java.util.*;
import java.math.*;
import java.lang.*;
import java.io.*;
import java.net.*;
import org.json.*;

public class utilities{
	static String correctGameKeyForJSONFile = "";
	public static int[] readHighScore() throws IOException{ //reads highscores by storing each new int in an int array
		int [] highscores = new int [2];
    	Scanner scanner = new Scanner(new File("highscore.txt"));
		int i = 0;
		while(scanner.hasNextInt()){
			highscores[i++] = scanner.nextInt();
		}
		return highscores;
    }
    public static String readFile(String fileName) { // file reader for oauth token
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
    public static void print(int messages, int individual, String docname) throws IOException{ //method for printing new highscores
    	PrintWriter writer = new PrintWriter(docname, "UTF-8");
        writer.println(messages);
        writer.println(individual);
        writer.close();
    }
	public static String channelInfo(String type, String channel){
		try{
			URL url = new URL("https://api.twitch.tv/kraken/channels/" + channel);
			InputStream is = url.openStream();

			JSONObject obj = new JSONObject(new JSONTokener(is));
			String status = (String) obj.get(type);
			is.close();
			return status.toString();
		} catch (Exception e){
			return null;
		}
	}
	public static String streamInfo(String type, String channel){
        try{
            URL url = new URL("https://api.twitch.tv/kraken/streams/" + channel);
            InputStream is = url.openStream();

            JSONObject obj = new JSONObject(new JSONTokener(is));
            String status = (String) obj.getJSONObject("stream").get(type);
            is.close();
            return status.toString();
        } catch (Exception e){
            return null;
        }
    }
	public static String[] categoryArray(String srurl, String game) {
		ArrayList<String> categories = new ArrayList<String>();
		try {
			URL url = new URL(srurl);
			InputStream is = url.openStream();
			JSONObject jObject = new JSONObject(new JSONTokener(is));
			Iterator<?> gameKeys = jObject.keys();
			String matchedKey;

			while(gameKeys.hasNext()){
				matchedKey = (String)gameKeys.next();
	            String key = new String(matchedKey.getBytes("UTF-8"));

	            // Test to see if it passed
	            //System.out.println(game + " equals " + key + "? :" + game.equals(key));

	            if(key.equals(game)){
	                game = matchedKey;
					correctGameKeyForJSONFile = matchedKey;
	                break;
	            }
	        }

			Iterator<?> keys = jObject.getJSONObject(game).keys();

			while(keys.hasNext()){
				String key = (String)keys.next();
				if(jObject.getJSONObject(game).get(key) instanceof JSONObject){
					categories.add(key);
				}
			}
			String[] categroiesarray = categories.toArray(new String[categories.size()]);
			return categroiesarray;
		} catch(Exception e){
			//e.printStackTrace();
			return null;
		}
	}
	public static String getSRInfo(String game, String category, String info){
      	try{
        	URL url = new URL("http://www.speedrun.com/api_records.php?game=" + StringUtils.replace(game, " ", "%20"));
        	InputStream is = url.openStream();
			JSONObject obj = new JSONObject(new JSONTokener(is));

			String time = obj.getJSONObject(correctGameKeyForJSONFile).getJSONObject(category).getString(info);
			is.close();
			return time;
		} catch (Exception e){
			return null;
		}
    }
	public static String timeConversion(int totalSeconds) {

		//I'm really lazy http://codereview.stackexchange.com/a/62714
		final int MINUTES_IN_AN_HOUR = 60;
		final int SECONDS_IN_A_MINUTE = 60;
		int seconds = totalSeconds % SECONDS_IN_A_MINUTE;
    	int totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE;
    	int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
    	int hours = totalMinutes / MINUTES_IN_AN_HOUR;

		if(hours == 0){
			return minutes + "m " + seconds + "s";
		}
		else{
			return  hours + "h " + minutes + "m " + seconds + "s";
		}
	}
}
