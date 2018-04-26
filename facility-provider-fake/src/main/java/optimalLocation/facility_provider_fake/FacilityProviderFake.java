package optimalLocation.facility_provider_fake;

import optimalLocation.query.Facilities;
import optimalLocation.query.providers.FacilityProvider;

public class FacilityProviderFake implements FacilityProvider
{
	@Override
	public Facilities getFacilities() {
		Facilities facilities = new Facilities();
		facilities.addFacility(-12.9654258, -38.4194011); //Pituaçu
		return facilities;
	}
}
