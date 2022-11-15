import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Ping implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        System.out.println("message content is: " + event.getMessageContent());

        if (event.getMessageContent().equals("")) {
            System.out.println("ITS EMPTY");
        }
        if (event.getMessageContent().equalsIgnoreCase("!ping")) {
            event.getChannel().sendMessage("Pong!");
            System.out.println("pinging");
        }
        System.out.println("eventCreated " + event.getChannel() + " " + event.getMessageContent() + " test");
    }
}