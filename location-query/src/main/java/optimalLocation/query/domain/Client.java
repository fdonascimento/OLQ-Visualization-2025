package optimalLocation.query.domain;

public class Client extends GeoLocation {

	private Double weight;

	public Client(Double latitude, Double longitude) {
		super(latitude, longitude);
		weight = 1.0;
	}
	
	public Client(Double latitude, Double longitude, String name) {
		this(latitude, longitude);
		super.setName(name);
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}
}
