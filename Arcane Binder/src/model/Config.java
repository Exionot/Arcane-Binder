package model;

public class Config {
	private String name;
	private boolean isFirstBoot, isDarkMode;
	
	public Config(boolean isFirstBoot, String name, boolean isDarkMode) {
		this.isFirstBoot = isFirstBoot;
		this.name = name;
		this.isDarkMode = isDarkMode;
	}
	
	public boolean isFirstBoot() { return this.isFirstBoot; }
	public String getName() { return this.name; }
	public boolean isDarkMode() { return this.isDarkMode; }
}
