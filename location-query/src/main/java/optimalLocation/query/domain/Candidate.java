package optimalLocation.query.domain;

public class Candidate extends Place {

	public Candidate(Double latitude, Double longitude) {
		super(latitude, longitude);
	}
	
	public Candidate(Double latitude, Double longitude, String name) {
		super(latitude, longitude, name);
	}
}
