package model;

import javafx.scene.text.Text;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;


public class StopWatch {

    private int hours;
    private int minutes;
    private int seconds;
    private int elapsedTime;
    private Text timeText;
    private Timer timer;

    /**
     * Create the StopWatch
     * @param timeText The Text where we want to update the timer
     */
    public StopWatch(Text timeText){
        hours = 0;
        minutes = 0;
        seconds = 0;
        elapsedTime = 0;
        this.timeText = timeText;

        timer = new Timer(1000, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                add();
            }
        });
        timer.start();
    }

    /**
     * add one second to the timer
     */
    private void add(){
        elapsedTime += 1000;
        setTime();
    }

    /**
     * Set the timer and change the Text
     */
    private void setTime(){
        hours = (elapsedTime/3600000);
        minutes = (elapsedTime/60000) % 60;
        seconds = (elapsedTime/1000) % 60;
        timeText.setText(toString());
    }


    /**
     * @return The timer in the good String format (hh:mm:ss)
     */
    public String toString(){
        String res = "";
        res += String.format("%02d", hours) + ":";
        res += String.format("%02d", minutes) + ":";
        res += String.format("%02d", seconds);
        return res;
    }

    /**
     * Stop the timer
     */
    public void stop(){
        timer.stop();
    }

    /**
     * Restart the timer
     */
    public void restart(){
        elapsedTime = 0;
        minutes = 0;
        hours = 0;
        seconds = 0;
        timeText.setText(toString());
        start();
    }

    /**
     * Start the Timer
     */
    public void start(){
        timer.start();
    }
}
