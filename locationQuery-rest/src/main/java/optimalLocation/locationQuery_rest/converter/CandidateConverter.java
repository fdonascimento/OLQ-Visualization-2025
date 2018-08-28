package optimalLocation.locationQuery_rest.converter;

import java.util.List;

import optimalLocation.locationQuery_rest.json.CandidateJson;
import optimalLocation.query.domain.Candidate;
import optimalLocation.query.domain.Candidates;

public class CandidateConverter {

	public Candidate convert(CandidateJson candidateJson) {
		if (candidateJson == null) {
			return null;
		}
		return new Candidate(candidateJson.getLatitude(), candidateJson.getLongitude());
	}
	
	public Candidates convertList(List<CandidateJson> candidatesJson) {
		if (candidatesJson == null) {
			return null;
		}
		Candidates candidates = new Candidates();
		candidatesJson.forEach(candidateJson -> candidates.addCandidate(convert(candidateJson)));
		return candidates;
	}
}
