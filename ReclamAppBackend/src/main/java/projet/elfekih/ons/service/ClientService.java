package projet.elfekih.ons.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import projet.elfekih.ons.dto.ClientDTO;
import projet.elfekih.ons.dto.ClientRequestDTO;
import projet.elfekih.ons.entities.Client;
import projet.elfekih.ons.mapper.ClientMapper;
import projet.elfekih.ons.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	public List<ClientDTO> findAll() {
		return clientRepository.findAll().stream().map(ClientMapper::toDTO).toList();
	}

	public ClientDTO findById(Long id) {
		Client client = clientRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable, id=" + id));

		return ClientMapper.toDTO(client);
	}

	public ClientDTO save(ClientRequestDTO dto) {
		clientRepository.findByEmail(dto.getEmail()).ifPresent(c -> {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email déjà utilisé");
		});

		Client client = ClientMapper.toEntity(dto);
		Client saved = clientRepository.save(client);

		return ClientMapper.toDTO(saved);
	}

	public ClientDTO update(Long id, ClientRequestDTO dto) {
		Client existing = clientRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable, id=" + id));

		existing.setNom(dto.getNom());
		existing.setEmail(dto.getEmail());
		existing.setTelephone(dto.getTelephone());

		if (dto.getMotDePasse() != null && !dto.getMotDePasse().isEmpty()) {
			existing.setMotDePasse(dto.getMotDePasse());
		}

		Client updated = clientRepository.save(existing);

		return ClientMapper.toDTO(updated);
	}

	public void deleteById(Long id) {
		clientRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable, id=" + id));

		try {
			clientRepository.deleteById(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"Impossible de supprimer ce client car il possède des réclamations");
		}
	}

	public Client findByEmail(String email) {
		return clientRepository.findByEmail(email).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable avec email=" + email));
	}

	public List<ClientDTO> search(String keyword) {
		return clientRepository.findByNomContainingIgnoreCase(keyword).stream().map(ClientMapper::toDTO).toList();
	}

	public Client login(String email, String motDePasse) {
		Client client = clientRepository.findByEmail(email)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email introuvable"));

		if (!client.getMotDePasse().equals(motDePasse)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Mot de passe incorrect");
		}

		return client;
	}
}