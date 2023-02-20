package stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

// panel text class (text in menu subscenes)
public class PanelText extends Label {

	public final static String FONT_PATH = "src/stage/resources/kenvector_future.ttf";

	public PanelText(String text) {
		setPrefWidth(GameMenuSubScene.WIDTH);
		setPrefHeight(GameMenuSubScene.HEIGHT);
		setPadding(new Insets(40,40,40,40));
		setText(text);
		setWrapText(true);

		// MARK: set font
		try {
			setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 14));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Verdana", 14));
		}
	}
}
