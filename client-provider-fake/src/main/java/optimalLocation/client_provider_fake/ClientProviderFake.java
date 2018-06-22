package optimalLocation.client_provider_fake;

import optimalLocation.query.domain.Clients;
import optimalLocation.query.providers.ClientProvider;

public class ClientProviderFake implements ClientProvider {

	public Clients getClients() {
		Clients clients = new Clients();
		clients.addClient(-12.9865552, -38.4372664, "Jardim Armação"); //Jardim Armação
		clients.addClient(-12.9710111, -38.4344209, "Imbuí"); //Imbuí
		clients.addClient(-12.9804811, -38.4465942, "STIEP"); //STIEP
		clients.addClient(-12.9654258, -38.4194011, "Pituaçu"); //Pituaçu
		clients.addClient(-12.9687659, -38.4610694, "Pernambués"); //Pernambués
		clients.addClient(-12.9918874, -38.4659443, "Itaigara"); //Itaigara
		clients.addClient(-12.9992595, -38.4554978, "Pituba"); //Pituba
		clients.addClient(-12.9932632, -38.4448701, "Costa Azul"); //Costa Azul
		clients.addClient(-12.9509761, -38.3978191, "Patamares"); //Patamares
		
		clients.addClient(-12.9359362, -38.4421752, "Sussuarana" ); //Sussuarana
		clients.addClient(-12.9366815, -38.4245897, "São Rafael"); //São Rafael
		clients.addClient(-12.9491408, -38.4310335, "CAB"); //CAB
		clients.addClient(-12.9173516, -38.4498777, "Cajazeiras"); //Cajazeiras
		clients.addClient(-12.9030259, -38.4487354, "Dom Avelar"); //Dom Avelar
		clients.addClient(-12.9445609, -38.4861707, "Curuzu"); //Curuzu;
		clients.addClient(-12.9585433, -38.4674792, "Cabula"); //Cabula;
		
		clients.addClient(-12.9866966, -38.5138266, "Barris"); //Barris
		clients.addClient(-12.9861396, -38.4923837, "Brotas"); //Brotas
		clients.addClient(-12.9931651, -38.5227317, "Canela"); // Canela
		clients.addClient(-12.9722272, -38.5136315, "Cidade Baixa"); //Cidade Baixa
		clients.addClient(-13.0080604, -38.4965618, "Rio Vermelho"); // Rio Vermelho
		clients.addClient(-13.0074586, -38.513941, "Ondina"); //Ondina
		clients.addClient(-12.993279, -38.5254765, "Vitória"); //Vitória
		
		return clients;
	}
}
