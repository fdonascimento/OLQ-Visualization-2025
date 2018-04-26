package optimalLocation.core;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import optimalLocation.query.Candidate;

public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DependencyInjectionConfiguration.class);
		OptimalLocationQuery optimalLocationQuery = context.getBean(OptimalLocationQuery.class);
		Candidate candidate = optimalLocationQuery.findBestLocation();
		
		System.out.printf("Latitude: %f\n", candidate.getLatitude());
		System.out.printf("Longitude: %f\n", candidate.getLongitude());
		context.close();
	}
}
