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
    long waheypersecondtime = 0;
    long waheypersecondtimeend = 0;
    long starttime = System.currentTimeMillis();
    boolean messagesent = true;
    boolean waheypersecond = false;

    public WaheyBot(){
        this.setName("WaheyBot");
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message){ //detects the first Wahey and allows future ones to be counted.
        setMessageDelay(2500); // we reset the message delay at this as to make sure the bot doesn't get global'd
        if((StringUtils.containsIgnoreCase(message, "wahey") == true) || (StringUtils.containsIgnoreCase(message, "wahay") == true)){ // allows all forms of Wahey and Wahay (e.g. WahEY and waHaY)
            starttime = System.currentTimeMillis(); //always resets the time to 0 if a wahey is posted beause 45 seconds after no waheys are posted the bot will either post the count or do nothing if the count is less than 15
            waheypersecondtimeend = (System.currentTimeMillis()/1000); //takes the time when a wahey is sent
            nonwaheys = 0; // upon each wahey or wahay posted the non-waheys are reset to zero because they would no longer be consecitive which would make the bot either post the count or do nothing if the count is less than 15
            wahey += StringUtils.countMatches(message.toLowerCase(), "wahey") + StringUtils.countMatches(message.toLowerCase(), "wahay"); // this addes the total waheys in every message together
            waheymessage++; // total messages
            messagesent = false;
            if(waheypersecond == false){ //sets a long to time in seconds the first wahey was sent
                waheypersecondtime = (System.currentTimeMillis()/1000);
                waheypersecond = true;
            }
        }

        if((StringUtils.containsIgnoreCase(message, "wahey") == false) && (StringUtils.containsIgnoreCase(message, "wahay") == false)){ // adds up consecutive non-waheys
            nonwaheys+=1;
        }

        if(((nonwaheys >= 5 || System.currentTimeMillis() - starttime > 45000)) && waheymessage < 15){ //this makes the bot do nothing if the count isn't above 15 after 5 consecutive non-waheys or 45 seconds
        	messagesent = true;
            waheypersecond = false;
            wahey = 0;
            waheymessage = 0;
        }

        else if(((nonwaheys >= 5) && (messagesent == false)) || ((System.currentTimeMillis() - starttime > 45000) && (messagesent == false))){ //This gets confusing so bare with me
        	try{ //reads highscore file
				highscores = utilities.readhighscore();
			}catch(IOException e){
			};
            speak(channel, "Chat had " + waheymessage + " messages containing Wahey and " + wahey + " individual Wahey's!" + " We also sent Waheys at " + ((double)wahey/(waheypersecondtimeend-waheypersecondtime)) + " per second!"); //posts the waheys it has counted and the messages and the waheys per second
            setMessageDelay(1501); // sets message delay to the smallest Twitch legal amount for a non-mod
            if(highscores[1] < wahey && highscores[0] < waheymessage){ // checks to see if the wahey message count and wahey count beats the highscore
            	speak(channel, "Chat beat both the previous message score of " + highscores[0] + " by sending " + waheymessage + " messages containing Wahey AND the previous wahey score of " + highscores[1] + " by sending " + wahey + " individual Waheys!");
            	try{
					utilities.print(waheymessage, wahey, "highscore.txt"); //writes new highscores
				}catch(IOException e){
				};
            }
            else if(highscores[0] < waheymessage){ // if not we check if the message count beat the highscore
            	speak(channel, "Chat beat the previous message score of " + highscores[0] + " by sending " + waheymessage + " messages containing Wahey!");
            	try{
					utilities.print(waheymessage, highscores[1], "highscore.txt"); //writes new highscore
				}catch(IOException e){
				};
            }
            else if(highscores[1] < wahey){ // and finally if the other two failed we check if the total wahey count was beaten
            	speak(channel, "Chat beat the previous wahey score of " + highscores[1] + " by sending " + wahey + " individual Waheys!");
            	try{
					utilities.print(highscores[0], wahey, "highscore.txt"); //writes new highscore
				}catch(IOException e){
				};
			}
			setMessageDelay(2500); // global prevention
            messagesent = true; // resetting the bot so it doesn't spam
            wahey = 0; // counts need to be reest too
            waheymessage = 0; // ^
        }
        // the following are custom commands and they will soon be moved to a JSON file, but I'm busy.
        if(message.equalsIgnoreCase("!info") || message.equalsIgnoreCase("!information")){
            speak(channel, "Hi! My name is WaheyBot, and I am a bot made by 911rennsport AKA renn. My initial purpose was to count the number of Waheys posted to chat just to \"annoy\" Nospimi99; however, now I sit in chat and try to be helpful.");
        }

        if(message.equalsIgnoreCase("!wahey") || message.equalsIgnoreCase("waheyinfo") || message.equalsIgnoreCase("!wahay") || message.equalsIgnoreCase("!wahayinfo")){
            speak(channel, "There are no longer any limitations! Let any form of Wahey fly free!");
        }

        if(channel.equals("#nospimi99")){
            if(message.equalsIgnoreCase("!tattoo")){
                speak(channel, "https://pbs.twimg.com/media/B6UWZN7CUAAn-ME.jpg and WIP: https://twitter.com/Nospimi99/status/606279392365387776");
            }
            if(message.equalsIgnoreCase(!"kungfu")){
                speak(channel, "God, KungFu is such a butt face");
            }
        }

        if(channel.equals("#nospimi99") && message.equalsIgnoreCase("!philip")){
            speak(channel, "The only person worse than Philip is Kungfu");
        }

        if(channel.equals("#nospimi99") && message.equalsIgnoreCase("!puppy")){
            speak(channel, "Help name the new puppy here! http://strawpoll.me/4289724/");
        }

        if(channel.equals("#nospimi99") && message.equalsIgnoreCase("!chaos")){
            speak(channel, "This is Super Mario 64 Chaos Edition, a ROM hack in which random Gameshark codes are activated every 5 seconds. Prolonged codes wear off after 30 seconds.");
        }

        if(channel.equals("#nospimi99") && message.equalsIgnoreCase("!DCW")){
            speak(channel, "DCW stands for Delayed Cutscene Warp and is basically a Wrong Warp to the final boss using an exploit in Witchyworld. The Any% w/DCW WR takes just under an hour, but without DCW the WR is just under 3 hours.");
        }

        if(channel.equals("#nospimi99") && message.equalsIgnoreCase("!SGDQ")){
            speak(channel, " SGDQ stands for Summer games done quick and is a week long speed running marathon that raises money for charity that takes place usually in June or July. for more informtation go to https://gamesdonequick.com/");
        }

        if(channel.equals("#nospimi99") && message.equalsIgnoreCase("!AGDQ")){
            speak(channel, "AGDQ stands for Awesome games done quick and is a week long speed running marathon that raises money for charity that takes place usually in January. for more informtation go to https://gamesdonequick.com/");
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

    //this doesn't work I should just remove it
    protected void onJoin(String channel, String sender, String login, String hostname){
        if (sender.equals("WaheyBot"))
        {
            speak(channel, "/me is now in this channel and ready to count!");
        }
        System.out.println(" <" + sender + " entered>");
    }
    //reconnects on disconnect
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

    //made it so I didnt need to type sendMessage(channel, message) all the time and shows the bot's messages in chat
    private void speak(String channel, String message){
        sendMessage(channel, message);
        System.out.println(" WaheyBot: " + message);
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
