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
	public LocationQueryResult findBestLocation(Clients clients, Facilities facilities, Candidates candidates) {
		LocationQueryResult result = new LocationQueryResult();
		result.setClients(clients);
		result.setFacilities(facilities);
		result.setCandidates(candidates);
		
		for (Candidate candidate : candidates) {
			calculateCandidateScore(clients, facilities, candidate);
			result.setFirstBestCandidate(candidate);
		}
		
		candidates.removeCandidate(result.getFirstBestCandidate());
		candidates.removeCandidate(result.getSecondBestCandidate());
		candidates.removeCandidate(result.getThirdBestCandidate());
		return result;
	}
	
	private void calculateCandidateScore(Clients clients, Facilities facilities, Candidate candidate) {
		candidate.startScore();
		for (Client client : clients) {
			calculateCandidateScore(facilities, client, candidate);
		}
	}
	
	private void calculateCandidateScore(Facilities facilities, Client client, Candidate candidate) {
		boolean candidateIsTheClosestPlace = true;
		for (Facility facility : facilities) {
			client.setClosestFacility(facility);

			if (facility.distance(client) < candidate.distance(client)) {
				candidateIsTheClosestPlace = false;
			}
		}
		
		if (candidateIsTheClosestPlace) {
			candidate.addToScore(client.getWeight());
			candidate.addAttractedClient(client);
		}
	}
}
