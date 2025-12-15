package model;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import application.Main;

public class Character {
	private String name, origin, faction, profilePath;
	private List<model.Character> relations;
	private URI sheetPath;
	private List<String> imagePaths;
	private List<String> posterPaths;
	
	public Character(String name, String origin, String faction, URI sheetPath, String profilePath, List<String> imagePaths, List<String> posterPaths) {
		this.name = name;
		this.origin = origin == null ? "N/A" : origin;
		this.faction = faction == null ? "No active faction" : faction;
		this.sheetPath = sheetPath;
		this.profilePath = profilePath == null ? Main.RESOURCE_DIR + "default-profile.jpg" : Main.VAULT_DIR + profilePath;
		this.imagePaths = imagePaths == null ? new ArrayList<>() : imagePaths;
		this.posterPaths = posterPaths;
		
		if (this.imagePaths.size() > 0) {
			for (int i = 0; i < this.imagePaths.size(); i++) {
				this.imagePaths.set(i, Main.VAULT_DIR + this.imagePaths.get(i));
			}
		}
		if (profilePath != null) this.imagePaths.add(this.profilePath);
		if (posterPaths != null) {
			for (int i = 0; i < this.posterPaths.size(); i++) {
				this.posterPaths.set(i, Main.VAULT_DIR + this.posterPaths.get(i));
			}
		}
	}
	
	public String getName() { return this.name; }
	public String getOrigin() { return this.origin; }
	public String getFaction() { return this.faction; }
	public List<model.Character> getRelations() { return this.relations; }
	public void setRelations(List<model.Character> relations) { this.relations = relations; }
	public URI getSheetPath() { return this.sheetPath; }
	public String getProfileImg() { return this.profilePath; }
	public List<String> getAllImages() { return this.imagePaths; }
	public List<String> getAllPosters() { return this.posterPaths; }
}
