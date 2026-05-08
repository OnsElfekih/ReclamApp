package projet.elfekih.ons.service;

import java.util.List;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.elfekih.ons.entities.Client;
import projet.elfekih.ons.repository.ClientRepository;

@Service
public class ClientService {
	@Autowired
	private ClientRepository clientRepository;

	public List<Client> findAll() {
		return clientRepository.findAll();
	}

	public Client findById(Long id) {
		return clientRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable, id=" + id));
	}

	public Client save(Client client) {
		clientRepository.findByEmail(client.getEmail()).ifPresent(c -> {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email déjà utilisé");
		});

		return clientRepository.save(client);
	}

	public Client update(Long id, Client client) {
		clientRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable, id=" + id));
		client.setId(id);
		return clientRepository.save(client);
	}

	public void deleteById(Long id) {
		clientRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable, id=" + id));
		clientRepository.deleteById(id);
	}

	public Client findByEmail(String email) {
		return clientRepository.findByEmail(email).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable avec email=" + email));
	}

	public List<Client> search(String keyword) {
		return clientRepository.findByNomContainingIgnoreCase(keyword);
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
