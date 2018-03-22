package br.edu.ifba.competitive_location;

public class GeoLocation {

	private Double latitude;
	private Double longitude;

	public GeoLocation(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}
	
	public Double distance(GeoLocation other) {
	    double latDistance = Math.toRadians(other.latitude - this.latitude);
	    double lngDistance = Math.toRadians(other.longitude - this.longitude);

	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	      + Math.cos(Math.toRadians(other.latitude)) * Math.cos(Math.toRadians(this.latitude))
	      * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

	    return 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	}

}
