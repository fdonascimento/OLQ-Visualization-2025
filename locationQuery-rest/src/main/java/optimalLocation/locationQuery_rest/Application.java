package optimalLocation.locationQuery_rest;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import optimalLocation.configuration.YamlConfig;
import optimalLocation.yaml.YamlReadException;
import optimalLocation.yaml.YamlReader;

@ComponentScan(value="optimalLocation")
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public YamlConfig getYamlConfig() throws YamlReadException {
		return YamlReader.readYaml(new File("settings.yaml"), YamlConfig.class);
	}
}
