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
	public static enum Property {
		FIRST_BOOT("app.first.boot"),
		USERNAME("app.username"),
		THEME("app.theme");
		
		private final String KEY;
		
		private Property(String key) {
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
	
	public void setConfig(Property configProperty, String value) throws IOException {
		Path path = Paths.get(application.Main.LOCAL_DIR + "config.properties");
		List<String> lines = Files.readAllLines(path);
		
		
		try (BufferedWriter writer = Files.newBufferedWriter(path);) {
	        for (String line : lines) {
	            if (line.startsWith(configProperty.getKey() + "=")) {
	                writer.write(configProperty.getKey() + "=" + value);
	                System.out.println(line);
	            } else {
	                writer.write(line);
	            }
	            writer.newLine();
	        }
	    } 
	}
	
	private void refreshConfig() {
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
					configProperty.getProperty("app.theme") == "light" ? false : 
						configProperty.getProperty("app.theme") == "dark" ? true : false
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
