package optimalLocation.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import optimalLocation.clientProviderTxt.utils.ColumnsIndex;
import optimalLocation.query.domain.Client;
import optimalLocation.query.domain.Clients;

public class TxtParser {

	private Path source;
	
	public TxtParser(Path source) {
		this.source = source;
	}
	
	public Clients parse() throws ClientParseException {
		Clients clients = new Clients();
		try {
			Files.lines(source).forEach(line -> {
				clients.addClient(parseLine(line));
			});
			return clients;
		} catch (IOException e) {
			throw new ClientParseException(e);
		}
	}
	
	private Client parseLine(String line) {
		String[] columns = line.split(",");
		double latitude = Double.parseDouble(columns[ColumnsIndex.LATITUDE.getIndex()].trim());
		double longitude = Double.parseDouble(columns[ColumnsIndex.LONGITUDE.getIndex()].trim());
		return new Client(latitude, longitude);
	}
}
