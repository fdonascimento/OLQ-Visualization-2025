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
		
		//Coutos
		clients.addClient(-12.8175967, -38.4854044, "São Tomé de Paripe");
		clients.addClient(-12.824583, -38.47145 , "Paripe");
		clients.addClient(-12.8633545, -38.4736719, "Periperi");
		clients.addClient(-12.861071, -38.433227, "Valéria");
		
		//São Cristovão
		clients.addClient(-12.8802195, -38.3599894, "Itinga");
		clients.addClient(-12.9189829, -38.3126964, "Praia do Flamengo");
		clients.addClient(-12.9432087, -38.3354611 , "Stella Maris");
		clients.addClient(-12.9476043, -38.3629457, "Itapuã");
		clients.addClient(-12.9414165, -38.3714894, "Alto do Coqueirinho");
		clients.addClient(-12.9290087, -38.3749638, "Bairro da Paz");
		clients.addClient(-12.9196847, -38.367496, "Mussurunga");
		
		//IAPI
		clients.addClient(-12.9686897, -38.4741125, "Horto Bela Vista");
		clients.addClient(-12.9639484, -38.4849824, "Cidade Nova");
		clients.addClient(-12.9638509, -38.4906158, "Baixa de Quintas");
		clients.addClient(-12.9581223, -38.4941411, "Caixa D'água");
		clients.addClient(-12.9606564, -38.4835868, "Pau Miúdo");
		clients.addClient(-12.9499279, -38.4886385, "Pero Vaz");
		clients.addClient(-12.9493669, -38.4849418, "Santa Mônica");
		clients.addClient(-12.9569389, -38.4739341, "Bom Retiro");
		clients.addClient(-12.9452034, -38.4937522, "Liberdade");
		
		//Bonfim
		clients.addClient(-12.9139097, -38.4952964, "Ribeira");
		clients.addClient(-12.9297056, -38.5155687, "Monte Serrat");
		clients.addClient(-12.9314525, -38.5122816, "Boa Viagem");
		clients.addClient(-12.9223719, -38.4882178, "Lobato");

		
		return clients;
	}
}
