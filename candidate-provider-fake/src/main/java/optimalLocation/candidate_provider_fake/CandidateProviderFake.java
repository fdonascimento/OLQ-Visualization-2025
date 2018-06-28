package optimalLocation.candidate_provider_fake;

import optimalLocation.query.domain.Candidates;
import optimalLocation.query.providers.CandidateProvider;

public class CandidateProviderFake implements CandidateProvider {
	
	public Candidates getCandidates() {
		Candidates candidates = new Candidates();
		candidates.addCandidate(-12.9807739, -38.4332938, "Boca do Rio"); //Boca do Rio
		candidates.addCandidate(-12.9409831, -38.439391, "Novo Horizonte"); //Novo Horizonte
		candidates.addCandidate(-12.8502501, -38.468478, "Coutos");
		candidates.addCandidate(-12.917061, -38.350478 , "São Cristovão");
		candidates.addCandidate(-12.9536905, -38.4813489, "IAPI");
		return candidates;
	}
}
