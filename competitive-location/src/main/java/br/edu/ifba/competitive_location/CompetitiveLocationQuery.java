package br.edu.ifba.competitive_location;

public class CompetitiveLocationQuery {

	public QueryResult run(Clients clients, Facilities facilities, CandidateLocations candidates) {
		Candidate bestCandidate = null;
		for (Candidate candidate : candidates) {
			candidate.startScore();
			for (Client client : clients) {
				calculateCandidateScore(facilities, client, candidate);
			}
			if (bestCandidate == null || candidate.score() > bestCandidate.score()) {
				bestCandidate = candidate;
			}
		}
		
		QueryResult queryResult = new QueryResult();
		queryResult.setBestLocation(bestCandidate);
		return queryResult;
	}
	
	private void calculateCandidateScore(Facilities facilities, Client client, Candidate candidate) {
		for (Facility facility : facilities) {
			if (facility.distance(client) < candidate.distance(client)) {
				return;
			}
			candidate.addToScore(client.getWeight());
		}
	}
}
