# WaheyBot
A Java based Twitch bot; its main function is to count Waheys in chat (or any other keyword). To compile the bot (it may vary from OS to OS):
<pre><code>javac -classpath pircbot.jar:commons-lang3-3.3.2.jar:java-json.jar:. *.java;</code></pre>
To run the bot:
<pre><code>java -classpath pircbot.jar:commons-lang3-3.3.2.jar:java-json.jar:. WaheyBotMain main *“channel to join” “second cannel to join” “nth channel to join”*</code></pre>


### Dependencies:
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
