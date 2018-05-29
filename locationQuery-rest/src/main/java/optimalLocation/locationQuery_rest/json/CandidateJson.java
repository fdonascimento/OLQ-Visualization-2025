package optimalLocation.locationQuery_rest.json;

import java.util.List;
import java.util.stream.Collectors;

import optimalLocation.query.domain.Candidate;

public class CandidateJson extends GeoLocationJson {

	private Double score;
	private List<ClientJson> attractedClients;
	
	public CandidateJson(Candidate candidate) {
		super(candidate);
		if (candidate != null) {
			this.score = candidate.score();
			this.attractedClients = candidate.getAttractedClients().stream().map(client -> new ClientJson(client)).collect(Collectors.toList());
		}
	}

	public Double getScore() {
		return score;
	}
	
	public List<ClientJson> getAttractedClients() {
		return attractedClients;
	}
}
