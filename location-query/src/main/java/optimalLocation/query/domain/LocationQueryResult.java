package optimalLocation.query.domain;

public class LocationQueryResult {

	private Candidates candidates;
	private Facilities facilities;
	private Clients clients;
	private Candidate bestCandidate;

	public Candidates getCandidates() {
		return candidates;
	}

	public void setCandidates(Candidates candidates) {
		this.candidates = candidates;
	}

	public Facilities getFacilities() {
		return facilities;
	}

	public void setFacilities(Facilities facilities) {
		this.facilities = facilities;
	}

	public Clients getClients() {
		return clients;
	}

	public void setClients(Clients clients) {
		this.clients = clients;
	}

	public Candidate getBestCandidate() {
		return bestCandidate;
	}

	public void setBestCandidate(Candidate newBestCandidate) {
		if (bestCandidate == null || newBestCandidate.score() > bestCandidate.score()) {
			bestCandidate = newBestCandidate;
		}
	}
}
