package stage;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

// game menu subscene class for help and about subscenes
public class GameMenuSubScene extends SubScene {

	//private final static String FONT_PATH = "src/stage/resources/kenvector_future.ttf";
	private final static String PANEL_PATH = "stage/resources/yellow_panel.png";
	public final static int WIDTH = 480;
	public final static int HEIGHT = 300;
	private final static int HIDDEN_X = 1000;
	private final static int FINAL_Y = 150;
	boolean hidden;

	// CONSTRUCTOR
	GameMenuSubScene() {
		super(new AnchorPane(), WIDTH, HEIGHT); // MARK: pass root and dimensions
		prefWidth(WIDTH);
		prefHeight(HEIGHT);
		this.hidden = true;

		// background panel (image,repeatx,repeaty,position,size) size = null, default
		BackgroundImage panel = new BackgroundImage(new Image(PANEL_PATH, WIDTH, HEIGHT, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

		AnchorPane root = (AnchorPane) this.getRoot(); // MARK: cast from Parent to AnchorPane
		root.setBackground(new Background(panel));

		setLayoutX(HIDDEN_X); // 280 for unhidden
		setLayoutY(FINAL_Y);  // 150 for unhidden
	}

	public void movePanel() {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.5));
		transition.setNode(this);  // MARK: move THIS panel

		if (this.hidden) {
			transition.setToX(-720); // MARK: subtract 720 to get 280 (x value for unhidden)
			this.hidden = false;
		} else {
			transition.setToX(0);
			this.hidden = true;
		}

		transition.play();
	}

	public AnchorPane getRootOfSubScene() {
		return (AnchorPane) this.getRoot();
	}

}
