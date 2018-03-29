package optimalLocation.core;

import optimalLocaiton.query.Candidate;
import optimalLocaiton.query.Candidates;
import optimalLocaiton.query.Clients;
import optimalLocaiton.query.Facilities;
import optimalLocaiton.query.LocationQuery;
import optimalLocaiton.query.providers.CandidateProvider;
import optimalLocaiton.query.providers.ClientProvider;
import optimalLocaiton.query.providers.FacilityProvider;

public class OptimalLocationQuery {
	
	private ClientProvider clientProvider;
	private FacilityProvider facilityProvider;
	private CandidateProvider candidateProvider;
	private LocationQuery locationQuery;
	
	public Candidate findBestLocation() {
		Clients clients = clientProvider.getClients();
		Candidates candidates = candidateProvider.getCandidates();
		Facilities facilities = facilityProvider.getFacilities();
		
		return locationQuery.run(clients, facilities, candidates);
	}
}
