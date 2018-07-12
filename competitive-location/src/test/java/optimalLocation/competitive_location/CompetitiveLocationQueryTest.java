package optimalLocation.competitive_location;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import optimalLocation.query.domain.Candidate;
import optimalLocation.query.domain.Candidates;
import optimalLocation.query.domain.Client;
import optimalLocation.query.domain.Clients;
import optimalLocation.query.domain.Facilities;
import optimalLocation.query.domain.Facility;
import optimalLocation.query.domain.GeoLocation;
import optimalLocation.query.domain.LocationQueryResult;

public class CompetitiveLocationQueryTest {

	@Test
	public void findBestLocationTest() {
		//given
		Clients clients = new Clients();
		clients.addClient(-12.9865552, -38.4372664); //Jardim Armação
		clients.addClient(-12.9710111, -38.4344209); //Imbuí
		clients.addClient(-12.9804811, -38.4465942); //STIEP
		clients.addClient(-12.9359362, -38.4421752 ); //Sussuarana
		
		Facilities facilities = new Facilities();
		facilities.addFacility(-12.9654258, -38.4194011); //Pituaçu
		
		Candidates candidates = new Candidates();
		candidates.addCandidate(-12.9807739, -38.4332938); //Boca do Rio
		candidates.addCandidate(-12.9409831, -38.439391); //Novo Horizonte
		
		//when
		CompetitiveLocationQuery query = new CompetitiveLocationQuery();
		LocationQueryResult result = query.findBestLocation(clients, facilities, candidates);
		
		//then
		GeoLocation expected = new GeoLocation(-12.9807739, -38.4332938); //Boca do Rio
		assertThat(result.getFirstBestCandidate().getLatitude()).as("The latitudes are not equals")
															.isEqualTo(expected.getLatitude());
		
		assertThat(result.getFirstBestCandidate().getLongitude()).as("The longitudes are not equals")
															.isEqualTo(expected.getLongitude());
	}
	
	@Test
	public void findBestLocation_2Facilities_correctBestLocation() {
		//given
		Clients clients = new Clients();
		clients.addClient(1., 1.);
		clients.addClient(1., 2.);
		
		clients.addClient(4., 1.);
		clients.addClient(4., 2.);
		clients.addClient(4., 3.);
		
		clients.addClient(3., 1.);
		clients.addClient(3., 2.);
		clients.addClient(3., 3.);
		
		Facilities facilities = new Facilities();
		facilities.addFacility(1., 0.);
		facilities.addFacility(3., 0.);
		
		Candidates candidates = new Candidates();
		candidates.addCandidate(4., 0.);
		
		//when
		CompetitiveLocationQuery query = new CompetitiveLocationQuery();
		LocationQueryResult result = query.findBestLocation(clients, facilities, candidates);
		
		//then
		Candidate bestLocationExpected = new Candidate(4.,  0.);
		Candidate bestLocation = result.getFirstBestCandidate();
		assertThat(bestLocation.getLatitude()).as("The latitudes are not equals")
															.isEqualTo(bestLocationExpected.getLatitude());
		
		assertThat(bestLocation.getLongitude()).as("The longitudes are not equals")
															.isEqualTo(bestLocationExpected.getLongitude());
	}
	
	@Test
	public void findBestLocation_2Facilities_correctBestLocationClients() {
		//given
		Clients clientsFirstFacility = new Clients();
		clientsFirstFacility.addClient(1., 1.);
		clientsFirstFacility.addClient(1., 2.);
		
		Clients clientsCandidate = new Clients();
		clientsCandidate.addClient(4., 1.);
		clientsCandidate.addClient(4., 2.);
		clientsCandidate.addClient(4., 3.);
		
		Clients clientsSecondFacility = new Clients();
		clientsSecondFacility.addClient(3., 1.);
		clientsSecondFacility.addClient(3., 2.);
		clientsSecondFacility.addClient(3., 3.);
		
		Clients clients = new Clients();
		clients.merge(clientsCandidate);
		clients.merge(clientsFirstFacility);
		clients.merge(clientsSecondFacility);
		
		Facilities facilities = new Facilities();
		facilities.addFacility(1., 0.);
		facilities.addFacility(3., 0.);
		
		Candidates candidates = new Candidates();
		candidates.addCandidate(4., 0.);
		
		//when
		CompetitiveLocationQuery query = new CompetitiveLocationQuery();
		LocationQueryResult result = query.findBestLocation(clients, facilities, candidates);
		
		//then
		Candidate bestLocation = result.getFirstBestCandidate();
		assertThat(bestLocation.getAttractedClients()).hasSize(3);
		for (Client client : bestLocation.getAttractedClients()) {
			assertThat(clientsCandidate.stream()).anyMatch(c -> c.equals(client));
		}
	}
	
	@Test
	public void findBestLocation_2Facilities_correctFacilitiesClients() {
		//given
		Clients clientsFirstFacility = new Clients();
		clientsFirstFacility.addClient(1., 1.);
		clientsFirstFacility.addClient(1., 2.);
		
		Clients clientsSecondFacility = new Clients();
		clientsSecondFacility.addClient(3., 1.);
		clientsSecondFacility.addClient(3., 2.);
		clientsSecondFacility.addClient(3., 3.);

		clientsSecondFacility.addClient(4., 1.);
		clientsSecondFacility.addClient(4., 2.);
		clientsSecondFacility.addClient(4., 3.);
		
		Clients clients = new Clients();
		clients.merge(clientsFirstFacility);
		clients.merge(clientsSecondFacility);
		
		Facilities facilities = new Facilities();
		facilities.addFacility(1., 0.);
		facilities.addFacility(3., 0.);
		
		Candidates candidates = new Candidates();
		candidates.addCandidate(4., 0.);
		
		//when
		CompetitiveLocationQuery query = new CompetitiveLocationQuery();
		query.findBestLocation(clients, facilities, candidates);
		
		//then
		Iterator<Facility> iterator = facilities.iterator();
		Facility firstFacility = iterator.next();
		Facility secondFacility = iterator.next();
		
		assertThat(firstFacility.getAttractedClients()).hasSize(2);
		assertThat(secondFacility.getAttractedClients()).hasSize(6);
		
		for (Client client : firstFacility.getAttractedClients()) {
			assertThat(clientsFirstFacility.stream()).anyMatch(c -> c.equals(client));
		}
		
		for (Client client : secondFacility.getAttractedClients()) {
			assertThat(clientsSecondFacility.stream()).anyMatch(c -> c.equals(client));
		}
	}
}
