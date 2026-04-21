package projet.elfekih.ons.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import projet.elfekih.ons.entities.Client;
import projet.elfekih.ons.repository.ClientRepository;


@Service
@RequiredArgsConstructor
public class ClientService {
	private final ClientRepository clientRepository;
	
	public List<Client> findAll() {
		return clientRepository.findAll();
	}
	
	public Client findById(Long id) {
		return clientRepository.findById(id).
				orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
	}
    public Client save(Client client) {
        return clientRepository.save(client);
    }
    public Client update(Long id, Client client) {
        clientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Client introuvable, id=" + id));
        client.setId(id);
        return clientRepository.save(client);
    }
    public void deleteById(Long id) {
        clientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Client introuvable, id=" + id));
        clientRepository.deleteById(id);
    }
    public boolean existsByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

}
