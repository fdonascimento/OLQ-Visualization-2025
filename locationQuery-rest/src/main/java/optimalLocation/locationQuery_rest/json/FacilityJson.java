package optimalLocation.locationQuery_rest.json;

import java.util.List;
import java.util.stream.Collectors;

import optimalLocation.query.domain.Facility;

public class FacilityJson extends GeoLocationJson {

	private Double score;
	private List<ClientJson> attractedClients;
	
	public FacilityJson(Facility facility) {
		super(facility);
		if (facility != null) {
			this.score = facility.score();
			if (facility.getAttractedClients() != null) {
				this.attractedClients = facility.getAttractedClients().stream().map(client -> new ClientJson(client)).collect(Collectors.toList());
			}
		}
	}
	
	public Double getScore() {
		return score;
	}
	
	public List<ClientJson> getAttractedClients() {
		return attractedClients;
	}
}
