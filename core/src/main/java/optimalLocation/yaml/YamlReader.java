package optimalLocation.yaml;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YamlReader {

	public static <T> T readYaml(File file, Class<T> yamlClass) throws YamlReadException {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try {
			return mapper.readValue(file, yamlClass);
		} catch (IOException e) {
			throw new YamlReadException(e.getMessage(), e);
		}
	}
}
