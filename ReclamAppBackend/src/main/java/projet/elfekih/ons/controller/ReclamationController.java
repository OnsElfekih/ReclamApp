package projet.elfekih.ons.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import projet.elfekih.ons.entities.Reclamation;
import projet.elfekih.ons.entities.StatutReclamation;
import projet.elfekih.ons.service.ReclamationService;

@RestController
@RequestMapping("/api/reclamations")
@CrossOrigin(origins = "http://localhost:4200")
public class ReclamationController {

	@Autowired
	private ReclamationService reclamationService;

	@GetMapping
	public ResponseEntity<List<Reclamation>> findAll(@RequestParam(required = false) StatutReclamation statut) {
		if (statut != null) {
			return ResponseEntity.ok(reclamationService.findByStatut(statut));
		}

		return ResponseEntity.ok(reclamationService.findAll());
	}

	@GetMapping("/search")
	public ResponseEntity<List<Reclamation>> search(@RequestParam(required = false) String agentNom,
			@RequestParam(required = false) String clientNom, @RequestParam(required = false) String produit,
			@RequestParam(required = false) StatutReclamation statut, @RequestParam(required = false) String date) {
		return ResponseEntity.ok(reclamationService.search(agentNom, clientNom, produit, statut, date));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Reclamation> findById(@PathVariable Long id) {
		return ResponseEntity.ok(reclamationService.findById(id));
	}

	@GetMapping("/client/{clientId}")
	public ResponseEntity<List<Reclamation>> findByClient(@PathVariable Long clientId) {
		return ResponseEntity.ok(reclamationService.findByClientId(clientId));
	}

	@PostMapping
	public ResponseEntity<Reclamation> save(@Valid @RequestBody Reclamation reclamation) {
		Reclamation saved = reclamationService.save(reclamation);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Reclamation> update(@PathVariable Long id, @Valid @RequestBody Reclamation reclamation) {
		return ResponseEntity.ok(reclamationService.update(id, reclamation));
	}

	@PutMapping("/{id}/affecter/{agentId}")
	public ResponseEntity<Reclamation> affecter(@PathVariable Long id, @PathVariable Long agentId) {
		return ResponseEntity.ok(reclamationService.affecter(id, agentId));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		reclamationService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/rapport")
	public ResponseEntity<Map<String, Object>> getRapport() {
		return ResponseEntity.ok(reclamationService.getRapport());
	}
}