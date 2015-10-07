# WaheyBot
A Java based Twitch bot; its main function is to count Waheys in chat (or any other keyword).

To compile the bot (it may vary from OS to OS):

Windows:

`gradlew build`

OS X:

`./gradlew build\`

To run the bot:

```java -jar /path/to/waheybot-1.0-SNAPSHOT.jar -username “twitch username” -channels “channel1”,”channel2”,”channeln”```



### Dependencies (They are handled with gradle now):
  - [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/)
  - [pIRCBot](http://www.jibble.org/pircbot.php)

### Future Features:
1. Improve the !wr command
2. Implement "default" bot features such as custom per channel commands

### Added Features:
1. Multichannel Support

### Dropped Ideas:
1. Decided against moving from pIRCBot to pIRCBotX
