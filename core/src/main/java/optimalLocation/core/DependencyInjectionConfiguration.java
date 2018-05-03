package optimalLocation.core;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import optimalLocation.configuration.YamlConfig;
import optimalLocation.loader.BeanFinder;
import optimalLocation.loader.FindLocationQueryException;
import optimalLocation.query.LocationQuery;
import optimalLocation.query.providers.CandidateProvider;
import optimalLocation.query.providers.ClientProvider;
import optimalLocation.query.providers.FacilityProvider;
import optimalLocation.yaml.YamlReadException;
import optimalLocation.yaml.YamlReader;

@Configuration
@ComponentScan(value="optimalLocation.core")
public class DependencyInjectionConfiguration {

	private YamlConfig yamlConfig;
	
	public DependencyInjectionConfiguration() throws YamlReadException {
		this.yamlConfig = YamlReader.readYaml(new File("settings.yaml"), YamlConfig.class);
	}
	
	@Bean
	public FacilityProvider getFacilityProvider() throws FindLocationQueryException {
		return BeanFinder.findFacilityProvider(yamlConfig.getFacilityProvider());
	}
	
	@Bean
	public ClientProvider getClientProvider() throws FindLocationQueryException {
		return BeanFinder.findClientProvider(yamlConfig.getClientProvider());
	}
	
	@Bean
	public CandidateProvider getCandidateProvider() throws FindLocationQueryException {
		return BeanFinder.findCandidateProvider(yamlConfig.getCandidateProvider());
	}
	
	@Bean
	public LocationQuery getLocationQuery() throws FindLocationQueryException {
		return BeanFinder.findLocationQuery(yamlConfig.getLocationQuery());
	}
}
