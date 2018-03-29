package br.edu.ifba.competitive_location;

import br.edu.ifba.location_query.Candidate;
import br.edu.ifba.location_query.Candidates;
import br.edu.ifba.location_query.Client;
import br.edu.ifba.location_query.Clients;
import br.edu.ifba.location_query.Facilities;
import br.edu.ifba.location_query.Facility;
import br.edu.ifba.location_query.LocationQuery;

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
