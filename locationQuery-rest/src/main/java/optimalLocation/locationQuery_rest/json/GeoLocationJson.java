package optimalLocation.locationQuery_rest.json;

import optimalLocation.query.domain.GeoLocation;

public class GeoLocationJson {

	private Double latitude;
	private Double longitude;
	private String name;

	public GeoLocationJson(GeoLocation geoLocation) {
		if (geoLocation != null) {
			this.latitude = geoLocation.getLatitude();
			this.longitude = geoLocation.getLongitude();
			this.name = geoLocation.getName();
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
	
	public String getName() {
		return name;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
