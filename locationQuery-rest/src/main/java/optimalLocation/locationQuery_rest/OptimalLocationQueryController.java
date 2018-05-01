package optimalLocation.locationQuery_rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import optimalLocation.core.OptimalLocationQuery;
import optimalLocation.query.Candidate;

@RestController
public class OptimalLocationQueryController {

	private final OptimalLocationQuery optimalLocationQuery;

	@Autowired
	public OptimalLocationQueryController(OptimalLocationQuery optimalLocationQuery) {
		this.optimalLocationQuery = optimalLocationQuery;
	}
	
	@RequestMapping("/bestLocation")
	public BestLocation bestLocation() {
		Candidate candidate = optimalLocationQuery.findBestLocation();
		return new BestLocation(candidate);
	}
}
