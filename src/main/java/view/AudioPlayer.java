package view;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import java.io.File;

public class AudioPlayer {
    private String audioFile = System.getProperty("user.dir").concat("\\src\\main\\resources\\sound\\");

    private MediaPlayer mediaPlayer;
    private String status;
    private Media currMedia;

    private double volume = 0.5;

    private String beat = "beat.mp3";

    public AudioPlayer(){
        setMusic(beat);
        mediaPlayer.setAutoPlay(true);
    }

    public AudioPlayer(String fileName){
        prepareMusic(fileName);
    }

    public MediaPlayer getMediaPlayer(){
        return this.mediaPlayer;
    }

    public String getBeat(){
        return beat;
    }

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

    public void prepareMusic(String fileName){
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
        currMedia = new Media(new File(audioFile.concat(fileName)).toURI().toString());
        mediaPlayer = new MediaPlayer(currMedia);
        mediaPlayer.setRate(1);
        mediaPlayer.setVolume(volume);
    }


    public void changeVolume(double volume) throws NumberFormatException{
        if (volume > 1 || volume < 0){
            throw new NumberFormatException("Volume must be between 0 and 1");
        }else {
            this.volume = volume;
            if (mediaPlayer != null){
                mediaPlayer.setVolume(volume);
            }
        }
    }

    public void restart(){
        mediaPlayer = new MediaPlayer(currMedia);
        mediaPlayer.setRate(1);
        mediaPlayer.setVolume(volume);

    }

}
