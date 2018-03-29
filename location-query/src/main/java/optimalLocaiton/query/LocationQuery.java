package optimalLocaiton.query;

public interface LocationQuery {

	Candidate run(Clients clients, Facilities facilities, Candidates candidates);
}
