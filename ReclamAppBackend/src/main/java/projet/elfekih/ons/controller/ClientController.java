package projet.elfekih.ons.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import projet.elfekih.ons.entities.Client;
import projet.elfekih.ons.service.ClientService;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@GetMapping("/{id}")
	public ResponseEntity<Client> findById(@PathVariable Long id) {
		Client client = clientService.findById(id);
		return ResponseEntity.ok(client);
	}

	@PostMapping
	public ResponseEntity<Client> save(@Valid @RequestBody Client client) {
		Client saved = clientService.save(client);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body) {

		String email = body.get("email");
		String motDePasse = body.get("motDePasse");

		if ("admin".equals(email) && "admin".equals(motDePasse)) {
			return ResponseEntity.ok(Map.of(
					"role", "ADMIN",
					"message", "Connexion admin réussie"
			));
		}

		Client connectedClient = clientService.login(email, motDePasse);

		return ResponseEntity.ok(Map.of(
				"role", "CLIENT",
				"client", connectedClient,
				"message", "Connexion client réussie"
		));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Client> update(@PathVariable Long id, @Valid @RequestBody Client client) {
		Client updated = clientService.update(id, client);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		clientService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Client>> findAll() {
		return ResponseEntity.ok(clientService.findAll());
	}

	@GetMapping("/search")
	public List<Client> search(@RequestParam String keyword) {
		return clientService.search(keyword);
	}
}