package optimalLocation.query.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Candidates implements Iterable<Candidate>{

	private List<Candidate> candidates;
	
	public Candidates() {
		candidates = new ArrayList<>();
	}
	
	public void addCandidate(Double latitude, Double longitude) {
		candidates.add(new Candidate(latitude, longitude));
	}
	
	public void addCandidate(Double latitude, Double longitude, String name) {
		candidates.add(new Candidate(latitude, longitude, name));
	}
	
	public void removeCandidate(Candidate candidate) {
		this.candidates.remove(candidate);
	}

	@Override
	public Iterator<Candidate> iterator() {
		return candidates.iterator();
	}

	public Stream<Candidate> stream() {
		return this.candidates.stream();
	}
}
