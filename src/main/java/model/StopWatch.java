package model;

import javafx.scene.text.Text;


import javax.swing.Timer;

/**
 * Creates a timer for the game. Every Second, we add 1000 to an <code>elapsedTime</code> variable and we set the timer
 * in the format : hh-mm-ss
 */
public class StopWatch {

    private int hours;
    private int minutes;
    private int seconds;
    private int elapsedTime;
    private final Text timeText;
    private final Timer timer;

    /**
     * Create the StopWatch in a Text node
     * @param timeText The Text node where we want to update the timer
     */
    public StopWatch(Text timeText){
        hours = 0;
        minutes = 0;
        seconds = 0;
        elapsedTime = 0;
        this.timeText = timeText;

        // Every second add 1000 to the Elapsed that
        timer = new Timer(1000, e -> add());
        timer.start();
    }

    /**
     * adds one second to the timer and Updates it
     */
    private void add(){
        elapsedTime += 1000;
        setTime();
    }

    /**
     * Updates the timer.
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
     * Stops the timer
     */
    public void stop(){
        timer.stop();
    }

    /**
     * Restarts the timer
     */
    public void restart(){
        stop();
        elapsedTime = 0;
        minutes = 0;
        hours = 0;
        seconds = 0;
        timeText.setText(toString());
        start();
    }

    /**
     * Starts the Timer
     */
    public void start(){
        timer.start();
    }
}