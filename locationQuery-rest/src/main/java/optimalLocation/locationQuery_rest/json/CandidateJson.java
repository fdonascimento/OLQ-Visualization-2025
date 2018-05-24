package optimalLocation.locationQuery_rest.json;

import optimalLocation.query.domain.Candidate;

public class CandidateJson extends GeoLocationJson {

	public Double score;
	
	public CandidateJson(Candidate candidate) {
		super(candidate);
		this.score = candidate.score();
	}

	public Double getScore() {
		return score;
	}
}
