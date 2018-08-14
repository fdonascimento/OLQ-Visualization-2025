package optimalLocation.locationQuery_rest.json;

import optimalLocation.query.domain.Client;

public class ClientJson extends GeoLocationJson {

	private Double weight;
	
	public ClientJson(Client client) {
		super(client);
		if (client != null) {
			this.weight = client.getWeight();
		}
	}
	
	public Double getWeight() {
		return weight;
	}
}
