package optimalLocation.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import optimalLocation.parser.ClientParseException;
import optimalLocation.parser.TxtParser;
import optimalLocation.query.domain.Client;
import optimalLocation.query.domain.Clients;

public class TxtParserTest {

	@Test
	public void readTxt_3Clients() throws URISyntaxException, ClientParseException {
		//given
		Path path = Paths.get(getClass().getResource("inputParser.txt").getPath());
		TxtParser txtParser = new TxtParser(path);
		
		//when
		Clients clients = txtParser.parse();
		
		//then
		assertThat(clients).isNotNull().as("Clients cannot be null.");
		assertThat(clients.stream()).isNotNull().as("stream cannot be null");
		assertThat(clients.stream().count()).isEqualTo(3);
	}
	
	@Test
	public void readTxt_correctLatitudeAndLongitude() throws URISyntaxException, ClientParseException {
		//given
		Path path = Paths.get(getClass().getResource("inputParser.txt").getPath());
		TxtParser txtParser = new TxtParser(path);
		
		//when
		Clients clients = txtParser.parse();
		
		//then
		Client expectedClient = new Client(-12.9747,-38.4767);
		Client currentClient = clients.iterator().next();
		assertThat(currentClient.getLatitude()).isEqualTo(expectedClient.getLatitude());
		assertThat(currentClient.getLongitude()).isEqualTo(expectedClient.getLongitude());
	}
	
	@Test
	public void readTxt_inexistentFile_throwsException() throws URISyntaxException {
		//given
		Path path = Paths.get("inexistent.txt");
		TxtParser txtParser = new TxtParser(path);
		
		assertThatThrownBy(() -> {
			//when
			txtParser.parse();
			
			//then
		}).isInstanceOf(ClientParseException.class);
	}
}