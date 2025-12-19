package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class HomeController implements Initializable{
	@FXML private Label welcomeLabel;
	
	private String username = Main.config.getName();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		welcomeLabel.setText(username);
	}
	
}
