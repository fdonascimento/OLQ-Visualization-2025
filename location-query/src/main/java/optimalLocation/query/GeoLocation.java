package optimalLocaiton.query;

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
		final double earthRadius = 6371; //Km
		
		double startLatitude = Math.toRadians(this.latitude);
		double endLatitude = Math.toRadians(other.latitude);
	    double latDistance = endLatitude - startLatitude;
	    double lngDistance = Math.toRadians(other.longitude - this.longitude);

	    double a = haversin(latDistance) + Math.cos(startLatitude) * Math.cos(endLatitude) * haversin(lngDistance);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    return c * earthRadius;
	}
	
	private double haversin(double distanceInRadians) {
		double sin = Math.sin(distanceInRadians / 2);
		return Math.pow(sin, 2);
	}

}
