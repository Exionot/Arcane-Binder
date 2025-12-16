package application;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class CardController {
	@FXML private ImageView charImageView, cardImageView;
	@FXML private Label charName;
	
	public void setCardData(String imagePath, String name) {
		charImageView.setImage(new Image(new File(imagePath).toURI().toString()));
		charName.setText(name);
	}
	
	public void setCardData(String imagePath) {
		cardImageView.setImage(new Image(new File(imagePath).toURI().toString()));
	}
	
	public static <Controller> void displayCards(String fxml, FlowPane container, Class<Controller> controllerClass, java.util.function.Consumer<Controller> controllerAction, Runnable onClick) {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource(application.Main.FXML_DIR + fxml + ".fxml"));
			VBox card = loader.load();
			Controller controller = loader.getController();
			if (controllerAction != null) {
	            controllerAction.accept(controller);
	        }
			card.setPickOnBounds(true);
			card.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				event.consume();
				onClick.run();
			});

			container.getChildren().add(card);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
	
	public static void openImage(String path) {
		try {
			Desktop.getDesktop().open(
			        new File(path)
			    );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void openCharInfo(model.Character character, ScrollPane homePanel) {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource(application.Main.FXML_DIR + "CharInfoView.fxml"));
			ScrollPane panel = loader.load();
			CharInfoController controller = loader.getController();
			controller.setData(character, homePanel);
			homePanel.setContent(panel);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public static Boolean isObsidianRunning() {
		String processName = "Obsidian.exe";
		try {
            Process process = Runtime.getRuntime().exec(
                new String[] {"cmd", "/c", "tasklist /FI \"IMAGENAME eq " + processName + "\""});
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(processName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
	}
}
