package projet.elfekih.ons.controller;

import java.util.List;
//pour retourner une réponse login avec role, message, client
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//Active la validation des DTO
import jakarta.validation.Valid;
//DTO client envoyé au frontend
import projet.elfekih.ons.dto.ClientDTO;
//DTO client reçu depuis le frontend
import projet.elfekih.ons.dto.ClientRequestDTO;
//DTO login reçu depuis le frontend
import projet.elfekih.ons.dto.LoginDTO;
import projet.elfekih.ons.entities.Client;
import projet.elfekih.ons.mapper.ClientMapper;
import projet.elfekih.ons.service.ClientService;

@RestController
@RequestMapping("/api/clients")
//Autorise Angular local et Angular déployé
@CrossOrigin(origins = { "http://localhost:4200", "https://reclamapp-frontend-47755772899.us-central1.run.app" })
public class ClientController {
	// Injection du service client
	@Autowired
	private ClientService clientService;

	@GetMapping("/{id}")
	public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(clientService.findById(id));
	}

	@PostMapping
	public ResponseEntity<ClientDTO> save(@Valid @RequestBody ClientRequestDTO dto) {
		ClientDTO saved = clientService.save(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO loginDTO) {

		String email = loginDTO.getEmail();
		String motDePasse = loginDTO.getMotDePasse();

		if ("admin".equals(email) && "admin".equals(motDePasse)) {
			return ResponseEntity.ok(Map.of("role", "ADMIN", "message", "Connexion admin réussie"));
		}

		Client connectedClient = clientService.login(email, motDePasse);
		ClientDTO clientDTO = ClientMapper.toDTO(connectedClient);

		return ResponseEntity.ok(Map.of("role", "CLIENT", "client", clientDTO, "message", "Connexion client réussie"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClientDTO> update(@PathVariable Long id, @Valid @RequestBody ClientRequestDTO dto) {
		return ResponseEntity.ok(clientService.update(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		clientService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<ClientDTO>> findAll() {
		return ResponseEntity.ok(clientService.findAll());
	}

	@GetMapping("/search")
	public ResponseEntity<List<ClientDTO>> search(@RequestParam String keyword) {
		return ResponseEntity.ok(clientService.search(keyword));
	}
}