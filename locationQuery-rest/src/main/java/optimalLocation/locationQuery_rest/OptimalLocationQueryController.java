package optimalLocation.locationQuery_rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import optimalLocation.core.OptimalLocationQuery;
import optimalLocation.locationQuery_rest.json.LocationQueryResultJson;
import optimalLocation.query.domain.LocationQueryResult;

@RestController
public class OptimalLocationQueryController {

	private final OptimalLocationQuery optimalLocationQuery;

	@Autowired
	public OptimalLocationQueryController(OptimalLocationQuery optimalLocationQuery) {
		this.optimalLocationQuery = optimalLocationQuery;
	}
	
	@RequestMapping("/findBestLocation")
	public LocationQueryResultJson findBestLocation() {
		LocationQueryResult result = optimalLocationQuery.findBestLocation();
		return new LocationQueryResultJson(result);
	}
}
