package optimalLocation.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import optimalLocaiton.query.Candidate;
import optimalLocaiton.query.Candidates;
import optimalLocaiton.query.Clients;
import optimalLocaiton.query.Facilities;
import optimalLocaiton.query.LocationQuery;
import optimalLocaiton.query.providers.CandidateProvider;
import optimalLocaiton.query.providers.ClientProvider;
import optimalLocaiton.query.providers.FacilityProvider;

@Configuration
@ComponentScan(value="optimalLocation.core")
public class DependencyInjectionConfiguration {

	@Bean
	public FacilityProvider getFacilityProvider() {
		return new FacilityProvider() {
			
			@Override
			public Facilities getFacilities() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
	
	@Bean
	public ClientProvider getClientProvider() {
		return new ClientProvider() {
			
			@Override
			public Clients getClients() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
	
	@Bean
	public CandidateProvider getCandidateProvider() {
		return new CandidateProvider() {
			
			@Override
			public Candidates getCandidates() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
	
	@Bean
	public LocationQuery getLocationQuery() {
		return new LocationQuery() {
			
			@Override
			public Candidate run(Clients clients, Facilities facilities, Candidates candidates) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
}
