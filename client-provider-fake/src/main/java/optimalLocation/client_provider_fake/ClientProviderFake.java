package optimalLocation.client_provider_fake;

import optimalLocation.query.Clients;
import optimalLocation.query.providers.ClientProvider;

public class ClientProviderFake implements ClientProvider {

	public Clients getClients() {
		Clients clients = new Clients();
		clients.addClient(-12.9865552, -38.4372664); //Jardim Armação
		clients.addClient(-12.9710111, -38.4344209); //Imbuí
		clients.addClient(-12.9804811, -38.4465942); //STIEP
		clients.addClient(-12.9359362, -38.4421752 ); //Sussuarana
		return clients;
	}
}
