package optimalLocation.query.domain;

public class Client extends GeoLocation {

	private Double weight;
	private Place closestPlace;

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

	public GeoLocation getClosestPlace() {
		return closestPlace;
	}

	public void setClosestPlace(Place closestPlace) {
		if (this.closestPlace == null || closestPlace.distance(this) < this.closestPlace.distance(this)) {
			if (this.closestPlace != null) {
				this.closestPlace.removeAttractedClient(this);
			}
			
			closestPlace.addAttractedClient(this);
			this.closestPlace = closestPlace;
		}
	}
}
