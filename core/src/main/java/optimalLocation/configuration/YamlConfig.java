package optimalLocation.configuration;

public class YamlConfig {

	private String locationQuery;
	private String clientProvider;
	private String facilityProvider;
	private String candidateProvider;

	public String getLocationQuery() {
		return locationQuery;
	}

	public void setLocationQuery(String locationQuery) {
		this.locationQuery = locationQuery;
	}

	public String getClientProvider() {
		return clientProvider;
	}

	public void setClientProvider(String clientProvider) {
		this.clientProvider = clientProvider;
	}

	public String getFacilityProvider() {
		return facilityProvider;
	}

	public void setFacilityProvider(String facilityProvider) {
		this.facilityProvider = facilityProvider;
	}

	public String getCandidateProvider() {
		return candidateProvider;
	}

	public void setCandidateProvider(String candidateProvider) {
		this.candidateProvider = candidateProvider;
	}
}
