package io.rennsport;

import org.jibble.pircbot.*;
import org.apache.commons.lang3.*;
import java.util.*;

public class WaheyBotMain {

    public static void main(final String[] args) throws Exception {
        String oauth = utilities.readFile("oauth.txt"); //Create an oauth.txt file in the running directory

        final ArrayList<WaheyBot> bots = new ArrayList<WaheyBot>();
        for(String s: args){
            WaheyBot bot = new WaheyBot();
            //WaheyBot bot2 = new WaheyBot();
            bot.setVerbose(true);
            bot.connect("irc.twitch.tv", 6667, oauth);
            bot.joinChannel("#" + s);
            bot.sendRawLine("CAP REQ :twitch.tv/commands");
            bots.add(bot);
        }

        //final WaheyBot bot = new WaheyBot();
        //WaheyBot bot2 = new WaheyBot();
        //bot.setVerbose(true);
        //bot.connect("irc.twitch.tv", 6667, oauth);
        //bot.joinChannel("#nospimi99");
        //bot2.setVerbose(true);
        //bot2.connect("irc.twitch.tv", 6667, oauth);
        //bot2.joinChannel("#911rennsport");
        //bot.sendRawLine("CAP REQ :twitch.tv/tags");
        //bot.sendRawLine("CAP REQ :twitch.tv/commands");

        //Updates Game and Title from Twitch per channel.
        Timer timer = new Timer();
        TimerTask hourlyTask = new TimerTask() {
            @Override
            public void run () {
                for(int i = 0; i < bots.size(); i++){
                    bots.get(i).setGame(args[i]);
                    bots.get(i).setTitle(args[i]);
                    bots.get(i).setPartner(args[i]);
                    bots.get(i).setStreamUpTime(args[i]);
                }
            }
        };
        timer.schedule (hourlyTask, 0l, 1000*60);
    }
}
