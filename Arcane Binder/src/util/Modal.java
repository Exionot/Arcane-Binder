package util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Modal {
	private static Parent root;
	private static Scene scene;
	private Stage stage;
	
	public Modal(ActionEvent e, String sceneName, Runnable onClose) throws IOException {
		root = FXMLLoader.load(Modal.class.getResource(application.Main.FXML_DIR + sceneName + ".fxml"));
		stage = new Stage();
		scene = new Scene(root);
		final URL CSS = Modal.class.getResource(application.Main.CSS_DIR + sceneName + ".css");
		if (CSS != null) scene.getStylesheets().add(CSS.toExternalForm());
		scene.getStylesheets().add(application.Main.GLOBAL_CSS);
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner((Stage)((Node)e.getSource()).getScene().getWindow());
		stage.setResizable(false);
		stage.setTitle("Arcane Binder");
//		stage.getIcons().add(new Image(new File(Main.RESOURCE_DIR + "icon.png").toURI().toString()));
		stage.centerOnScreen();
		stage.setOnCloseRequest(event -> { onClose.run(); });
	}
	
	public Modal(Stage owner, String sceneName, Runnable onClose) throws IOException {
		root = FXMLLoader.load(Modal.class.getResource(application.Main.FXML_DIR + sceneName + ".fxml"));
		stage = new Stage();
		scene = new Scene(root);
		final URL CSS = Modal.class.getResource(application.Main.CSS_DIR + sceneName + ".css");
		if (CSS != null) scene.getStylesheets().add(CSS.toExternalForm());
		scene.getStylesheets().add(application.Main.GLOBAL_CSS);
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(owner.getScene().getWindow());
		stage.setResizable(false);
		stage.setTitle("Arcane Binder");
//		stage.getIcons().add(new Image(new File(Main.RESOURCE_DIR + "icon.png").toURI().toString()));
		stage.centerOnScreen();
		stage.setOnCloseRequest(event -> { onClose.run(); });
	}
	
	public void show() {
		stage.show();
	}
	
	public void showAndWait() {
		stage.showAndWait();
	}
}
