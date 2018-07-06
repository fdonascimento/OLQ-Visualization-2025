package optimalLocation.query.domain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

public class Clients implements Iterable<Client>{

	private Set<Client> clients;
	
	public Clients() {
		clients = new HashSet<>();
	}
	
	public void addClient(Double latitude, Double longitude) {
		clients.add(new Client(latitude, longitude));
	}
	
	public void addClient(Double latitude, Double longitude, String name) {
		clients.add(new Client(latitude, longitude, name));
	}
	
	public void addClient(Client client) {
		clients.add(client);
	}

	@Override
	public Iterator<Client> iterator() {
		return clients.iterator();
	}

	public Stream<Client> stream() {
		return clients.stream();
	}
	
	public void merge(Clients clients) {
		this.clients.addAll(clients.clients);
	}
}
 