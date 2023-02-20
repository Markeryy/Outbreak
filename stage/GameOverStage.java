package stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import game.GameTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

// gameover class
public class GameOverStage {
	private StackPane pane;
	private VBox vbox;
	private Scene scene;
	private GraphicsContext gc;
	private Canvas canvas;
	private ImageView view;
	public final static String FONT_PATH = "src/stage/resources/kenvector_future.ttf";
	public final Image bg = new Image("stage/resources/menu_background.png",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);

	// CONSTRUCTOR
	public GameOverStage(int num){
		this.pane = new StackPane();
		this.vbox = new VBox();
		this.scene = new Scene(pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.view = new ImageView();
		this.gc = canvas.getGraphicsContext2D();
		this.setProperties(num);
	}

	private void setProperties(int num){
		//this.gc.setFill(Color.WHITE);		//set font color of text
		//this.gc.fillRect(0,0,GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc.setTextAlign(TextAlignment.CENTER);
		this.gc.setTextBaseline(VPos.CENTER);
        this.vbox.setAlignment(Pos.CENTER);
        this.vbox.setSpacing(10);

        try {
			this.gc.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 50));
		} catch (FileNotFoundException e) {
			this.gc.setFont(Font.font("Verdana", 50));
		}

        this.gc.setFill(Color.CHARTREUSE); //set font color of text
		if (num == 1) { //for win
			this.gc.fillText("YOU WIN!\nKILLED " + GameTimer.fishKilled + " VIRUSES", Math.round(canvas.getWidth()/2), 100); //old y = 150
		} else { //for lose
			this.gc.fillText("GAME OVER!", Math.round(canvas.getWidth()/2), 150);
		}

		this.createBackground();
		this.createStartButton();
		this.createExitButton();
		this.pane.getChildren().addAll(this.canvas,vbox);
	}
	private void createBackground() {
		this.view.setImage(this.bg);
		this.pane.getChildren().add(this.view);
	}

	// create buttons
	private void createStartButton() {
		GameButton button = new GameButton("AGAIN!");
		this.vbox.getChildren().add(button);

		button.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				GameStage playGame = new GameStage();
				playGame.setStage(GameStage.stage);
			}
		});
	}
	private void createExitButton() {
		GameButton button = new GameButton("EXIT");
		this.vbox.getChildren().add(button);

		button.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.exit(0);
				Platform.exit();
			}
		});
	}

	// GETTER
	public Scene getScene(){
		return this.scene;
	}
}
