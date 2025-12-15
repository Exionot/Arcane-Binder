package application;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardController {
	@FXML private ImageView charImageView, cardImageView;
	@FXML private Label charName;
	
	public void setCardData(String imagePath, String name) {
		charImageView.setImage(new Image(new File(imagePath).toURI().toString()));
		charName.setText(name);
	}
	
	public void setCardData(String imagePath) {
		cardImageView.setImage(new Image(new File(imagePath).toURI().toString()));
	}
}
