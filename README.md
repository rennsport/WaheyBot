# WaheyBot
A Java based Twitch bot; its main function is to count Waheys in chat (or any other keyword). To compile the bot (it may vary from OS to OS):
Windows:
<pre><code>gradle.bat</code></pre>

OS X:
<pre><code>./gradle.bat</code></pre>

To run the bot:
<pre><code>java -jar /path/to/waheybot-1.0-SNAPSHOT.jar -username “twitch username” -channels “channel1”,”channel2”
</code></pre>


### Dependencies (They are handled with gradle now):
  - [Apache Commons Lang]
  - [pIRCBot]

### Future Features:
1. Improve the !wr command
2. Implement "default" bot features such as custom per channel commands

### Added Features:
1. Multichannel Support

### Dropped Ideas:
1. Decided against moving from pIRCBot to pIRCBotX

[apache commons lang]:https://commons.apache.org/proper/commons-lang/
[pircbot]:http://www.jibble.org/pircbot.php
[pircbotx]:https://code.google.com/p/pircbotx/
