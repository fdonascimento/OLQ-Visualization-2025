package optimalLocation.locationQuery_rest.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import optimalLocation.locationQuery_rest.json.CandidateJson;
import optimalLocation.query.domain.Candidate;
import optimalLocation.query.domain.Candidates;

public class CandidateConverterTest {

	@Test
	public void convert_candidateJson_candidate() {
		//given
		CandidateConverter converter = new CandidateConverter();
		CandidateJson json = new CandidateJson(1., 2.);
		
		//when
		Candidate candidate = converter.convert(json);
		
		//then
		assertThat(candidate.getLatitude()).isEqualTo(1.);
		assertThat(candidate.getLongitude()).isEqualTo(2.);
	}
	
	@Test
	public void convert_nullJson_nullCandidate() {
		//given
		CandidateConverter converter = new CandidateConverter();
		CandidateJson nullJson = null;
		
		//when
		Candidate candidate = converter.convert(nullJson);
		
		//then
		assertThat(candidate).isNull();
	}
	
	@Test
	public void convertList_nullJsonList_nullCandidates() {
		//given
		CandidateConverter converter = new CandidateConverter();
		List<CandidateJson> nullJsonList = null;
		
		//when
		Candidates candidates = converter.convertList(nullJsonList);
		
		//then
		assertThat(candidates).isNull();
	}
	
	@Test
	public void convertList_jsonList_candidates() {
		//given
		CandidateConverter converter = new CandidateConverter();
		List<CandidateJson> jsonList = new ArrayList<>();
		jsonList.add(new CandidateJson(1.2, 2.2));
		jsonList.add(new CandidateJson(2.5, 2.1));
		
		//when
		Candidates candidates = converter.convertList(jsonList);
		
		//then
		assertThat(candidates.stream().count()).isEqualTo(2);
		
		Iterator<Candidate> iterator = candidates.iterator();
		Candidate candidate = iterator.next();
		assertThat(candidate.getLatitude()).isEqualTo(1.2);
		assertThat(candidate.getLongitude()).isEqualTo(2.2);
		
		candidate = iterator.next();
		assertThat(candidate.getLatitude()).isEqualTo(2.5);
		assertThat(candidate.getLongitude()).isEqualTo(2.1);
	}
}
