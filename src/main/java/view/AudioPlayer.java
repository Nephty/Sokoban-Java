package view;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import java.io.File;

import javafx.util.Duration;
import model.FileGetter;

/**
 * The <code>AudioPlayer</code> is a support for playing audio files. The simple methods and constructors make it
 * very easy to add sound effects to the game. By default, the volume is on 0.5 (equivalent to 50%) and the rate is
 * on 1 (equivalent to 100%). These values can be changed to get a higher or lower volume or play the sound
 * slower or faster. The default track (main theme music) is also by default on auto play if implemented with
 * the appropriate constructor. Other AudioPlayers (those you assign a file to) will not be on auto play by default.
 */
public class AudioPlayer {
    private static final String audioFile = FileGetter.directory("sound");
    public static final Media crashFile = getMediaFile("crash.mp3");
    public static final Media pushFile = getMediaFile("push.mp3");
    public static  final Media beatFile = getMediaFile("beat.mp3");
    public static final Media secretFile = getMediaFile("secret.mp3");

    private MediaPlayer mediaPlayer;
    private Media currMedia;


    private double volume = 0.5;


    public static final byte MINIMUM_VOLUME = 0;
    public static final byte MAXIMUM_VOLUME = 1;

    private static Media getMediaFile(String audioName){
        return new Media(new File(audioFile.concat(audioName)).toURI().toString());
    }

    /**
     * Create a new <code>AudioPlayer</code> object with the main theme music as default music.
     * Automatically plays the music upon creation.
     */
    public AudioPlayer(){
        prepareMusic(beatFile);
        play();
        loop();
    }

    /**
     * Create the <code>Media</code> object and the <code>MediaPlayer</code> object in use and sets the default
     * values for the rate and volume.
     * @param audio The Media object that will be used as the audio track.
     */
    public void prepareMusic(Media audio) {
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
        currMedia = audio;
        mediaPlayer = new MediaPlayer(currMedia);
        mediaPlayer.setRate(1);
        mediaPlayer.setVolume(volume);
    }

    /**
     * Plays an effect (a short sound)
     * @param audio The effect we want to play.
     */
    public void playEffect(Media audio){
        if (currMedia != audio){
            prepareMusic(audio);
        }
        play();
        restart();
    }

    /**
     * Set the volume of the <code>MediaPlayer</code> in use.
     * @param volume The new volume
     */
    public void setVolume(double volume)  {
        if (volume > MAXIMUM_VOLUME) {
            this.volume = 1;
        } else if (volume < MINIMUM_VOLUME){
            this.volume = 0;
        } else {
            this.volume = volume;
        }
        if (mediaPlayer != null){
            mediaPlayer.setVolume(volume);
        }
    }

    /**
     * Restart the current audio track.
     */
    public void restart(){
        mediaPlayer.seek(Duration.ZERO);
    }

    /**
     * Resume the current audio track.
     */
    public void play(){
        mediaPlayer.play();
    }
    /**
     * Loop the current media
     */
    public void loop(){
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });
    }

    /**
     * Stop the loop mode of the current media
     */
    public void stopLoop(){
        mediaPlayer.setOnEndOfMedia(null);
    }
}