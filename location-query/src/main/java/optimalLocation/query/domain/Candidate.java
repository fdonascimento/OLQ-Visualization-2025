package optimalLocation.query.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Candidate extends GeoLocation {

	private Double score;
	private Set<Client> attractedClients;
	
	public Candidate(Double latitude, Double longitude) {
		super(latitude, longitude);
		startScore();
		this.attractedClients = new HashSet<>();
	}
	
	public Candidate(Double latitude, Double longitude, String name) {
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
		this.attractedClients.add(client);
	}
	
	public Set<Client> getAttractedClients() {
		return Collections.unmodifiableSet(attractedClients);
	}
	
	public void removeAttractedClient(Client client) {
		attractedClients.remove(client);
	}
}
