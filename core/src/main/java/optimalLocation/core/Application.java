package optimalLocation.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import optimalLocation.query.Candidate;

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
		Candidate candidate = optimalLocationQuery.findBestLocation();
		
		System.out.printf("Latitude: %f\n", candidate.getLatitude());
		System.out.printf("Longitude: %f\n", candidate.getLongitude());
	}
}
