package optimalLocation.candidate_provider_fake;

import optimalLocation.query.domain.Candidates;
import optimalLocation.query.providers.CandidateProvider;

public class CandidateProviderFake implements CandidateProvider {
	
	public Candidates getCandidates() {
		Candidates candidates = new Candidates();
		candidates.addCandidate(-12.9807739, -38.4332938, "Boca do Rio"); //Boca do Rio
		candidates.addCandidate(-12.9409831, -38.439391, "Novo Horizonte"); //Novo Horizonte
		return candidates;
	}
}
