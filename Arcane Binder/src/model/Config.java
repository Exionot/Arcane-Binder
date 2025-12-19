package model;

public class Config {
	private String name, accentHex;
	private boolean isFirstBoot, isDarkMode;
	
	public Config(boolean isFirstBoot, String name, boolean isDarkMode, String accentHex) {
		this.isFirstBoot = isFirstBoot;
		this.name = name;
		this.isDarkMode = isDarkMode;
		this.accentHex = "#" + accentHex;
	}
	
	public boolean isFirstBoot() { return this.isFirstBoot; }
	public String getName() { return this.name; }
	public boolean isDarkMode() { return this.isDarkMode; }
	public String getAccentHex() { return this.accentHex; }
}
