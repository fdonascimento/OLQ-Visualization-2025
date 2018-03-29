package br.edu.ifba.location_query;

public interface LocationQuery {

	Candidate run(Clients clients, Facilities facilities, Candidates candidates);
}
