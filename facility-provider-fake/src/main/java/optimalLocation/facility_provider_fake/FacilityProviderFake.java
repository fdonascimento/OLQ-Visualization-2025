package optimalLocation.facility_provider_fake;

import optimalLocation.query.domain.Facilities;
import optimalLocation.query.providers.FacilityProvider;

public class FacilityProviderFake implements FacilityProvider
{
	@Override
	public Facilities getFacilities() {
		Facilities facilities = new Facilities();
		facilities.addFacility(-12.992953, -38.5152408, "Garcia");
		facilities.addFacility(-12.9232927, -38.505632, "Bonfim");
		facilities.addFacility(-12.960991, -38.467068, "Cabula");
		facilities.addFacility(-12.903771, -38.405956, "Cajazeiras");
		facilities.addFacility(-12.953924, -38.404452, "Patamares");
		return facilities;
	}
}
