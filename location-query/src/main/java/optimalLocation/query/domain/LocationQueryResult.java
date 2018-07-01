package optimalLocation.query.domain;
public class LocationQueryResult {

	private Candidates candidates;
	private Facilities facilities;
	private Clients clients;
	private Candidate firstBestCandidate;
	private Candidate secondBestCandidate;
	private Candidate thirdBestCandidate;

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

	public Candidate getFirstBestCandidate() {
		return firstBestCandidate;
	}

	public void setFirstBestCandidate(Candidate newFirstBestCandidate) {
		if (firstBestCandidate == null || newFirstBestCandidate.score() > firstBestCandidate.score()) {
			setSecondBestCandidate(firstBestCandidate);
			firstBestCandidate = newFirstBestCandidate;
		}
	}

	public Candidate getSecondBestCandidate() {
		return secondBestCandidate;
	}

	public void setSecondBestCandidate(Candidate newSecondBestCandidate) {
		if (newSecondBestCandidate != null && (secondBestCandidate == null || newSecondBestCandidate.score() > secondBestCandidate.score())) {
			setThirdBestCandidate(secondBestCandidate);
			secondBestCandidate = newSecondBestCandidate;
		}
	}

	public Candidate getThirdBestCandidate() {
		return thirdBestCandidate;
	}

	public void setThirdBestCandidate(Candidate newThirdBestCandidate) {
		if (newThirdBestCandidate !=null && (thirdBestCandidate == null || newThirdBestCandidate.score() > thirdBestCandidate.score())) {
			thirdBestCandidate = newThirdBestCandidate;
		}
	}
}
