package optimalLocation.locationQuery_rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import optimalLocation.core.OptimalLocationQuery;
import optimalLocation.locationQuery_rest.converter.CandidateConverter;
import optimalLocation.locationQuery_rest.json.InputQueryJson;
import optimalLocation.locationQuery_rest.json.LocationQueryResultJson;
import optimalLocation.query.domain.Candidate;
import optimalLocation.query.domain.Candidates;
import optimalLocation.query.domain.LocationQueryResult;

@RestController
public class OptimalLocationQueryController {

	private final OptimalLocationQuery optimalLocationQuery;
	private InputQueryJson inputQuery;

	@Autowired
	public OptimalLocationQueryController(OptimalLocationQuery optimalLocationQuery) {
		this.optimalLocationQuery = optimalLocationQuery;
		this.inputQuery = new InputQueryJson();
	}
	
	@CrossOrigin(methods=RequestMethod.POST)
	@PostMapping("/input-candidates")
	public ResponseEntity<String> test(@RequestBody InputQueryJson inputQuery) {
		if (inputQuery.getCandidates() != null && !inputQuery.getCandidates().isEmpty()) {
			this.inputQuery = inputQuery;
		} else {
			this.inputQuery = null;
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
	
	@CrossOrigin(methods=RequestMethod.GET)
	@GetMapping("/findBestLocation")
	public LocationQueryResultJson findBestLocation() {
		CandidateConverter converter = new CandidateConverter();
		Candidates candidates = converter.convertList(inputQuery.getCandidates());
		LocationQueryResult result = optimalLocationQuery.findBestLocation(candidates);
		return new LocationQueryResultJson(result);
	}
	
	@CrossOrigin(methods=RequestMethod.GET)
	@GetMapping("/influenceArea/{latitude}/{longitude}")
	public LocationQueryResultJson attractedClients(@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude) {
		Candidates candidates = new Candidates();
		candidates.addCandidate(new Candidate(latitude, longitude));
		LocationQueryResult result = optimalLocationQuery.findBestLocation(candidates);
		return new LocationQueryResultJson(result);
	}
}
