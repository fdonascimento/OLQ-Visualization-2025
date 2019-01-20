package optimalLocation.min_clients;

import optimalLocation.query.LocationQuery;
import optimalLocation.query.domain.Candidate;
import optimalLocation.query.domain.Candidates;
import optimalLocation.query.domain.Client;
import optimalLocation.query.domain.Clients;
import optimalLocation.query.domain.Facilities;
import optimalLocation.query.domain.Facility;
import optimalLocation.query.domain.LocationQueryResult;
import optimalLocation.query.domain.Place;

public class MinClientsLocationQuery implements LocationQuery {

	@Override
	public LocationQueryResult findBestLocation(Clients clients, Facilities facilities, Candidates candidates) {
		LocationQueryResult result = new LocationQueryResult();
		result.setClients(clients);
		result.setFacilities(facilities);
		result.setCandidates(candidates);
		Candidate bestCandidate = null;
		
		for (Candidate candidate : candidates) {
			calculateCandidateScore(clients, facilities, candidate);
			if (bestCandidate == null || candidate.score() < bestCandidate.score()) {
				bestCandidate = candidate;
			}
			candidate.calculateClosestAndFarthestClient();
		}
		result.setBestCandidate(bestCandidate);
		
		facilities.forEach(Place::calculateClosestAndFarthestClient);
		return result;
	}
	
	public void setBestCandidate(Candidate newBestCandidate) {
		
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
