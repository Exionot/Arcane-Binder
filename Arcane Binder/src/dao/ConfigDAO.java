package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class ConfigDAO {
	public static class Property {
		private PropertyType propertyType;
		private String value;
		public Property(PropertyType propertyType, Object value) {
			this.propertyType = propertyType;
			this.value = value.toString();
		}
		
		public String getPropertyKey() { return this.propertyType.getKey(); }
		public PropertyType getPropertyType() { return this.propertyType; }
		public String getValue() { return this.value; }
	}
	
	public static enum PropertyType {
		FIRST_BOOT("app.first.boot"),
		USERNAME("app.username"),
		THEME("app.theme"),
		ACCENT("app.accent");
		
		private final String KEY;
		
		private PropertyType(String key) {
			this.KEY = key;
		}
		
		public String getKey() { return this.KEY; }
	}
	
	private static ConfigDAO dao;
	private static model.Config config;
	
	private ConfigDAO() {
		refreshConfig();
	}
	
	public static ConfigDAO getDAO() {
		if (ConfigDAO.dao == null) {
			dao = new ConfigDAO();
		}
		return dao;
	}
	
	public model.Config getConfig(){ return ConfigDAO.config; }
	
	public void setConfig(PropertyType configProperty, String value) throws IOException {
		Path path = Paths.get(application.Main.LOCAL_DIR + "config.properties");
		List<String> lines = Files.readAllLines(path);
		
		
		try (BufferedWriter writer = Files.newBufferedWriter(path);) {
	        for (String line : lines) {
	            if (line.startsWith(configProperty.getKey() + "=")) {
	                writer.write(configProperty.getKey() + "=" + value);
	            } else {
	                writer.write(line);
	            }
	            writer.newLine();
	        }
	    } 
	}
	
	public void refreshConfig() {
		ConfigDAO.config = fetchConfig();
	}
	
	private model.Config fetchConfig(){
		if (!doesConfigExists()) {
			createDefaultConfig();
		}
		File configFile = new File(application.Main.LOCAL_DIR + "config.properties");
		Properties configProperty = new Properties();
		try (FileInputStream input = new FileInputStream(configFile)){
			configProperty.load(input);
			
			model.Config configObj = new model.Config(
					Boolean.parseBoolean(configProperty.getProperty("app.first.boot")), 
					configProperty.getProperty("app.username"), 
					configProperty.getProperty("app.theme").equals("Light") ? false : 
						configProperty.getProperty("app.theme").equals("Dark") ? true : false,
					configProperty.getProperty("app.accent")
					);
			
			return configObj;
		}catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private boolean doesConfigExists() {
		return new File(application.Main.LOCAL_DIR + "config.properties").exists();
	}
	
	private void createDefaultConfig() {
		Properties property = new Properties();
		
		property.setProperty("app.first.boot", "true");
		property.setProperty("app.username", "user");
		property.setProperty("app.theme", "light");
		
		try (FileOutputStream output = new FileOutputStream(application.Main.LOCAL_DIR + "config.properties")){
			property.store(output, "AAAAAAA");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
