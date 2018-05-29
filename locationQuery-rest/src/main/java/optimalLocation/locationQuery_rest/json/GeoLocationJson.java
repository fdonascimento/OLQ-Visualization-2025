package optimalLocation.locationQuery_rest.json;

import optimalLocation.query.domain.GeoLocation;

public class GeoLocationJson {

	private Double latitude;
	private Double longitude;

	public GeoLocationJson(GeoLocation geoLocation) {
		if (geoLocation != null) {
			this.latitude = geoLocation.getLatitude();
			this.longitude = geoLocation.getLongitude();
		}
	}
	
	public GeoLocationJson(Double latitude, Double longitude) {
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
