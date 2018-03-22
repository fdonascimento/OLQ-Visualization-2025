package br.edu.ifba.competitive_location;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CompetitiveLocationQueryTest {

	@Test
	public void runTest() {
		//given
		Clients clients = new Clients();
		clients.addClient(-12.9865552, -38.4372664); //Jardim Armação
		clients.addClient(-12.9710111, -38.4344209); //Imbuí
		clients.addClient(-12.9804811, -38.4465942); //STIEP
		clients.addClient(-12.9409831, -38.439391); //Novo Horizonte
		
		Facilities facilities = new Facilities();
		facilities.addFacility(-12.9654258, -38.4194011); //Pituaçu
		
		CandidateLocations candidates = new CandidateLocations();
		candidates.addCandidate(-12.9807739, -38.4332938); //Boca do Rio
		candidates.addCandidate(12.9491408, -38.4310335); //CAB
		
		//when
		CompetitiveLocationQuery query = new CompetitiveLocationQuery();
		QueryResult actual = query.run(clients, facilities, candidates);
		
		//then
		GeoLocation expected = new GeoLocation(-12.9807739, -38.4332938); //Boca do Rio
		assertThat(actual.getBestLocation().getLatitude()).as("The latitudes are not equals")
															.isEqualTo(expected.getLatitude());
		
		assertThat(actual.getBestLocation().getLongitude()).as("The longitudes are not equals")
															.isEqualTo(expected.getLongitude());
	}
}
