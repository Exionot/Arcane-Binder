package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

public class HomeController {
	@FXML private ScrollPane homePanel;
	@FXML private Button charactersButton;
	
	public void changePanel(ActionEvent e) {
		if (((Button)e.getSource()).getId() == charactersButton.getId()) {
			loadPanel("Characters");
		}
	}
	
	private void loadPanel(String id) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(application.Main.FXML_DIR + id + ".fxml"));
			ScrollPane charPanel = loader.load();
			CharactersController controller = loader.getController();
			controller.refresh(homePanel);
			charPanel.setPickOnBounds(true);
			homePanel.setContent(charPanel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
