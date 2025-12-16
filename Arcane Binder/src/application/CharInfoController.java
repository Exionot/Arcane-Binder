package application;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class CharInfoController {
	@FXML private ImageView charProfileImageView;
	@FXML private Label nameLabel, originLabel, factionLabel, imagesLabel, postersLabel;
	@FXML private FlowPane relationsContainer, imageContainer, posterContainer;
	
	private model.Character openedCharacter;
	
	public void setData(model.Character character, ScrollPane homePanel) {
		openedCharacter = character;
		charProfileImageView.setImage(new Image(new File(character.getProfileImg()).toURI().toString()));
		nameLabel.setText(character.getName());
		originLabel.setText("Origin: " +character.getOrigin());
		factionLabel.setText("Faction: " + character.getFaction());
		
		if (character.getRelations() != null) {
			for (model.Character relations : character.getRelations()) {
				CardController.displayCards("SmallCharCard", relationsContainer, CardController.class, controller -> {
					controller.setCardData(relations.getProfileImg(), relations.getName());
				}, () -> {
					CardController.openCharInfo(relations, homePanel);
				});
			}
		}
		imagesLabel.setVisible(false);
		if (character.getAllImages() != null) {
			imagesLabel.setVisible(true);
			for (String path : character.getAllImages()) {
				CardController.displayCards("GalleryCard", imageContainer, CardController.class, controller -> {
					controller.setCardData(path);
				}, () -> {
					CardController.openImage(path);
				});
			}
		}
		postersLabel.setVisible(false);
		if (character.getAllPosters() != null) {
			postersLabel.setVisible(true);
			for (String path : character.getAllPosters()) {
				CardController.displayCards("GalleryCard", posterContainer, CardController.class, controller -> {
					controller.setCardData(path);
				}, () -> {
					CardController.openImage(path);
				});
			}
		}
		
	}
	
	public void openCharSheet() {
		try {
			if (isObsidianRunning()) {
				Desktop.getDesktop().browse(openedCharacter.getSheetPath());
			}else {
				Desktop.getDesktop().browse(new URI(Main.VAULT_LINK));
				Thread.sleep(5000);
				Desktop.getDesktop().browse(openedCharacter.getSheetPath());
			}
        } catch (IOException | InterruptedException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	private Boolean isObsidianRunning() {
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
