import java.awt.event.*;
import javax.swing.*;

public class Stopwatch {


    int timeElapsed = 0;
    int seconds = 0;
    int minutes = 0;
    int hours = 0;
    String stringSeconds = String.format("%02d", seconds);
    String stringMinutes = String.format("%02d", minutes);
    String stringHours = String.format("%02d", hours);

    String stringTime;

    Timer timer = new Timer(1000, new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            timeElapsed = timeElapsed + 1000;
            hours = (timeElapsed / 3600000);
            minutes = (timeElapsed / 60000) % 60;
            seconds = (timeElapsed / 1000) % 60;
            stringSeconds = String.format("%02d", seconds);
            stringMinutes = String.format("%02d", minutes);
            stringHours = String.format("%02d", hours);
            stringTime = stringHours + ":" + stringMinutes + ":" + stringSeconds;
        }
    });


    public String getStringTime() {
        return stringTime;
    }

    void start() {
        timer.start();
    }

    void stop() {
        timer.stop();
    }

    void reset() {
        timer.stop();
        timeElapsed = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;
        stringSeconds = String.format("%02d", seconds);
        stringMinutes = String.format("%02d", minutes);
        stringHours = String.format("%02d", hours);
        stringTime = stringHours + ":" + stringMinutes + ":" + stringSeconds;
        timer.start();
    }

}