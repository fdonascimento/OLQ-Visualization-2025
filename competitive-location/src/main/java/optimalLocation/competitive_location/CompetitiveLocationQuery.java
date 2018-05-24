package optimalLocation.competitive_location;

import optimalLocation.query.LocationQuery;
import optimalLocation.query.domain.Candidate;
import optimalLocation.query.domain.Candidates;
import optimalLocation.query.domain.Client;
import optimalLocation.query.domain.Clients;
import optimalLocation.query.domain.Facilities;
import optimalLocation.query.domain.Facility;
import optimalLocation.query.domain.LocationQueryResult;

public class CompetitiveLocationQuery implements LocationQuery {

	@Override
	public LocationQueryResult run(Clients clients, Facilities facilities, Candidates candidates) {
		LocationQueryResult result = new LocationQueryResult();
		result.setClients(clients);
		result.setFacilities(facilities);
		result.setCandidates(candidates);
		
		Candidate bestCandidate = null;
		for (Candidate candidate : candidates) {
			calculateCandidateScore(clients, facilities, candidate);
			bestCandidate = getBestCandidate(bestCandidate, candidate);
		}
		
		result.setBestCandidate(bestCandidate);
		return result;
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
