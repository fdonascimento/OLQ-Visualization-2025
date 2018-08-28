package optimalLocation.locationQuery_rest.json;

import optimalLocation.query.domain.Candidate;

public class CandidateJson extends PlaceJson {

	private Double score;
	
	public CandidateJson() {
		super(0.0,0.0);
	}
	
	public CandidateJson(Candidate candidate) {
		super(candidate);
		if (candidate != null) {
			this.score = candidate.score();
		}
	}
	
	public CandidateJson(Double latitude, Double longitude) {
		super(latitude, longitude);
	}

	public Double getScore() {
		return score;
	}
}
