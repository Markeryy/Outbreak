package stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

// customized button class for menu and gameover screen
public class GameButton extends Button {

	private final String FONT_PATH = "src/stage/resources/kenvector_future.ttf";

	// image (190px x 45px) 4 pixels smaller than released image
	private final String BUTTON_PRESSED_IMG = "-fx-background-color: transparent;"
			+ " -fx-background-image: url('/stage/resources/yellow_button01.png')";

	// image (190px x 49px)
	private final String BUTTON_RELEASED_IMG = "-fx-background-color: transparent;"
			+ " -fx-background-image: url('/stage/resources/yellow_button00.png')";

	// CONSTRUCTOR
	GameButton(String text) {
		this.setText(text);
		this.initButton();
		setPrefWidth(190); // MARK: image width (190 px)
		setPrefHeight(49); // MARK: image height (49 px)
		setStyle(BUTTON_RELEASED_IMG);
		this.buttonListeners();
	}

	private void initButton() {
		// setting font
		try {
			setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
		} catch (FileNotFoundException e) {
			setFont(Font.loadFont("Verdana", 23));  // MARK: if font cannot be loaded
			e.printStackTrace();
		}
	}

	// changing the image based on button state
	private void buttonPressed() {
		setStyle(BUTTON_PRESSED_IMG);
		setPrefHeight(45);
		setLayoutY(getLayoutY() + 4); // due to difference in image size of button
	}
	private void buttonReleased() {
		setStyle(BUTTON_RELEASED_IMG);
		setPrefHeight(49);
		setLayoutY(getLayoutY() - 4); // MARK: due to difference in image size of button
	}

	// button listener
	private void buttonListeners() {
		// MARK: when clicked
		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton().equals(MouseButton.PRIMARY)) { // MARK: left clicked (PRIMARY)
					buttonPressed();
				}
			}
		});

		// MARK: when released
		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton().equals(MouseButton.PRIMARY)) {
					buttonReleased();
				}
			}
		});

		// MARK: when hovering over the button
		setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setEffect(new DropShadow()); // MARK: add a drop shadow
			}
		});

		// MARK: when not hovering
		setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setEffect(null);  // MARK: remove drop shadow
			}
		});
	}
}
