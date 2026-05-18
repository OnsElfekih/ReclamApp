package projet.elfekih.ons.mapper;

import projet.elfekih.ons.dto.ClientDTO;
import projet.elfekih.ons.dto.ClientRequestDTO;
import projet.elfekih.ons.entities.Client;

public class ClientMapper {

	public static ClientDTO toDTO(Client client) {

		ClientDTO dto = new ClientDTO();
		 // Copie id
		dto.setId(client.getId());
		dto.setNom(client.getNom());
		dto.setEmail(client.getEmail());
		dto.setTelephone(client.getTelephone());

		return dto;
	}

	public static Client toEntity(ClientRequestDTO dto) {

		Client client = new Client();

		client.setNom(dto.getNom());
		client.setEmail(dto.getEmail());
		client.setTelephone(dto.getTelephone());
		client.setMotDePasse(dto.getMotDePasse());

		return client;
	}
}