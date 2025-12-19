package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DashboardController implements Initializable{
	@FXML private ScrollPane homePanel;
	@FXML private Button homeButton, charactersButton, galleryButton, settingsButton;
	@FXML private ImageView logoImageView;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logoImageView.setImage(new Image(new File(Main.RESOURCE_DIR + "temp-logo.png").toURI().toString()));
		
		ColorAdjust adjust = new ColorAdjust();
		adjust.setBrightness(0.2);
		adjust.setContrast(0);
		adjust.setSaturation(0);

		logoImageView.setEffect(adjust);
		
		loadPanel("Home", HomeController.class, controller -> {
		});
	}
	
	public void changePanel(ActionEvent e) {
		if (((Button)e.getSource()).getId() == homeButton.getId()) {
			loadPanel("Home", HomeController.class, controller -> {});
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
		else if (((Button)e.getSource()).getId() == settingsButton.getId()) {
			loadPanel("Settings", SettingsController.class, controller -> {
//				controller.refresh(homePanel);
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
