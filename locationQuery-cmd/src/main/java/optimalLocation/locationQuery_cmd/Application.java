package optimalLocation.locationQuery_cmd;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import optimalLocation.configuration.YamlConfig;
import optimalLocation.core.OptimalLocationQuery;
import optimalLocation.query.domain.Candidate;
import optimalLocation.query.domain.LocationQueryResult;
import optimalLocation.yaml.YamlReadException;
import optimalLocation.yaml.YamlReader;

@ComponentScan(value="optimalLocation")
@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private OptimalLocationQuery optimalLocationQuery;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LocationQueryResult result = optimalLocationQuery.findBestLocation();
		
		Candidate bestCandidate = result.getFirstBestCandidate();
		System.out.printf("Latitude: %f\n", bestCandidate.getLatitude());
		System.out.printf("Longitude: %f\n", bestCandidate.getLongitude());
	}
	
	@Bean
	public YamlConfig getYamlConfig() throws YamlReadException {
		return YamlReader.readYaml(new File("settings.yaml"), YamlConfig.class);
	}
}
