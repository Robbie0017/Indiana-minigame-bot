package mini;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Main extends ListenerAdapter {
    private static JDA jda;
    private static JDABuilder builder;
    private static int messageCount = 0;
    private static int actionCount = (int) (Math.random() * (20 - 12) + 12);
    private static EmbedBuilder leader;
    private static String messageID;
    private static int rand;
    private static String answer;
    private static EmbedBuilder currentSpawn;
    private static String eventChannel = "776836285280682017";
    private static String adminID = "187287857763713027";
    private static Map<User, Integer> unsortedMap = new HashMap<User, Integer>();

    public static void main(String[] args) throws LoginException {
        String token = "DISCORD BOT ID HERE";
        builder = JDABuilder.createDefault(token);
        builder.setToken(token);
        builder.setActivity(Activity.playing("with Egypt"));
        builder.addEventListeners(new Main());
        jda = builder.build();
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        super.onMessageReceived(event);
        System.out.println(event.getAuthor().getAsTag() + " : " + event.getMessage().getContentStripped());

        //if (event.getChannel().getId().equals("784158051996270602") && !event.getAuthor().isBot()) {
        //    event.getChannel().sendMessage(event.getAuthor().getName() + " : " + event.getMessage().getContentDisplay() + ".").queue();
        //    event.getMessage().delete().queue();
        //}
        //if (event.getMessage().getContentDisplay().equals("test")) {
        //    event.getChannel().sendMessage("\uD83C\uDF7B").queue();
        //}
        if (!event.getAuthor().isBot()) {
            if (!unsortedMap.containsKey(event.getAuthor())) {
                if (!event.getAuthor().isBot()) {
                    unsortedMap.put(event.getAuthor(), 0);
                }
            }
            if (event.getMessage().getContentDisplay().toUpperCase().equals("INDIANA HELP")) {
                EmbedBuilder help = new EmbedBuilder();
                help.setColor(Color.cyan);
                help.setTitle("Help");
                help.addField("User commands", "", false);
                help.addField("-leaderboard", "Displays leaderboard", false);
                help.addField("-give ``mentioned user`` ``points to be given``", "Transfer specified points from your balance to the balance of the mentioned user", false);
                help.addField("", "", false);
                help.addField("Admin commands", "", false);
                help.addField("-add ``mentioned user`` ``points to be added``", "Adds specified amount of points to mentioned user", false);
                help.addField("-set ``mentioned user`` ``points to be added``", "Sets mentioned user's point count to specified value", false);
                help.addField("-reset", "Completely resets cached values for all users and resets leaderboard", false);
                help.addField("-messagecount ``count``", "Sets the number of messages needed to trigger event to specified value", false);
                help.addField("-eventchannel ``mentioned channel``", "Changes where events are sent to mentioned channel", false);
                event.getChannel().sendMessage(help.build()).queue();
            }
            if (event.getAuthor().getId().equals(adminID)) {
                if (event.getMessage().getContentDisplay().toUpperCase().startsWith("-ADD")) {
                    if (!unsortedMap.containsKey(event.getMessage().getMentionedUsers().get(0))) {
                        unsortedMap.put(event.getMessage().getMentionedUsers().get(0), 0);
                    }
                    User lucky = event.getMessage().getMentionedUsers().get(0);
                    String intValue = event.getMessage().getContentDisplay().replaceAll("[^0-9]", "");
                    int value = Integer.parseInt(intValue);
                    addPoint(lucky, value);
                    event.getChannel().sendMessage(value + " points were added to " + lucky.getAsTag()).queue();
                }
                if (event.getMessage().getContentDisplay().toUpperCase().startsWith("-SET")) {
                    if (!unsortedMap.containsKey(event.getMessage().getMentionedUsers().get(0))) {
                        unsortedMap.put(event.getMessage().getMentionedUsers().get(0), 0);
                    }
                    User lucky = event.getMessage().getMentionedUsers().get(0);
                    String intValue = event.getMessage().getContentDisplay().replaceAll("[^0-9]", "");
                    int value = Integer.parseInt(intValue);
                    setPoint(lucky, value);
                    event.getChannel().sendMessage(lucky.getAsTag() + "'s point value was set to " + value).queue();
                }
                if (event.getMessage().getContentDisplay().toUpperCase().equals("-RESET")) {
                    unsortedMap = new HashMap<User, Integer>();
                    event.getChannel().sendMessage("Leaderboard reset").queue();
                }
                //if (event.getMessage().getContentDisplay().toUpperCase().startsWith("-MESSAGECOUNT")) {
                //    String intValue = event.getMessage().getContentDisplay().replaceAll("[^0-9]", "");
                //    int value = Integer.parseInt(intValue);
                //    actionCount = value;
                //    event.getChannel().sendMessage("Message count to trigger events was set to " + value).queue();
                //}
                if (event.getMessage().getContentDisplay().toUpperCase().startsWith("-EVENTCHANNEL")) {
                    eventChannel = event.getMessage().getMentionedChannels().get(0).getId();
                    event.getChannel().sendMessage("Events will now trigger in " + event.getMessage().getMentionedChannels().get(0).getAsMention()).queue();
                }
            }
            if (event.getMessage().getContentDisplay().toUpperCase().startsWith("-GIVE")) {
                if (!unsortedMap.containsKey(event.getMessage().getMentionedUsers().get(0))) {
                    unsortedMap.put(event.getMessage().getMentionedUsers().get(0), 0);
                }
                User lucky = event.getMessage().getMentionedUsers().get(0);
                String intValue = event.getMessage().getContentDisplay().replaceAll("[^0-9]", "");
                int value = Integer.parseInt(intValue);
                if ((unsortedMap.get(event.getAuthor()) >= value) && (value > 0)) {
                    addPoint(lucky, value);
                    removePoint(event.getAuthor(), value);
                    event.getChannel().sendMessage(value + " points were transferred from " + event.getAuthor().getAsTag() + " to " + lucky.getAsTag()).queue();
                } else if (value == 0) {
                    event.getChannel().sendMessage("You can't transfer 0 points").queue();
                } else {
                    event.getChannel().sendMessage("Something went wrong").queue();
                }
            }
            if (event.getMessage().getContentDisplay().toUpperCase().equals("-LEADERBOARD")) {
                Map<User, Integer> sortedMap = sortByValue(unsortedMap);
                leader = new EmbedBuilder();
                leader.setColor(Color.CYAN);
                leader.setTitle("Leaderboard");
                printMap(sortedMap);
                event.getChannel().sendMessage(leader.build()).queue();
            }
            if (messageCount >= actionCount && jda.getRegisteredListeners().size() < 2) {
                messageCount = 0;
                rand = (int) (Math.random() * (10 - 1 + 1) + 1);
                jda.addEventListener(new Catch());
                currentSpawn = spawns(rand);
                jda.getTextChannelById(eventChannel).sendMessage(currentSpawn.build()).queue(message -> {
                    messageID = message.getId();
                });
            }
            messageCount++;
            actionCount = (int) (Math.random() * (20 - 12) + 12);
        }
    }

    private static EmbedBuilder spawns(int rand) {
        EmbedBuilder spawn = new EmbedBuilder();
        spawn.setColor(Color.cyan);
        switch (rand) {
            case 1:
                spawn.setTitle("A riddle!");
                spawn.setDescription("What comes out at night to take the sun's place?");
                spawn.setImage("https://cdn.dribbble.com/users/361038/screenshots/2820721/5_000.gif");
                break;
            case 2:
                spawn.setTitle("A riddle!");
                spawn.setDescription("What turns brown as fall comes around?");
                spawn.setImage("https://www.wallpaperup.com/uploads/wallpapers/2014/04/10/329109/eb96b031a9c1492dc7955d4d5653e428-700.jpg");
                break;
            case 3:
                spawn.setTitle("A puzzle!");
                int randRoman = (int) (Math.random() * (3999 - 1) + 1);
                spawn.setDescription(String.valueOf(randRoman));
                answer = IntegerToRomanNumeral(randRoman);
                spawn.setImage("https://www.unrv.com/images/400width/roman-numerals.png");
                break;
            case 4:
                spawn.setTitle("Pesty vines...Clear a path!");
                spawn.setDescription("[**chop chop**]");
                spawn.setImage("https://3.bp.blogspot.com/-2k0Argqcd5s/W6kMrwLxftI/AAAAAAAARRs/LGCedsxP6JUHyqb9suBvja5aX1wJOQIewCLcBGAs/s1600/forest.gif");
                break;
            case 5:
                spawn.setTitle("You found treasure!");
                spawn.setDescription("[**pawn**]");
                spawn.setImage("https://24.media.tumblr.com/tumblr_mbfqccQ60f1r2m60oo1_500.gif");
                break;
            case 6:
                spawn.setTitle("AGHH! MUMMY!");
                spawn.setDescription("[**run**]");
                spawn.setImage("https://media1.tenor.com/images/ab3807d044a442ffbb2d53eb6d1ca979/tenor.gif");
                break;
            case 7:
                spawn.setTitle("It's a trap!");
                spawn.setDescription("[**dodge**]");
                spawn.setImage("https://thumbs.gfycat.com/FaithfulGrayLemming-max-1mb.gif");
                break;
            case 8:
                spawn.setTitle("Careful, one of those is cursed!");
                spawn.setDescription("Chance to lose, gain, or gain double! [**take**]");
                spawn.setImage("https://media1.tenor.com/images/2c88294c3550ecaced1ad30042012aad/tenor.gif");
                break;
            case 9:
                spawn.setTitle("Yuck! Insects!");
                spawn.setDescription("[**swat**]");
                spawn.setImage("https://thumbs.gfycat.com/PleasedFlippantFairyfly-size_restricted.gif");
                break;
            case 10:
                spawn.setTitle("Hurry, before it closes!");
                spawn.setDescription("[**slide**]");
                spawn.setImage("https://thumbs.gfycat.com/DetailedSameIguana-size_restricted.gif");
                break;

        }
        return spawn;
    }

    public static String IntegerToRomanNumeral(int input) {
        if (input < 1 || input > 3999)
            return "Invalid Roman Number Value";
        String s = "";
        while (input >= 1000) {
            s += "M";
            input -= 1000;
        }
        while (input >= 900) {
            s += "CM";
            input -= 900;
        }
        while (input >= 500) {
            s += "D";
            input -= 500;
        }
        while (input >= 400) {
            s += "CD";
            input -= 400;
        }
        while (input >= 100) {
            s += "C";
            input -= 100;
        }
        while (input >= 90) {
            s += "XC";
            input -= 90;
        }
        while (input >= 50) {
            s += "L";
            input -= 50;
        }
        while (input >= 40) {
            s += "XL";
            input -= 40;
        }
        while (input >= 10) {
            s += "X";
            input -= 10;
        }
        while (input >= 9) {
            s += "IX";
            input -= 9;
        }
        while (input >= 5) {
            s += "V";
            input -= 5;
        }
        while (input >= 4) {
            s += "IV";
            input -= 4;
        }
        while (input >= 1) {
            s += "I";
            input -= 1;
        }
        return s;
    }

    private static Map<User, Integer> sortByValue(Map<User, Integer> unsortMap) {
        List<Map.Entry<User, Integer>> list =
                new LinkedList<Map.Entry<User, Integer>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<User, Integer>>() {
            public int compare(Map.Entry<User, Integer> o1,
                               Map.Entry<User, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<User, Integer> sortedMap = new LinkedHashMap<User, Integer>();
        for (Map.Entry<User, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static <K, V> void printMap(Map<K, V> map) {
        int i = 1;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            User user = (User) entry.getKey();
            int points = (int) entry.getValue();
            System.out.println("Key : " + entry.getKey()
                    + " Value : " + entry.getValue());
            switch (i) {
                case 1:
                    leader.appendDescription("\uD83D\uDC51 -> " + user.getAsMention() + " : " + points + "\n");
                    break;
                case 2:
                    leader.appendDescription("\uD83E\uDD48 -> " + user.getAsMention() + " : " + points + "\n");
                    break;
                case 3:
                    leader.appendDescription("\uD83E\uDD49 -> " + user.getAsMention() + " : " + points + "\n");
                    break;
                case 4:
                    leader.appendDescription("\u0034\uFE0F\u20E3 -> " + user.getAsMention() + " : " + points + "\n");
                    break;
                case 5:
                    leader.appendDescription("\u0035\uFE0F\u20E3 -> " + user.getAsMention() + " : " + points + "\n");
                    break;
                case 6:
                    leader.appendDescription("\u0036\uFE0F\u20E3 -> " + user.getAsMention() + " : " + points + "\n");
                    break;
                case 7:
                    leader.appendDescription("\u0037\uFE0F\u20E3 -> " + user.getAsMention() + " : " + points + "\n");
                    break;
                case 8:
                    leader.appendDescription("\u0038\uFE0F\u20E3 -> " + user.getAsMention() + " : " + points + "\n");
                    break;
                case 9:
                    leader.appendDescription("\u0039\uFE0F\u20E3 -> " + user.getAsMention() + " : " + points + "\n");
                    break;
                case 10:
                    leader.appendDescription("\uD83D\uDD1F -> " + user.getAsMention() + " : " + points + "\n");
                    break;
            }
            i++;
        }
    }

    public static JDABuilder getBuilder() {
        return builder;
    }

    public static JDA getJda() {
        return jda;
    }

    public static String getMessageID() {
        return messageID;
    }

    public static int getRand() {
        return rand;
    }

    public static EmbedBuilder getSpawn() {
        return currentSpawn;
    }

    public static void addPoint(User userID, int points) {
        unsortedMap.put(userID, unsortedMap.get(userID) + points);
    }

    public static void removePoint(User userID, int points) {
        unsortedMap.put(userID, unsortedMap.get(userID) - points);
    }

    public static void setPoint(User userID, int points) {
        unsortedMap.put(userID, points);
    }

    public static void setMessageCount(int newCount) {
        messageCount = newCount;
    }

    public static String getEventChannel() {
        return eventChannel;
    }

    public static String getAnswer() {
        return answer;
    }
}
