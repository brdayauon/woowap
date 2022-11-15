package Audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import org.javacord.api.DiscordApi;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.audio.AudioSourceBase;

public class AudioSourcePlayer extends AudioSourceBase {

    private final AudioPlayer mAudioPlayer;
    private AudioFrame mLastFrame;

    public AudioSourcePlayer(DiscordApi api, AudioPlayer audioPlayer) {
        super(api);
        mAudioPlayer = audioPlayer;
    }
    @Override
    public byte[] getNextFrame() {
        if (mLastFrame == null) {
            return null;
        }
        return applyTransformers(mLastFrame.getData());
    }

    @Override
    public boolean hasFinished() {
        return false;
    }

    @Override
    public boolean hasNextFrame() {
        mLastFrame = mAudioPlayer.provide();
        return mLastFrame != null;
    }

    @Override
    public AudioSource copy() {
        return new AudioSourcePlayer(getApi(), mAudioPlayer);
    }
}
