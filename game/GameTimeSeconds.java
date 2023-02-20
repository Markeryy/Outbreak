package game;

import java.util.Timer;
import java.util.TimerTask;

// timer class that will be used to count duration of player immortality (if vaccine is picked up)
public class GameTimeSeconds {

	public static int secondsPassed = 0;

	Timer timer = new Timer();
	TimerTask task = new TimerTask() { // declare and instantiate (used in anonymous classes)
		public void run() {
			if (secondsPassed != 3) {
				secondsPassed += 1;
			} else {
				secondsPassed = 0; // reset
				timer.cancel();
			}
		}
	};

	public void start() {
		timer.scheduleAtFixedRate(task, 1000, 1000); // start at 1 sec, increment 1 sec
	}

}
