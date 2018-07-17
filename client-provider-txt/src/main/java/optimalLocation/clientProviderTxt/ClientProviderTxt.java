package optimalLocation.clientProviderTxt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;

import optimalLocation.clientProviderTxt.utils.CalculateMd5Exception;
import optimalLocation.clientProviderTxt.utils.Md5Calculator;
import optimalLocation.parser.ClientParseException;
import optimalLocation.parser.TxtParser;
import optimalLocation.query.domain.Clients;
import optimalLocation.query.providers.ClientProvider;

public class ClientProviderTxt implements ClientProvider {

	private Path source;
	private Clients cache;
	private String fileHash;
	
	public ClientProviderTxt(Path source) {
		this.source = source;
	}
	
	public ClientProviderTxt() {
		this.source = getDefaultSource();
	}
	
	private Path getDefaultSource() {
		Path dir = Paths.get(System.getProperty("user.dir"), "ClientProviders");
		try (Stream<Path> paths = Files.find(dir, 1, (fileName, fileAttributes) -> FilenameUtils.isExtension(fileName.toString(), "txt"))){
			return paths.findFirst().get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
				
	}
	
	@Override
	public Clients getClients() {
		try {
			String newHash = Md5Calculator.getMd5(source);
			if (!newHash.equals(fileHash)) {
				fileHash = newHash;
				cache = parseFile();
				return cache;
			}
			return cache;
		} catch (CalculateMd5Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private Clients parseFile() throws ClientParseException {
		TxtParser parser = new TxtParser(source);
		return parser.parse();
	}
	
	public static void main(String args[]) {
		ClientProviderTxt clientProvider = new ClientProviderTxt();
		Clients clients = clientProvider.getClients();
		long startTime = Calendar.getInstance().getTimeInMillis();
		long endTime = Calendar.getInstance().getTimeInMillis();
		System.out.println("ms: "+(endTime - startTime));
		System.out.println("clients size: "+clients.stream().count());
	}
}
