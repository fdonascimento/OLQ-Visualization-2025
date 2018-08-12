package optimalLocation.core;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import optimalLocation.query.LocationQuery;
import optimalLocation.query.domain.Candidate;
import optimalLocation.query.domain.Candidates;
import optimalLocation.query.providers.CandidateProvider;
import optimalLocation.query.providers.ClientProvider;
import optimalLocation.query.providers.FacilityProvider;

public class OptimalLocationQueryTest {

	@Mock
	private ClientProvider clientProvider;
	@Mock
	private FacilityProvider facilityProvider;
	@Mock
	private CandidateProvider candidateProvider;
	@Mock
	private LocationQuery locationQuery;
	
	@BeforeEach
	public void setupClass() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void findBestLocation_null_correctFlow() {
		//given
		OptimalLocationQuery optimalLocationQuery = new OptimalLocationQuery();
		optimalLocationQuery.setCandidateProvider(candidateProvider);
		optimalLocationQuery.setClientProvider(clientProvider);
		optimalLocationQuery.setFacilityProvider(facilityProvider);
		optimalLocationQuery.setLocationQuery(locationQuery);
		
		//when
		optimalLocationQuery.findBestLocation(null);
		
		//then
		verify(candidateProvider, times(1)).getCandidates();
		verify(clientProvider, times(1)).getClients();
		verify(facilityProvider, times(1)).getFacilities();
		verify(locationQuery, times(1)).findBestLocation(any(), any(), any());
	}
	
	@Test
	public void findBestLocation_candidates_correctFlow() {
		//given
		OptimalLocationQuery optimalLocationQuery = new OptimalLocationQuery();
		optimalLocationQuery.setCandidateProvider(candidateProvider);
		optimalLocationQuery.setClientProvider(clientProvider);
		optimalLocationQuery.setFacilityProvider(facilityProvider);
		optimalLocationQuery.setLocationQuery(locationQuery);
		
		Candidates candidates = new Candidates();
		candidates.addCandidate(new Candidate(1.1, 2.1));
		candidates.addCandidate(new Candidate(2.1, 2.3));
		
		//when
		optimalLocationQuery.findBestLocation(candidates);
		
		//then
		verifyZeroInteractions(candidateProvider);
		verify(clientProvider, times(1)).getClients();
		verify(facilityProvider, times(1)).getFacilities();
		verify(locationQuery, times(1)).findBestLocation(any(), any(), any());
	}
}
