package optimalLocation.locationQuery_rest.json;

import optimalLocation.query.domain.Candidate;

public class BestLocationJson extends GeoLocationJson {

	public BestLocationJson(Candidate candidate) {
		super(candidate);
	}

	public BestLocationJson(Double latitude, Double longitude) {
		super(latitude, longitude);
	}

}
