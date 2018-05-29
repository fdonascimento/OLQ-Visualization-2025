package optimalLocation.locationQuery_rest.json;

import java.util.List;
import java.util.stream.Collectors;

import optimalLocation.query.domain.LocationQueryResult;

public class LocationQueryResultJson {

	private CandidateJson bestLocation;
	private List<CandidateJson> candidates;
	private List<ClientJson> clients;
	private List<FacilityJson> facilities;
	
	public LocationQueryResultJson(LocationQueryResult result) {
		if (result != null) {
			this.bestLocation = new CandidateJson(result.getBestCandidate());
			this.candidates = result.getCandidates().stream().map(candidate -> new CandidateJson(candidate)).collect(Collectors.toList());
			this.clients = result.getClients().stream().map(client -> new ClientJson(client)).collect(Collectors.toList());
			this.facilities = result.getFacilities().stream().map(facility -> new FacilityJson(facility)).collect(Collectors.toList());
		}
	}
	
	public CandidateJson getBestLocation() {
		return bestLocation;
	}
	
	public List<CandidateJson> getCandidates() {
		return candidates;
	}
	
	public List<ClientJson> getClients() {
		return clients;
	}
	
	public List<FacilityJson> getFacilities() {
		return facilities;
	}
}
