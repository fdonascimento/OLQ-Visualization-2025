package optimalLocation.query.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Place extends GeoLocation {

	private Set<Client> attractedClients;
	private Client closestClient;
	private Client farthestClient;
	private double maxDistance;
	private double minDistance;
	private Double averageDistance;
	
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
	
	public Client getClosestClient() {
		return closestClient;
	}
	
	public Client getFarthestClient() {
		return farthestClient;
	}
	
	public double getMaxDistance() {
		return maxDistance;
	}
	
	public double getMinDistance() {
		return minDistance;
	}
	
	public double getAverageDistance() {
		if (averageDistance == null) {
			double sum = attractedClients.stream().mapToDouble(client -> this.distance(client)).sum();
			this.averageDistance = sum / attractedClients.size();
		}
		return averageDistance;
	}
	
	public void calculateClosestAndFarthestClient() {
		attractedClients.forEach(client -> {
			double distance = this.distance(client);
			if (this.closestClient == null || distance <= this.minDistance) {
				this.closestClient = client;
				this.minDistance = distance;
			}
			
			if (this.farthestClient == null || distance >= this.maxDistance) {
				this.farthestClient = client;
				this.maxDistance = distance;
			}
		});
	}
}
