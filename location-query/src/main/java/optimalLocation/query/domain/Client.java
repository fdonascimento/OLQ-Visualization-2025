package optimalLocation.query.domain;

public class Client extends GeoLocation {

	private Double weight;
	private Facility closestFacility;

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
	
	public void setClosestFacility(Facility closestFacility) {
		if (this.closestFacility == null || closestFacility.distance(this) <= this.closestFacility.distance(this)) {
			if (this.closestFacility != null) {
				this.closestFacility.removeAttractedClient(this);
			}
			
			closestFacility.addAttractedClient(this);
			this.closestFacility = closestFacility;
		}
	}
}
