package optimalLocation.query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Facilities implements Iterable<Facility>{

	private List<Facility> facilities;
	
	public Facilities() {
		facilities = new ArrayList<>();
	}
	
	public void addFacility(Double latitude, Double longitude) {
		facilities.add(new Facility(latitude, longitude));
	}

	@Override
	public Iterator<Facility> iterator() {
		return facilities.iterator();
	}

}
