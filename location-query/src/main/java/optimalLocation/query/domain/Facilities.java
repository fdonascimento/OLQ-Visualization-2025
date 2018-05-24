package optimalLocation.query.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

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

	public Stream<Facility> stream() {
		return this.facilities.stream();
	}
}
