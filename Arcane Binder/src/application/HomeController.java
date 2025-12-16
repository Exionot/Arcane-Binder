package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

public class HomeController {
	@FXML private ScrollPane homePanel;
	@FXML private Button homeButton, charactersButton, galleryButton;
	
	public void changePanel(ActionEvent e) {
		if (((Button)e.getSource()).getId() == homeButton.getId()) {
//			loadPanel("Home");
		}
		else if (((Button)e.getSource()).getId() == charactersButton.getId()) {
			loadPanel("Characters", CharactersController.class, controller -> {
				controller.refresh(homePanel);
			});
		}
		else if (((Button)e.getSource()).getId() == galleryButton.getId()) {
			loadPanel("Gallery", GalleryController.class, controller -> {
				controller.refresh(homePanel);
			});
		}
	}
	
	private <Controller> void loadPanel(String id, Class<Controller> controller, java.util.function.Consumer<Controller> controllerAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(application.Main.FXML_DIR + id + ".fxml"));
			ScrollPane charPanel = loader.load();
			Controller panelController = loader.getController();
			controllerAction.accept(panelController);
			charPanel.setPickOnBounds(true);
			homePanel.setContent(charPanel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
