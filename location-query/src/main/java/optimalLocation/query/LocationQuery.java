package optimalLocation.query;

import optimalLocation.query.domain.Candidates;
import optimalLocation.query.domain.Clients;
import optimalLocation.query.domain.Facilities;
import optimalLocation.query.domain.LocationQueryResult;

public interface LocationQuery {

	LocationQueryResult findBestLocation(Clients clients, Facilities facilities, Candidates candidates);
}
