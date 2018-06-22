package optimalLocation.query.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Place extends GeoLocation {

	private Double score;
	private List<Client> attractedClients;
	
	public Place(Double latitude, Double longitude) {
		super(latitude, longitude);
		startScore();
	}
	
	public Place(Double latitude, Double longitude, String name) {
		this(latitude, longitude);
		super.setName(name);
	}
	
	public void addToScore(Double weight) {
		score += weight;
	}

	public Double score() {
		return score;
	}

	public void startScore() {
		score = 0.;
	}
	
	public void addAttractedClient(Client client) {
		if (attractedClients == null) {
			this.attractedClients = new ArrayList<>();
		}
		this.attractedClients.add(client);
	}
	
	public List<Client> getAttractedClients() {
		return Collections.unmodifiableList(attractedClients);
	}
	
	public void removeAttractedClient(Client client) {
		attractedClients.remove(client);
	}
}
