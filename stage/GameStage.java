package stage;

import game.GameTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// game stage class
public class GameStage {

	public static final int WINDOW_HEIGHT = 500;
	public static final int WINDOW_WIDTH = 800;
	private Scene scene;
	public static Stage stage;

	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameTimer gametimer;

	private ImageView view;
	private Image gameBackground;
	private final static String GAME_BACKGROUND_PATH = "stage/resources/game_background.png";

	//the class constructor
	public GameStage() {
		this.root = new Group();
		this.scene = new Scene(this.root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.CADETBLUE);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.view = new ImageView();
		this.gameBackground = new Image(GameStage.GAME_BACKGROUND_PATH);
		this.view.setImage(this.gameBackground);

		//instantiate an animation timer
		this.gametimer = new GameTimer(this.gc,this.scene);
	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		GameStage.stage = stage;

		//set stage elements here
		this.root.getChildren().add(view);
		this.root.getChildren().add(canvas);

		GameStage.stage.setTitle("OUTBREAK!");
		GameStage.stage.getIcons().add(GameMenu.icon);	//add icon to stage

		GameStage.stage.setScene(this.scene); // MARK: show elements unique to this stage

		//invoke the start method of the animation timer
		this.gametimer.start();
		GameStage.stage.show();
	}

}

