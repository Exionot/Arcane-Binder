package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class YamlReader {
	
	public static String getValue(Path path, String identifier) throws IOException {
		Object yamlValue = readYaml(path, identifier);
		return yamlValue == null ? null : yamlValue.toString();
	}
	
	public static List<?> getList(Path path, String identifier) throws IOException {
		Object yamlValue = readYaml(path, identifier);
		return (List<?>) yamlValue;
	}
	
	public static Map<?,?> getMap(Path path, String identifier) throws IOException {
		Object yamlValue = readYaml(path, identifier);
		return (Map<?,?>) yamlValue;
	}
	
	public static String getMapValue (Map<?,?> map, String identifier) throws IOException {
		return map.get(identifier) == null ? null : map.get(identifier).toString();
	}
	
	private static Object readYaml(Path path, String identifier) throws IOException {
		List<String> lines = Files.readAllLines(path);

        boolean inYaml = false;
        StringBuilder yamlContent = new StringBuilder();

        for (String line : lines) {
            if (line.trim().equals("---")) {
                if (!inYaml) {
                    inYaml = true;
                } else {
                    break;
                }
            } else if (inYaml) {
                yamlContent.append(line).append("\n");
            }
        }

        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(yamlContent.toString());

        Object obj = data.get(identifier);
        
        return obj;
	}
}
