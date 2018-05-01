package optimalLocation.locationQuery_rest;

import optimalLocation.query.Candidate;

public class BestLocation {

	private Double latitude;
	private Double longitude;

	public BestLocation(Candidate candidate) {
		this.latitude = candidate.getLatitude();
		this.longitude = candidate.getLongitude();
	}

	public BestLocation(Double latitude, Double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}
}
