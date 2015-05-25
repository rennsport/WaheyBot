import org.jibble.pircbot.*;
import org.apache.commons.lang3.*;

public class WaheyBotMain {
    
    public static void main(String[] args) throws Exception {
        
        // Now start our bot up.
        WaheyBot bot = new WaheyBot();
        
        // Enable debugging output.
        bot.setVerbose(true);
        
        // Connect to the IRC server.
        bot.connect("irc.twitch.tv", 6667, "oauth:6m77gofziu2nxcs5vk52lpui410rfx");

        // Join the #pircbot channel.
        bot.joinChannel("#911rennsport");
        //bot.joinChannel("#");

        bot.sendRawLine("CAP REQ :twitch.tv/tags");
    }
}