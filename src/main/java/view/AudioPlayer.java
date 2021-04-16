package view;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.util.Duration;

import java.io.File;

public class AudioPlayer {
    private String audioFile = System.getProperty("user.dir").concat("\\src\\main\\resources\\sound\\");

    private MediaPlayer mediaPlayer;
    private Media currMedia;

    private double volume = 0.5;

    private String beat = "beat.mp3";

    /**
     * Default constructor which uses the default beat of the game
     */
    public AudioPlayer(){
        setMusic(beat);
        loop();
    }

    /**
     * AudioPlayer constructor with a special sound (not the beat)
     * @param fileName The name of the sound we want to set (Only in the sound directory)
     */
    public AudioPlayer(String fileName){
        prepareMusic(fileName);
    }

    /**
     * MediaPlayer accessor
     * @return The current MediaPlayer
     */
    public MediaPlayer getMediaPlayer(){
        return this.mediaPlayer;
    }

    /**
     * Beat accessor
     * @return The name of the default beat of the game
     */
    public String getBeat(){
        return beat;
    }

    /**
     * Change the music of the game
     * @param musicName The fileName of the sound we want to use (Only in the sound directory)
     */
    public void setMusic(String musicName){
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
        currMedia = new Media(new File(audioFile.concat(musicName)).toURI().toString());
        mediaPlayer = new MediaPlayer(currMedia);
        mediaPlayer.play();
        mediaPlayer.setRate(1);
        mediaPlayer.setVolume(volume);
    }

    /**
     * Change the music but doesn't play it
     * @param fileName The fileName of the sound we want to use (Only in the sound directory)
     */
    public void prepareMusic(String fileName){
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
        currMedia = new Media(new File(audioFile.concat(fileName)).toURI().toString());
        mediaPlayer = new MediaPlayer(currMedia);
        mediaPlayer.setRate(1);
        mediaPlayer.setVolume(volume);
    }


    /**
     * Change the volume of the mediaPlayer
     * @param volume A number between 0 and 1.
     */
    public void changeVolume(double volume){
        this.volume = volume;
        if (mediaPlayer != null){
            mediaPlayer.setVolume(volume);
        }
    }

    /**
     * restart the sound (used for the sound effects)
     */
    public void restart(){
        mediaPlayer.seek(Duration.ZERO);
    }

    /**
     * Loop the current media
     */
    public void loop(){
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });
    }

    /**
     * Stop the loop mode of the current media
     */
    public void stopLoop(){
        mediaPlayer.setOnEndOfMedia(null);
    }

}
