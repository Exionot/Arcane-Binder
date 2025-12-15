package application;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class CharactersController{
	@FXML private FlowPane cardPanel;
	
	private List<model.Character> allCharacters;

	public void refresh(ScrollPane homePanel) {
		try {
			allCharacters = new dao.CharacterDAO(Path.of(application.Main.CHAR_DIR)).getCharacters();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		
		cardPanel.getChildren().clear();
		for(model.Character character : allCharacters) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource(application.Main.FXML_DIR + "LargeCharCard.fxml"));
				VBox card = loader.load();
				CardController controller = loader.getController();
				controller.setCardData(character.getProfileImg(), character.getName());
				card.setPickOnBounds(true);
				card.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
					event.consume();
					System.out.println("Click");
					openCharInfo(character, homePanel);
				});

				cardPanel.getChildren().add(card);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void openCharInfo(model.Character character, ScrollPane homePanel) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(application.Main.FXML_DIR + "CharInfoView.fxml"));
			ScrollPane panel = loader.load();
			CharInfoController controller = loader.getController();
			controller.setData(character, homePanel);
			homePanel.setContent(panel);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
