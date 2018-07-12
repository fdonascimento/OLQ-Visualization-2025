package optimalLocation.query.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Place extends GeoLocation {

	private Set<Client> attractedClients;
	
	public Place(Double latitude, Double longitude) {
		super(latitude, longitude);
		this.attractedClients = new HashSet<>();
	}
	
	public Place(Double latitude, Double longitude, String name) {
		this(latitude, longitude);
		super.setName(name);
	}
	
	public void addAttractedClient(Client client) {
		this.attractedClients.add(client);
	}
	
	public Set<Client> getAttractedClients() {
		return Collections.unmodifiableSet(attractedClients);
	}
	
	public void removeAttractedClient(Client client) {
		attractedClients.remove(client);
	}
}
