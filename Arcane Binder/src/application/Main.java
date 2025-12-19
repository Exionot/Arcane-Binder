package application;
	
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;


public class Main extends Application {
	private Parent root;
	
	public static final String FXML_DIR = "/fxml/";
	public static final String CSS_DIR = "/css/";
	public static final String GLOBAL_CSS = CSS_DIR + "application.css";
	public static final String RESOURCE_DIR = getRootDir("resource");
	public static final String VAULT_DIR = "C:/Exionot/Vaults/World Building/"; 
	public static final String VAULT_LINK = "obsidian://open?World%20Building=Vaults&file=";
	public static final String CHAR_DIR = VAULT_DIR + "Arcane-Bound/Worlds Collide/01 Characters/";
	public static final String LOCAL_DIR;
	static {
	    try {
	        String userHome = System.getProperty("user.home");
	        File uploadDir = new File(userHome, "Arcane Binder");
	        if (!uploadDir.exists()) {
	            uploadDir.mkdirs();
	        }
	        LOCAL_DIR = uploadDir.getAbsolutePath() + File.separator;
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to initialize upload directory", e);
	    }
	}
	public static model.Config config = dao.ConfigDAO.getDAO().getConfig();
	
	public static final String getRootDir(String folderName) {
		try {
	         File rootDir = new File(Main.class.getProtectionDomain()
	                              .getCodeSource()
	                              .getLocation()
	                              .toURI())
	                         .getParentFile();
	        File folderDir = new File(rootDir, folderName);
	        if (!folderDir.exists()) {
	        	folderDir.mkdirs(); 
	        }
	        return folderDir.getAbsolutePath() + File.separator;
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to initialize folder directory", e);
	    }
	}
	
	public static void updateConfig() {
		config = dao.ConfigDAO.getDAO().getConfig();
	}
	
	public static void refreshCss(Parent root) {
		List<String> stylesList = new ArrayList<>();
		boolean isDarkMode = config.isDarkMode();
		
		if (isDarkMode) {
			stylesList.add("-primary: -dark-mode-primary;"
					+ "	-secondary: -dark-mode-secondary;"
					+ "	-text-primary: -dark-mode-text-primary;");
		}else {
			stylesList.add("-primary: -light-mode-primary;"
					+ "	-secondary: -light-mode-secondary;"
					+ "	-text-primary: -light-mode-text-primary;");
			
		}
		
		stylesList.add("-accent-color: " + config.getAccentHex() + ";");
		
		Color textColor = util.TextColorChecker.getTextColor(Color.web(config.getAccentHex()));
		if (textColor == Color.BLACK) stylesList.add("-accent-text: -light-mode-text-primary;");
		else
		if (textColor == Color.WHITE) stylesList.add("-accent-text: -dark-mode-text-primary;");
		
		StringBuilder builder = new StringBuilder();
		for (String styles : stylesList) {
			builder.append(styles);
		}
		
		root.setStyle(builder.toString());
	}
	
	public Parent getRoot() { return this.root; }
	
	@Override
	public void start(Stage primaryStage) {
		try {
			root = FXMLLoader.load(getClass().getResource(FXML_DIR+"Dashboard.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(GLOBAL_CSS);
			primaryStage.setScene(scene);
			
			if (config.isFirstBoot()) {
				util.Modal namePrompt = new util.Modal(primaryStage, "UsernamePrompt", () -> {
					System.exit(0);
					return;
				});
				namePrompt.showAndWait();
				dao.ConfigDAO.getDAO().setConfig(dao.ConfigDAO.PropertyType.FIRST_BOOT, "false");
			}
			
			refreshCss(root);
			primaryStage.setResizable(false);
			primaryStage.centerOnScreen();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
