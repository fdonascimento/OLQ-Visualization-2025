package br.edu.ifba.competitive_location;

public class CompetitiveLocationQuery {

	public Candidate run(Clients clients, Facilities facilities, CandidateLocations candidates) {
		Candidate bestCandidate = null;
		for (Candidate candidate : candidates) {
			calculateCandidateScore(clients, facilities, candidate);
			bestCandidate = getBestCandidate(bestCandidate, candidate);
		}
		
		return bestCandidate;
	}

	private Candidate getBestCandidate(Candidate bestCandidate, Candidate newCandidate) {
		if (bestCandidate == null || newCandidate.score() > bestCandidate.score()) {
			bestCandidate = newCandidate;
		}
		return bestCandidate;
	}

	private void calculateCandidateScore(Clients clients, Facilities facilities, Candidate candidate) {
		candidate.startScore();
		for (Client client : clients) {
			calculateCandidateScore(facilities, client, candidate);
		}
	}
	
	private void calculateCandidateScore(Facilities facilities, Client client, Candidate candidate) {
		for (Facility facility : facilities) {
			if (facility.distance(client) < candidate.distance(client)) {
				break;
			}
			candidate.addToScore(client.getWeight());
		}
	}
}
