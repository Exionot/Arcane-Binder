package application;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import dao.CharacterDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

public class GalleryController {
	@FXML private FlowPane imageContainer, posterContainer;
	
	private List<model.Character> allCharacters;
	
	public void refresh(ScrollPane homePanel) {
		try {
			allCharacters = CharacterDAO.getDao().getAllCharacters();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		
		imageContainer.getChildren().clear();
		posterContainer.getChildren().clear();
		for(model.Character character : allCharacters) {
			for (String path : character.getAllImages()) {
				CardController.displayCards("GalleryCard", imageContainer, CardController.class, controller -> {
					controller.setCardData(path);
				}, () -> {
					CardController.openImage(path);
				});
			}
			
			if (character.getAllPosters() != null) {
				for (String path : character.getAllPosters()) {
					CardController.displayCards("GalleryCard", posterContainer, CardController.class, controller -> {
						controller.setCardData(path);
					}, () -> {
						CardController.openImage(path);
					});
				}
			}
		}
	}
}
