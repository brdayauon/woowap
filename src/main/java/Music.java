import Audio.AudioSourcePlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Music implements MessageCreateListener {
    String prefix = Tokens.prefix;
    AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    AudioPlayer player = playerManager.createPlayer();

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String message = event.getMessageContent();

        playerManager.registerSourceManager(new YoutubeAudioSourceManager());
        AudioSource source = new AudioSourcePlayer(Main.bot, player);
        ServerVoiceChannel channel = event.getMessageAuthor().getConnectedVoiceChannel().get();
        channel.connect().thenAccept(audioConnection -> {
            audioConnection.setSelfDeafened(false);
            audioConnection.setAudioSource(source);
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });

        if (message.startsWith(prefix + "play")) {
            play(event);
        } else if (message.startsWith(prefix + "pause")) {
            pause(event);
        } else if (message.startsWith(prefix + "skip")) {
            skip(event);
        } else if (message.startsWith(prefix + "resume")) {
            resume(event);
        } else if (message.startsWith(prefix + "stop")) {
            stop(event);
        }
    }


    private void play(MessageCreateEvent event) {
        playerManager.registerSourceManager(new YoutubeAudioSourceManager());

        playerManager.loadItem("https://www.youtube.com/watch?v=FAucVNRx_mU", new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                player.playTrack(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                for (AudioTrack track : playlist.getTracks()) {
                    player.playTrack(track);
                }
            }

            @Override
            public void noMatches() {
                // Notify the user that we've got nothing
            }

            @Override
            public void loadFailed(FriendlyException throwable) {
                // Notify the user that everything exploded
            }
        });
    }

    private void pause(MessageCreateEvent event) {
        event.getChannel().sendMessage("Pausing: ");
        player.setPaused(true);
    }

    private void resume(MessageCreateEvent event) {
        event.getChannel().sendMessage("Resuming..");
        player.setPaused(false);
    }
    private void skip(MessageCreateEvent event) {
        event.getChannel().sendMessage("Skipping: ");
    }

    private void stop (MessageCreateEvent event) {
        event.getChannel().sendMessage("stopping..");
        player.stopTrack();
    }
}
