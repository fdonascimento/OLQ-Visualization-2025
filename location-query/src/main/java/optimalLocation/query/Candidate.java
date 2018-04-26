package optimalLocation.query;

public class Candidate extends GeoLocation {

	private Double score;
	
	public Candidate(Double latitude, Double longitude) {
		super(latitude, longitude);
	}

	public void addToScore(Double weight) {
		score += weight;
	}

	public Double score() {
		return score;
	}

	public void startScore() {
		score = 0.;
	}

}
