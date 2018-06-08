package optimalLocation.query.domain;

import java.util.ArrayList;
import java.util.List;

public class Candidate extends GeoLocation {

	private Double score;
	private List<Client> attractedClients;
	
	public Candidate(Double latitude, Double longitude) {
		super(latitude, longitude);
	}
	
	public Candidate(Double latitude, Double longitude, String name) {
		super(latitude, longitude, name);
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
		return this.attractedClients;
	}
}
