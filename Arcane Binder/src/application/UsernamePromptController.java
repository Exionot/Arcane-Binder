package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class UsernamePromptController {
	@FXML private TextField usernameField;
	
	public void submitUsername(ActionEvent event) {
		String username = usernameField.getText().trim();
		
		if (username.isBlank()) {
			new util.FancyAlert(AlertType.ERROR, "Arcane Binder", "Name cannot be blank!").showAndWait();
			return;
		}
		util.FancyAlert confirmation = new util.FancyAlert(AlertType.CONFIRMATION, "Arcane Binder", "Are you sure with the username '" + username + "'?");
		
		if (confirmation.showAndWait().get() != ButtonType.OK) {
			return;
		}
		
		try {
			dao.ConfigDAO.getDAO().setConfig(dao.ConfigDAO.Property.USERNAME, username);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
