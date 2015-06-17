import org.jibble.pircbot.*;
import org.apache.commons.lang3.*;
import java.util.*;
import java.math.*;
import java.lang.*;
import java.io.*;
//import org.json.*;

public class WaheyBot extends PircBot{
    int wahey = 0;
    int waheymessage = 0;
    int nonwaheys = 0;
    int [] highscores = new int [2];
    long starttime = System.currentTimeMillis();
    boolean messagesent = true;
    public WaheyBot(){
        this.setName("WaheyBot");
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message){
        setMessageDelay(2500);
        if((StringUtils.containsIgnoreCase(message, "wahey") == true) || (StringUtils.containsIgnoreCase(message, "wahay") == true)){
            starttime = System.currentTimeMillis();
            nonwaheys = 0;
            wahey += StringUtils.countMatches(message.toLowerCase(), "wahey") + StringUtils.countMatches(message.toLowerCase(), "wahay");
            waheymessage++;
            messagesent = false;
        }

        if((StringUtils.containsIgnoreCase(message, "wahey") == false) && (StringUtils.containsIgnoreCase(message, "wahay") == false)){
            nonwaheys+=1;
        }

        if(((nonwaheys >= 5 || System.currentTimeMillis() - starttime > 45000)) && waheymessage < 15){
        	messagesent = true;
            wahey = 0;
            waheymessage = 0;
        }

        else if(((nonwaheys >= 5) && (messagesent == false)) || ((System.currentTimeMillis() - starttime > 45000) && (messagesent == false))){
        	try{
				highscores = utilities.readhighscore();
			}catch(IOException e){
			};
            speak(channel, "Chat had " + waheymessage + " messages containing Wahey and " + wahey + " individual Wahey's!");
            setMessageDelay(1501);
            if(highscores[1] < wahey && highscores[0] < waheymessage){
            	speak(channel, "Chat beat both the previous message score of " + highscores[0] + " by sending " + waheymessage + " messages containing Wahey AND the previous wahey score of " + highscores[0] + " by sending " + wahey + " individual Waheys!");
            	try{
					print(waheymessage, wahey, "highscore.text");
				}catch(IOException e){
				};
            }
            else if(highscores[0] < waheymessage){
            	speak(channel, "Chat beat the previous message score of " + highscores[0] + " by sending " + waheymessage + " messages containing Wahey!");
            	try{
					print(waheymessage, highscores[1], "highscore.text");
				}catch(IOException e){
				};
            }
            else if(highscores[1] < wahey){
            	speak(channel, "Chat beat the previous wahey score of " + highscores[0] + " by sending " + wahey + " individual Waheys!");
            	try{
					print(highscores[0], wahey, "highscore.text");
				}catch(IOException e){
				};
			}
			setMessageDelay(2500);
            messagesent = true;
            wahey = 0;
            waheymessage = 0;
        }

        if(message.equalsIgnoreCase("!info") || message.equalsIgnoreCase("information")){
            speak(channel, "Hi! My name is WaheyBot, and I am a bot made by 911rennsport AKA renn. My initial purpose was to count the number of Waheys posted to chat just to \"annoy\" Nospimi99; however, now I sit in chat and try to be helpful.");
        }

        if(message.equalsIgnoreCase("!wahey") || message.equalsIgnoreCase("waheyinfo") || message.equalsIgnoreCase("!wahay") || message.equalsIgnoreCase("!wahayinfo")){
            speak(channel, "There are no longer any limitations! Let any form of Wahey fly free!");
        }

        if(channel.equals("#nospimi99") && message.equalsIgnoreCase("!tattoo")){
        	speak(channel, "https://pbs.twimg.com/media/B6UWZN7CUAAn-ME.jpg and WIP: https://twitter.com/Nospimi99/status/606279392365387776");
        }

        if(channel.equals("#nospimi99") && message.equalsIgnoreCase("!kungfu")){
            speak(channel, "God, KungFu is such a butt face");
        }

        if(channel.equals("#nospimi99") && message.equalsIgnoreCase("!  ")){
            speak(channel, "The only person worse than Philip is Kungfu");
        }

        if(channel.equals("#nospimi99") && message.equalsIgnoreCase("!puppy")){
            speak(channel, "Help name the new puppy here! http://strawpoll.me/4289724/");
        }

        if(channel.equals("#nospimi99") && message.equalsIgnoreCase("!chaos")){
            speak(channel, "This is Super Mario 64 Chaos Edition, a ROM hack in which random Gameshark codes are activated every 5 seconds. Prolonged codes wear off after 30 seconds.");
        }

        if(channel.equals("#nospimi99") && message.equalsIgnoreCase("!waheyrecord")){
        	try{
				highscores = utilities.readhighscore();
			}catch(IOException e){
			};
			speak(channel, "The record for the most messages containing Wahey is " + highscores[0] + " and the record for the most individual Waheys is " + highscores[1] + "!");
        }

        if(channel.equals("#nospimi99") && message.equalsIgnoreCase("!multi")){
            
            speak(channel, "http://kadgar.net/live/nospimi99/firedragon764/kungfufruitcup/misskyliee");
        }
    }

    protected void onJoin(String channel, String sender, String login, String hostname)
    {
        if (sender.equals("WaheyBot"))
        {
            speak(channel, "/me is now in this channel and ready to count!");
        }
        System.out.println(" <" + sender + " entered>");
    }

    protected void onDisconnect(){
        while (!isConnected()) {
            try {
                reconnect();
                joinChannel("#nospimi99");
                Thread.sleep(1000);
            }
            catch (Exception e) {
                // Couldn't reconnect!
                // Pause for a short while...?
            }
        }
    }

    private void speak(String channel, String message)
    {
        sendMessage(channel, message);
        System.out.println(" WaheyBot: " + message);
    }

    private void print(int messages, int individual, String docname) throws IOException{
        PrintWriter writer = new PrintWriter(docname, "UTF-8");
        writer.println(messages);
        writer.println(individual);
        writer.close();
    }

    /*private int[] readhighscore() throws IOException{
    	Scanner scanner = new Scanner(new File("highscore.text"));
		int i = 0;
		while(scanner.hasNextInt())
		{
			highscores[i++] = scanner.nextInt();
		}
		return highscores;
    }*/
}