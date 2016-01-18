package io.rennsport;

import org.jibble.pircbot.*;
import org.apache.commons.lang3.*;
import java.util.*;
import java.math.*;
import java.lang.*;
import java.io.*;
import org.json.*;
import java.net.*;
import java.nio.charset.Charset;

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

    private String title;
    private String game;
    private boolean partner;
    private String uptime;

    public WaheyBot(String twitchName){
        this.setName(twitchName);
    }
    public void onMessage(String channel, String sender, String login, String hostname, String message){ //detects the first Wahey and allows future ones to be counted.
        String channelname = channel.substring(1);
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
				highscores = utilities.readHighScore();
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
        if(message.equalsIgnoreCase("!wahey") || message.equalsIgnoreCase("!waheyinfo") || message.equalsIgnoreCase("!wahay") || message.equalsIgnoreCase("!wahayinfo")){
            speak(channel, "I count the number of Waheys the chat posts. It doesn't matter how you type it either WAHEY, wAhAy, and WEHay are all valid examples. Let any form of Wahey fly free!");
        }
        if(channel.equals("#nospimi99")){
            if(message.equalsIgnoreCase("!tattoo")){
                speak(channel, "https://pbs.twimg.com/media/B6UWZN7CUAAn-ME.jpg and WIP: https://twitter.com/Nospimi99/status/606279392365387776");
            }
            if(message.equalsIgnoreCase("!sub")){
                speak(channel, "http://www.twitch.tv/nospimi99/subscribe");
            }
            if(message.equalsIgnoreCase("!emotes")){
                speak(channel, "Sub Emotes: nossyHi nossyDerp nossyLove nossyPanic nossyParty nossyHelp nossyWAT nossySplit FrankerFaceZ Emotes: BOKUM ChibiQuil EEKUM LizDerp LoveBag NapTime nosGlod nosHeart nosWhoosh nosWin");
            }
            if(message.equalsIgnoreCase("!kungfu")){
                speak(channel, "God, KungFu is such a butt face");
            }
            if(message.equalsIgnoreCase("!DCW") /*|| (StringUtils.containsIgnoreCase(message, "DCW")) && (StringUtils.containsIgnoreCase(message, "?")) || (StringUtils.containsIgnoreCase(message, "DCW")) && (StringUtils.containsIgnoreCase(message, "what"))*/){
                speak(channel, "@" + sender + ", DCW stands for Delayed Cutscene Warp and is basically a Wrong Warp to the final boss using an exploit in Witchyworld. The Any% w/DCW WR takes just under an hour, but without DCW the WR is just under 3 hours.");
            }
            if(message.equalsIgnoreCase("!SGDQ")){
                speak(channel, " SGDQ stands for Summer games done quick and is a week long speed running marathon that raises money for charity that takes place usually in June or July. for more informtation go to https://gamesdonequick.com/");
            }
            if(message.equalsIgnoreCase("!AGDQ")){
                speak(channel, "AGDQ stands for Awesome games done quick and is a week long speed running marathon that raises money for charity that takes place usually in January. for more informtation go to https://gamesdonequick.com/");
            }
            if(message.equalsIgnoreCase("!multi")){
                speak(channel, "http://kadgar.net/live/nospimi99/jctomo or http://multitwitch.tv/nospimi99/jctomo");
            }
            if(message.equalsIgnoreCase("!glitch") /*|| ((StringUtils.containsIgnoreCase(message, "glitch")) && ((StringUtils.containsIgnoreCase(message, "what")) || (StringUtils.containsIgnoreCase(message, "what's")) || (StringUtils.containsIgnoreCase(message, "what is"))))*/){
                speak(channel, "@" + sender + ", PasteBin to the new glitch http://pastebin.com/5e8mFGEw");
            }
            if(message.equalsIgnoreCase("!waheyrecord")){
                try{
                    highscores = utilities.readHighScore();
                }catch(IOException e){
                };
                speak(channel, "The record for the most messages containing Wahey is " + highscores[0] + " and the record for the most individual Waheys is " + highscores[1] + "!");
            }
        }

        if(message.equals("!wr")) // Someone is requesting the World Record time for the current game.
        {

            String srurl = "http://www.speedrun.com/api_records.php?game=" + StringUtils.replace(game, " ", "%20");

            String[] categories = utilities.categoryArray(srurl, game);

            if(categories == null)
            {
                speak(channel, "Sorry there are no SpeedRun.com leaderboards for this game.");
            }
            else
            {
                String catToSend = "";
                boolean titleHasCat = false;

                // For each category..
                for(int i = 0; i < categories.length; i++)
                {
                    String currentCategoryRaw = categories[i];
                    String currentCategoryUTF = utilities.strToUTF8(categories[i]);

                    titleHasCat = title.contains(currentCategoryUTF);

                    String titleHasStr = titleHasCat ? "contains " : "does NOT contain ";

                    System.out.println("[INFO] Found category \"" + currentCategoryUTF + "\"\n"
                                    + "[INFO] The title " + titleHasStr
                                    + "the category \""
                                    + currentCategoryUTF + "\""
                    );

                    // If the stream title contains the current category
                    if(titleHasCat)
                    {
                        // If the current category is longer than the previous one.
                        if(currentCategoryRaw.length() > catToSend.length())
                        {
                            // Use the longer category instead.
                            catToSend = currentCategoryRaw;
                        }
                    }
                }

                // Send the message if it exists (if catToSend does NOT equal "".
                if(!catToSend.equals(""))
                {
                    String time = utilities.timeConversion(Integer.parseInt(utilities.getSRInfo(game, catToSend, "time")));
                    speak(channel, "The world record for " + game + " " + catToSend + " is " + time + " by " + utilities.getSRInfo(game, catToSend, "player"));
                }
                else
                {
                    speak(channel, "Good job, you broke something... Couldn't find a matching category for "
                                    + game + ".");
                }
            }
        }
        if(message.equals("!channelinfo")){
            speak(channel, title + " | " + game);
        }
    }
    protected void onJoin(String channel, String sender, String login, String hostname){
        if (sender.equals("waheybot"))
        {
            //speak(channel, "/me is now in this channel and ready to count!");
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
    //made it so I didnt need to type sendMessage(channel, message) all the time, and this shows the bot's messages in chat
    private void speak(String channel, String message){
        sendMessage(channel, message);
        System.out.println(" WaheyBot: " + message);
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String channel)
    {
        title = utilities.channelInfo("status", channel);
    }
    public String getGame(){
        return game;
    }
    public void setGame(String channel){
        game = utilities.channelInfo("game", channel);
    }
    public boolean getPartner(){
        return partner;
    }
    public void setPartner(String channel){
        partner = Boolean.valueOf(utilities.channelInfo("partner", channel));
    }
    public String getStreamUpTime(){
        return uptime;
    }
    public void setStreamUpTime(String channel){
        uptime = utilities.streamInfo("created_at", channel);
    }
    /*private int[] readHighScore() throws IOException{
    	Scanner scanner = new Scanner(new File("highscore.text"));
		int i = 0;
		while(scanner.hasNextInt())
		{
			highscores[i++] = scanner.nextInt();
		}
		return highscores;
    }*/
}
