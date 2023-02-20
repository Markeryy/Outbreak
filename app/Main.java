package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import stage.GameMenu;

// main class
public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage){
		// launch main menu
		GameMenu menu = new GameMenu();
		menu.setStage(stage);
		closeWindowListener(stage);  // listener for closing window
	}

	private void closeWindowListener(Stage stage) {
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent e) {
		    	System.exit(0);
		    	Platform.exit();  // closes threads
		    }
		});
	}

}
