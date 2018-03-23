package br.edu.ifba.competitive_location;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CandidateLocations implements Iterable<Candidate>{

	private List<Candidate> candidates;
	
	public CandidateLocations() {
		candidates = new ArrayList<>();
	}
	
	public void addCandidate(Double latitude, Double longitude) {
		candidates.add(new Candidate(latitude, longitude));
	}

	@Override
	public Iterator<Candidate> iterator() {
		return candidates.iterator();
	}

}
