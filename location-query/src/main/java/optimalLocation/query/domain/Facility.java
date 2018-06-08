package optimalLocation.query.domain;

import java.util.ArrayList;
import java.util.List;

public class Facility extends GeoLocation {

	private List<Client> attractedClients;
	
	public Facility(Double latitude, Double longitude) {
		super(latitude, longitude);
	}
	
	public Facility(Double latitude, Double longitude, String name) {
		super(latitude, longitude, name);
	}

	public void addAttractedClient(Client client) {
		if (attractedClients == null) {
			this.attractedClients = new ArrayList<>();
		}
		this.attractedClients.add(client);
	}
	
	public List<Client> getAttractedClients() {
		return this.attractedClients;
	}
}
