import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Main {

    public static DiscordApi bot;

    public static void main(String [] args)  {
         bot = new DiscordApiBuilder()
                .setAllIntents()
                .addListener(new Ping())
                .addListener(new Music())
                .setToken(Tokens.TOKEN)
                .login().join();
        System.out.println(bot.createBotInvite());

    }
}