package mini;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class Catch extends ListenerAdapter {
    private static User capturer;
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        super.onMessageReceived(event);
        String eventChannel = Main.getEventChannel();
        switch (Main.getRand()) {
            case 1:
                if (event.getMessage().getContentDisplay().toUpperCase().contains("MOON")) {
                    capturer = event.getAuthor();
                    Main.getJda().getTextChannelById(eventChannel).editMessageById(Main.getMessageID(), Main.getSpawn().setDescription(capturer.getAsMention() + " has solved the riddle!").build()).queue();
                    Main.setMessageCount(0);
                    Main.addPoint(capturer, 1);
                    Main.getJda().removeEventListener(this);
                }
                break;
            case 2:
                if (event.getMessage().getContentDisplay().toUpperCase().contains("TREE") || event.getMessage().getContentDisplay().toUpperCase().contains("LEAVE") || event.getMessage().getContentDisplay().toUpperCase().contains("LEAF")) {
                    capturer = event.getAuthor();
                    Main.getJda().getTextChannelById(eventChannel).editMessageById(Main.getMessageID(), Main.getSpawn().setDescription(capturer.getAsMention() + " has solved the riddle! ").build()).queue();
                    Main.setMessageCount(0);
                    Main.addPoint(capturer, 1);
                    Main.getJda().removeEventListener(this);
                }
                break;
            case 3:
                if (event.getMessage().getContentDisplay().toUpperCase().equals(Main.getAnswer())) {
                    capturer = event.getAuthor();
                    Main.getJda().getTextChannelById(eventChannel).editMessageById(Main.getMessageID(), Main.getSpawn().setDescription(capturer.getAsMention() + " has cracked the code!").build()).queue();
                    Main.setMessageCount(0);
                    Main.addPoint(capturer, 1);
                    Main.getJda().removeEventListener(this);
                }
                break;
            case 4:
                if (event.getMessage().getContentDisplay().toUpperCase().contains("CHOP CHOP")) {
                    capturer = event.getAuthor();
                    Main.getJda().getTextChannelById(eventChannel).editMessageById(Main.getMessageID(), Main.getSpawn().setDescription(capturer.getAsMention() + " cleared away the vines!").build()).queue();
                    Main.setMessageCount(0);
                    Main.addPoint(capturer, 1);
                    Main.getJda().removeEventListener(this);
                }
                break;
            case 5:
                if (event.getMessage().getContentDisplay().toUpperCase().contains("PAWN")) {
                    capturer = event.getAuthor();
                    Main.getJda().getTextChannelById(eventChannel).editMessageById(Main.getMessageID(), Main.getSpawn().setDescription(capturer.getAsMention() + " made a handsome sum!").build()).queue();
                    Main.setMessageCount(0);
                    Main.addPoint(capturer, 1);
                    Main.getJda().removeEventListener(this);
                }
                break;
            case 6:
                if (event.getMessage().getContentDisplay().toUpperCase().contains("RUN")) {
                    capturer = event.getAuthor();
                    Main.getJda().getTextChannelById(eventChannel).editMessageById(Main.getMessageID(), Main.getSpawn().setDescription(capturer.getAsMention() + " escaped in time!").build()).queue();
                    Main.setMessageCount(0);
                    Main.addPoint(capturer, 1);
                    Main.getJda().removeEventListener(this);
                }
                break;
            case 7:
                if (event.getMessage().getContentDisplay().toUpperCase().contains("DODGE")) {
                    capturer = event.getAuthor();
                    Main.getJda().getTextChannelById(eventChannel).editMessageById(Main.getMessageID(), Main.getSpawn().setDescription(capturer.getAsMention() + ", Phew! Close one!").build()).queue();
                    Main.setMessageCount(0);
                    Main.addPoint(capturer, 1);
                    Main.getJda().removeEventListener(this);
                }
                break;
            case 8:
                if (event.getMessage().getContentDisplay().toUpperCase().contains("TAKE")) {
                    capturer = event.getAuthor();
                    int rando = (int) (Math.random() * (3 - 1 + 1) + 1);
                    if (rando == 1) {
                        Main.getJda().getTextChannelById(eventChannel).editMessageById(Main.getMessageID(), Main.getSpawn().setDescription(capturer.getAsMention() + " got away with it").build()).queue();
                        Main.setMessageCount(0);
                        Main.addPoint(capturer, 1);
                        Main.getJda().removeEventListener(this);
                    } else if (rando == 2) {
                        Main.getJda().getTextChannelById(eventChannel).editMessageById(Main.getMessageID(), Main.getSpawn().setDescription(capturer.getAsMention() + " got lucky! **(+2)**").build()).queue();
                        Main.setMessageCount(0);
                        Main.addPoint(capturer, 2);
                        Main.getJda().removeEventListener(this);
                    } else if (rando == 3) {
                        Main.getJda().getTextChannelById(eventChannel).editMessageById(Main.getMessageID(), Main.getSpawn().setDescription(capturer.getAsMention() + " fell under a curse **(-2)**").build()).queue();
                        Main.setMessageCount(0);
                        Main.removePoint(capturer, 2);
                        Main.getJda().removeEventListener(this);
                    }
                    Main.getJda().removeEventListener(this);
                }
                break;
            case 9:
                if (event.getMessage().getContentDisplay().toUpperCase().contains("SWAT")) {
                    capturer = event.getAuthor();
                    Main.getJda().getTextChannelById(eventChannel).editMessageById(Main.getMessageID(), Main.getSpawn().setDescription(capturer.getAsMention() + " swatted away those pesty bugs!").build()).queue();
                    Main.setMessageCount(0);
                    Main.addPoint(capturer, 1);
                    Main.getJda().removeEventListener(this);
                }
                break;
            case 10:
                if (event.getMessage().getContentDisplay().toUpperCase().contains("SLIDE")) {
                    capturer = event.getAuthor();
                    Main.getJda().getTextChannelById(eventChannel).editMessageById(Main.getMessageID(), Main.getSpawn().setDescription(capturer.getAsMention() + " made it just in time!").build()).queue();
                    Main.setMessageCount(0);
                    Main.addPoint(capturer, 1);
                    Main.getJda().removeEventListener(this);
                }
                break;
        }
    }
    public static User getCapturer() {
        return capturer;
    }
}
