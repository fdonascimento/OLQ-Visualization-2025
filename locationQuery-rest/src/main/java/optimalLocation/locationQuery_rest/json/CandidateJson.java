package optimalLocation.locationQuery_rest.json;

import optimalLocation.query.domain.Candidate;

public class CandidateJson extends PlaceJson {

	private Double score;
	
	public CandidateJson(Candidate candidate) {
		super(candidate);
		if (candidate != null) {
			this.score = candidate.score();
		}
	}

	public Double getScore() {
		return score;
	}
}
