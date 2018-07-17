package optimalLocation.clientProviderTxt;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.jupiter.api.Test;

import optimalLocation.query.domain.Clients;
import optimalLocation.query.providers.ClientProvider;

public class ClientProviderTxtTest {

	@Test
	public void readTxt_3Clients() throws URISyntaxException {
		//given
		Path path = Paths.get(getClass().getResource("inputClientProvider.txt").getPath());
		ClientProvider clientProvider = new ClientProviderTxt(path);
		
		//when
		Clients clients = clientProvider.getClients();
		
		//then
		assertThat(clients).isNotNull().as("Clients cannot be null.");
		assertThat(clients.stream()).isNotNull().as("stream cannot be null");
		assertThat(clients.stream().count()).isEqualTo(3);
	}
	
	@Test
	public void readTxt_cacheIsUsed() throws URISyntaxException {
		//given
		Path path = Paths.get(getClass().getResource("inputClientProvider.txt").getPath());
		ClientProvider clientProvider = new ClientProviderTxt(path);
		Clients clients = clientProvider.getClients();
		
		//when
		Clients currentClients = clientProvider.getClients();
		
		//then
		assertThat(currentClients).isEqualTo(clients);
	}
	
	@Test
	public void readTxt_cacheIsNotUsed() throws URISyntaxException, IOException {
		//given
		Path path = Paths.get(getClass().getResource("inputClientProvider.txt").getPath());
		ClientProvider clientProvider = new ClientProviderTxt(path);
		Clients clients = clientProvider.getClients();
		String newLine = "-12.9747,-38.4767,1507686148,Acabou de publicar um vídeo em Salvador, Bahia, Brazil https://t.co/ttIzlx62cK";
		Files.write(path, newLine.getBytes(), StandardOpenOption.APPEND);
		
		//when
		Clients currentClients = clientProvider.getClients();
		
		//then
		assertThat(currentClients).isNotEqualTo(clients);
	}
}
