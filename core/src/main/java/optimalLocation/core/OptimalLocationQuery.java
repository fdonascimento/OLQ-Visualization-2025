package optimalLocation.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import optimalLocation.query.Candidate;
import optimalLocation.query.Candidates;
import optimalLocation.query.Clients;
import optimalLocation.query.Facilities;
import optimalLocation.query.LocationQuery;
import optimalLocation.query.providers.CandidateProvider;
import optimalLocation.query.providers.ClientProvider;
import optimalLocation.query.providers.FacilityProvider;

@Component
public class OptimalLocationQuery {

	private ClientProvider clientProvider;
	private FacilityProvider facilityProvider;
	private CandidateProvider candidateProvider;
	private LocationQuery locationQuery;

	@Autowired
	public void setClientProvider(ClientProvider clientProvider) {
		this.clientProvider = clientProvider;
	}

	@Autowired
	public void setFacilityProvider(FacilityProvider facilityProvider) {
		this.facilityProvider = facilityProvider;
	}

	@Autowired
	public void setCandidateProvider(CandidateProvider candidateProvider) {
		this.candidateProvider = candidateProvider;
	}

	@Autowired
	public void setLocationQuery(LocationQuery locationQuery) {
		this.locationQuery = locationQuery;
	}

	public Candidate findBestLocation() {
		Clients clients = clientProvider.getClients();
		Candidates candidates = candidateProvider.getCandidates();
		Facilities facilities = facilityProvider.getFacilities();

		return locationQuery.run(clients, facilities, candidates);
	}
}
