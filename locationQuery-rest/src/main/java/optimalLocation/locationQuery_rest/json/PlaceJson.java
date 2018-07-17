package optimalLocation.locationQuery_rest.json;

import java.util.List;
import java.util.stream.Collectors;

import optimalLocation.query.domain.Place;

public class PlaceJson extends GeoLocationJson {

	private List<ClientJson> attractedClients;
	
	public PlaceJson(Place place) {
		super(place);
		if (place != null) {
			if (place.getAttractedClients() != null) {
				this.attractedClients = place.getAttractedClients().stream().map(client -> new ClientJson(client)).collect(Collectors.toList());
			}
		}
	}

	public List<ClientJson> getAttractedClients() {
		return attractedClients;
	}
}
