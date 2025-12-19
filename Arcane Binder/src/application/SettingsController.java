package application;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import dao.ConfigDAO.Property;
import dao.ConfigDAO.PropertyType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class SettingsController implements Initializable{
	@FXML private Label usernameLabel;
	@FXML private TextField usernameField;
	@FXML private ComboBox<String> themeComboBox;
	@FXML private ColorPicker accentColorPicker;
	@FXML private Button resetAccentButton, resetSettingsButton, applyButton;
	
	private Map<PropertyType, Property> defaultList, currentList, changeList;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dao.ConfigDAO.getDAO().refreshConfig();
		Main.updateConfig();
		usernameLabel.setText(Main.config.getName());
		usernameField.setText(Main.config.getName());
		themeComboBox.getItems().clear();
		themeComboBox.getItems().addAll("Light", "Dark");
		System.out.println(Main.config.isDarkMode());
		themeComboBox.getSelectionModel().select(Main.config.isDarkMode() ? 1 : 0);
		accentColorPicker.setValue(Color.web(Main.config.getAccentHex()));
		
		
		defaultList = new HashMap<>();
		defaultList.put(PropertyType.USERNAME, new Property(PropertyType.USERNAME, "User"));
		defaultList.put(PropertyType.THEME, new Property(PropertyType.THEME, "Light"));
		defaultList.put(PropertyType.ACCENT, new Property(PropertyType.ACCENT, "ffffff"));
		
		currentList = new HashMap<>(); 
		currentList.put(PropertyType.USERNAME, new Property(PropertyType.USERNAME, Main.config.getName()));
		currentList.put(PropertyType.THEME, new Property(PropertyType.THEME, Main.config.isDarkMode() ? "Dark" : "Light"));
		currentList.put(PropertyType.ACCENT, new Property(PropertyType.ACCENT, Main.config.getAccentHex()));
		
		changeList = new HashMap<>();
		changeList.putAll(currentList);
	}
	
	public void previewTheme() {
		boolean isCurrentDarkMode = Main.config.isDarkMode();
		String selected = themeComboBox.getSelectionModel().getSelectedItem();
		boolean isSelectedDarkMode = selected == "Light" ? false : true;
		
		if (!isCurrentDarkMode && isSelectedDarkMode) {
			changeList.replace(PropertyType.THEME, new Property(PropertyType.THEME, "Dark"));
		}
		else if (isCurrentDarkMode && !isSelectedDarkMode) {
			changeList.replace(PropertyType.THEME, new Property(PropertyType.THEME, "Light"));
		}
	}
	
	public void previewAccent(ActionEvent e) {
		Color selectedColor = accentColorPicker.getValue();
		String newHex = String.format("%02X%02X%02X",
		        (int)(selectedColor.getRed() * 255),
		        (int)(selectedColor.getGreen() * 255),
		        (int)(selectedColor.getBlue() * 255));
		changeList.replace(PropertyType.ACCENT, new Property(PropertyType.ACCENT, newHex));
	}
	
	public void resetAccent() {
		changeList.replace(PropertyType.ACCENT, defaultList.get(PropertyType.ACCENT));
		accentColorPicker.setValue(Color.web("#" + defaultList.get(PropertyType.ACCENT).getValue()));
	}
	
	public void resetSettings() {
		util.FancyAlert warning = new util.FancyAlert(AlertType.CONFIRMATION, "Reset Settings", "This will reset settings to its default values");
		
		if (warning.showAndWait().get() == ButtonType.OK) {
			for (Property defaultValue : defaultList.values()) {
				try {
					dao.ConfigDAO.getDAO().setConfig(defaultValue.getPropertyType(), defaultValue.getValue());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		initialize(null, null);
	}
	
	public void applyChanges(ActionEvent event) {
		changeList.replace(PropertyType.USERNAME, new Property(PropertyType.USERNAME, usernameField.getText().trim()));
		
		util.FancyAlert warning = new util.FancyAlert(AlertType.CONFIRMATION, "Apply Changes", "Apply Changes?");
		
		if (warning.showAndWait().get() == ButtonType.OK) {
			for (Property changedValue : changeList.values()) {
				if (changedValue.equals(currentList.get(changedValue.getPropertyType()))) {
					continue;
				}
				
				try {
					dao.ConfigDAO.getDAO().setConfig(changedValue.getPropertyType(), changedValue.getValue());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		initialize(null, null);
		Main.refreshCss(((Button)event.getSource()).getScene().getRoot());
	}
}
