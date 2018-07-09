package optimalLocation.query.domain;

public class Candidate extends Place {

	private Double score;
	
	public Candidate(Double latitude, Double longitude) {
		super(latitude, longitude);
		startScore();
	}
	
	public Candidate(Double latitude, Double longitude, String name) {
		super(latitude, longitude, name);
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
