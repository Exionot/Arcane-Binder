package dao;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import application.Main;

public class CharacterDAO {
	
	private final Path charDir;

    public CharacterDAO(Path charDir) {
        this.charDir = charDir;
    }
	
	public List<Path> getFiles() throws IOException{
		return Files.list(charDir)
                .filter(Files::isRegularFile)
                .filter(p -> p.getFileName().toString().endsWith(".md"))
                .collect(Collectors.toList());
	}
	
	@SuppressWarnings("unchecked")
	public List<model.Character> getCharacters() throws IOException, URISyntaxException {
		List<Path> charPaths = getFiles();
		
		Map<String, model.Character> characters = new HashMap<>();
		for (Path path : charPaths) {
			String charName = (path.getFileName().toString()).replaceFirst("\\.[^.]+$", "");
			characters.put(
					charName,
					new model.Character(
							charName,
							util.YamlReader.getMapValue(util.YamlReader.getMap(path, "location"), "origin"),
							util.YamlReader.getMapValue(util.YamlReader.getMap(path, "faction"), "active"),
							new URI(Main.VAULT_LINK + charName.trim().replaceAll(" ", "%20")),
							util.YamlReader.getValue(path, "profileImage"),
							(List<String>) util.YamlReader.getList(path, "galleryImage"),
							(List<String>) util.YamlReader.getList(path, "posterImage")
							)
					);
			
		}
		
		for (Path path : charPaths) {
            String name = path.getFileName().toString().replaceFirst("\\.[^.]+$", "");
            model.Character character = characters.get(name);

            List<String> relationNames = (List<String>) util.YamlReader.getList(path, "relations"); 
            if (relationNames != null) {
            	List<model.Character> relations = relationNames.stream().map(characters::get).filter(Objects::nonNull).toList();
				character.setRelations(relations);
				System.out.println(name);
				System.out.println(relationNames);
			}
        }
		List<model.Character> charList = new ArrayList<>(characters.values());
		charList.sort(Comparator.comparing(model.Character::getName));
		
		return charList;
	}
}
