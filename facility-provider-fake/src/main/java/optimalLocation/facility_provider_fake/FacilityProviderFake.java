package optimalLocation.facility_provider_fake;

import optimalLocation.query.domain.Facilities;
import optimalLocation.query.providers.FacilityProvider;

public class FacilityProviderFake implements FacilityProvider
{
	@Override
	public Facilities getFacilities() {
		Facilities facilities = new Facilities();
		facilities.addFacility(-12.992953, -38.5152408, "Garcia"); //Garcia
		return facilities;
	}
}
