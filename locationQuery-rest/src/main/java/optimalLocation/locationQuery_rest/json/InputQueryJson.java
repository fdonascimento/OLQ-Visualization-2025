package optimalLocation.locationQuery_rest.json;

import java.util.List;

public class InputQueryJson {

	private List<CandidateJson> candidates;

	public InputQueryJson() {
	}

	public List<CandidateJson> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<CandidateJson> candidates) {
		this.candidates = candidates;
	}
}
