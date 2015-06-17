import org.jibble.pircbot.*;
import org.apache.commons.lang3.*;

public class WaheyBotMain {
    
    public static void main(String[] args) throws Exception {
        String oauth = utilities.readfile("oauth.txt"); // Create an oauth.txt file in the running directory
        WaheyBot bot = new WaheyBot();
        bot.setVerbose(true);
        bot.connect("irc.twitch.tv", 6667, oauth);
        bot.joinChannel("#nospimi99");
        //bot.sendRawLine("CAP REQ :twitch.tv/tags");
    }
}