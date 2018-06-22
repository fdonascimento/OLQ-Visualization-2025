package optimalLocation.query.domain;

public class Facility extends Place {

	public Facility(Double latitude, Double longitude) {
		super(latitude, longitude);
	}
	
	public Facility(Double latitude, Double longitude, String name) {
		super(latitude, longitude, name);
	}
}
