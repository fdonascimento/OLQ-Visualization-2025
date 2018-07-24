package optimalLocation.locationQuery_rest.json;

import java.util.List;
import java.util.stream.Collectors;

import optimalLocation.query.domain.Place;

public class PlaceJson extends GeoLocationJson {

	private List<ClientJson> attractedClients;
	private ClientJson closestClient;
	private ClientJson farthestClient;
	private Double minDistance;
	private Double maxDistance;
	private Double averageDistance;
	
	public PlaceJson(Place place) {
		super(place);
		if (place != null) {
			if (place.getAttractedClients() != null) {
				this.attractedClients = place.getAttractedClients().stream().map(client -> new ClientJson(client)).collect(Collectors.toList());
			}
			
			this.closestClient = new ClientJson(place.getClosestClient());
			this.farthestClient = new ClientJson(place.getFarthestClient());
			this.minDistance = place.getMinDistance();
			this.maxDistance = place.getMaxDistance();
			this.averageDistance = place.getAverageDistance();
		}
	}

	public List<ClientJson> getAttractedClients() {
		return attractedClients;
	}
	
	public Double getMaxDistance() {
		return maxDistance;
	}
	
	public Double getMinDistance() {
		return minDistance;
	}
	
	public ClientJson getClosestClient() {
		return closestClient;
	}
	
	public ClientJson getFarthestClient() {
		return farthestClient;
	}
	
	public Double getAverageDistance() {
		return averageDistance;
	}
}
