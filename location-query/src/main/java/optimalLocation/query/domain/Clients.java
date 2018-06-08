package optimalLocation.query.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Clients implements Iterable<Client>{

	private List<Client> clients;
	
	public Clients() {
		clients = new ArrayList<>();
	}
	
	public void addClient(Double latitude, Double longitude) {
		clients.add(new Client(latitude, longitude));
	}
	
	public void addClient(Double latitude, Double longitude, String name) {
		clients.add(new Client(latitude, longitude, name));
	}

	@Override
	public Iterator<Client> iterator() {
		return clients.iterator();
	}

	public Stream<Client> stream() {
		return clients.stream();
	}
}
 