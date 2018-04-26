package optimalLocaiton.query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Clients implements Iterable<Client>{

	private List<Client> clients;
	
	public Clients() {
		clients = new ArrayList<>();
	}
	
	public void addClient(Double latitude, Double longitude) {
		clients.add(new Client(latitude, longitude));
	}

	@Override
	public Iterator<Client> iterator() {
		return clients.iterator();
	}

}
