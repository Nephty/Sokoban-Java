package view;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import java.io.File;
import javafx.util.Duration;

/**
 * The <code>AudioPlayer</code> is a support for playing audio files. The simple methods and constructors make it
 * very easy to add sound effects to the game. By default, the volume is on 0.5 (equivalent to 50%) and the rate is
 * on 1 (equivalent to 100%). These values can be changed to get a higher or lower volume or play the sound
 * slower or faster. The default track (main theme music) is also by default on auto play if implemented with
 * the appropriate constructor. Other AudioPlayers (those you assign a file to) will not be on auto play by default.
 */
public class AudioPlayer {
    private String audioFile = System.getProperty("user.dir").concat("\\src\\main\\resources\\sound\\");

    private MediaPlayer mediaPlayer;
    private Media currMedia;

    private double volume = 0.5;

    private final String fileName = "beat.mp3";

    /**
     * Create a new <code>AudioPlayer</code> object with the main theme music as default music.
     * Automatically plays the music upon creation.
     */
    public AudioPlayer(){
        prepareMusic(fileName);
        play();
        loop();
    }

    /**
     * Create a new <code>AudioPlayer</code> object without automatically playing the music.
     * The object will remain silent if no playing method is applied.
     * @param fileName The name of the media file containing the sound
     */
    public AudioPlayer(String fileName) {
        prepareMusic(fileName);
    }

    /**
     * Return the <code>MediaPlayer</code> attribute.
     * @return The <code>MediaPlayer</code> attribute.
     */
    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }

    /**
     * Return the fileName attribute.
     * @return The fileName in use.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Create the <code>Media</code> object and the <code>MediaPlayer</code> object in use and sets the default
     * values for the rate and volume.
     * @param fileName The name of the file that will be used as the audio track
     */
    public void prepareMusic(String fileName) {
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
        currMedia = new Media(new File(audioFile.concat(fileName)).toURI().toString());
        mediaPlayer = new MediaPlayer(currMedia);
        mediaPlayer.setRate(1);
        mediaPlayer.setVolume(volume);
    }

    /**
     * Set the volume of the <code>MediaPlayer</code> in use.
     * @param volume_ The new volume
     * @throws NumberFormatException Exception thrown when the volume isn't a number between 0 and 1
     */
    public void setVolume(double volume) throws NumberFormatException {
        if (volume > 1 || volume < 0){
            throw new NumberFormatException("Volume must be between 0 and 1");
        }else {
            this.volume = volume;
            if (mediaPlayer != null){
                mediaPlayer.setVolume(volume);
            }
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