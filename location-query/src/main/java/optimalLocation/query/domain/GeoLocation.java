package optimalLocation.query.domain;

public class GeoLocation {

	private Double latitude;
	private Double longitude;
	private String name;

	public GeoLocation(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public GeoLocation(Double latitude, Double longitude, String name) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
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

	public void setName(String name) {
		this.name = name;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeoLocation other = (GeoLocation) obj;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		return true;
	}
}
