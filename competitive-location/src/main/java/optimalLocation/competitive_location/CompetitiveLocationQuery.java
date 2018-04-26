package optimalLocation.competitive_location;

import optimalLocation.query.Candidate;
import optimalLocation.query.Candidates;
import optimalLocation.query.Client;
import optimalLocation.query.Clients;
import optimalLocation.query.Facilities;
import optimalLocation.query.Facility;
import optimalLocation.query.LocationQuery;

public class CompetitiveLocationQuery implements LocationQuery {

	@Override
	public Candidate run(Clients clients, Facilities facilities, Candidates candidates) {
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
