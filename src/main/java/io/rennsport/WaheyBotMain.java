package io.rennsport;

import org.jibble.pircbot.*;
import org.apache.commons.lang3.*;
import java.util.*;

public class WaheyBotMain {

    private static String[] channels;

    public static String[] getChannels()
    {
        return channels;
    }

    public static void main(final String[] args) throws Exception {

        String oauth = utilities.readFile("oauth.txt"); //Create an oauth.txt file in the running directory

        final ArrayList<WaheyBot> bots = new ArrayList<WaheyBot>();

        if(args.length == 0)
        {
            // No channels specified, return and warn the user.
            System.out.println("[ERROR]: No arguments specified!");
        }

        // The twitch username to login to with the bot.
        String username = "";

        channels = null;

        for(int i = 0; i < args.length; i++)
        {
            String arg = args[i].toLowerCase();

            if(arg.startsWith("-"))
            {
                switch(arg.replace("-", "")) // Replace the first dash ONLY.
                {
                    case "username":
                    {
                        // The next argument is definitely our username.

                        // Grab and skip the next.
                        username = args[i + 1];
                        i++;
                        break;
                    }
                    case "oauth":
                    {
                        // The next argument is definitely our oauth
                        // If this argument exists then we replace the existing oauth.
                        oauth = args[i + 1];
                        // Make sure to skip the next argument
                        i++;
                        break;
                    }
                    case "channels":
                    {
                        // The next argument is definitely the list of channels to connect to.
                        // TODO: strip the channel input of invalid characters....
                        channels = args[i+1].split(","); // Get a comma separated list of channels.
                        // Make sure to skip the next argument
                        i++;
                        break;
                    }
                    default:
                    {
                        System.out.println("Invalid flag: -" + arg);
                    }
                }
            }
            else
            {
                System.out.println("Invalid argument: " + arg);
            }
        }

        boolean shouldQuit = false;

        if(username.isEmpty())
        {
            // Can't login without a username..
            System.out.println("[WARN] No username found!\n"
                            + "[WARN] Please use the flag -username <username> to specify it.");
            shouldQuit = true;
        }

        if(oauth.isEmpty())
        {
            // Can't login without a username..
            System.out.println("[WARN] No oauth found!\n"
                            + "[WARN] Please use the flag -oauth <oauth> to specify it"
                            + " or create oauth.txt in the running directory.");
            shouldQuit = true;
        }

        if(channels == null)
        {
            // Can't login without a username..
            System.out.println("[WARN] No oauth found!\n"
                            + "[WARN] Please use the flag -oauth <oauth> to specify it"
                            + " or create oauth.txt in the running directory.");
            shouldQuit = true;
        }

        if(shouldQuit)
        {
            System.out.println("[INFO] Stopping WaheyBot...");
            return;
        }

        for(String channel: channels)
        {
            WaheyBot bot = new WaheyBot(username);
            //WaheyBot bot2 = new WaheyBot();
            bot.setVerbose(true);
            bot.connect("irc.twitch.tv", 6667, oauth);
            bot.joinChannel("#" + channel);
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

        TimerTask minuteTask = new TimerTask() {
            @Override
            public void run () {
                System.out.println("[INFO] Reloading stream args.");
                for(int i = 0; i < bots.size(); i++)
                {
                    bots.get(i).setGame(channels[i]);
                    bots.get(i).setTitle(channels[i]);
                    bots.get(i).setPartner(channels[i]);
                    bots.get(i).setStreamUpTime(channels[i]);
                }
            }
        };

        // TimerTask fifteenMinutes = new TimerTask() {
        //     @Override
        //     public void run () {
        //         for(int i = 0; i < bots.size(); i++)
        //         {
        //             bots.get(i).sendMessage('#' + channels[i], "Hey there! We have a lot of new people in here because of the front page, so mods are going to be more strict with purges/timeouts/bans so make sure to check out the rules below! If you're on mobile, tap the screen and press the 'i' button to see them!");
        //         }
        //     }
        // };
        //
        // timer.schedule (minuteTask, 0l, 1000*60);
        // timer.schedule (fifteenMinutes, 0l, 1000*31);
    }
}
