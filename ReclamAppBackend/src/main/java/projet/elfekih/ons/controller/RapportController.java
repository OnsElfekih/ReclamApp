package projet.elfekih.ons.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet.elfekih.ons.service.ReclamationService;

@RestController
@RequestMapping("/api/rapport")
@CrossOrigin(origins = { "http://localhost:4200", "https://reclamapp-frontend-47755772899.us-central1.run.app" })
public class RapportController {
	@Autowired
	private ReclamationService reclamationService;

	// GET /api/rapport/satisfaction
	// Retourne : { totalReclamations, moyenneNotes, parStatut }
	@GetMapping("/satisfaction")
	public ResponseEntity<Map<String, Object>> getSatisfaction() {
		return ResponseEntity.ok(reclamationService.getRapport());
	}

	@GetMapping("/rapport")
	public ResponseEntity<Map<String, Object>> getRapport() {
		return ResponseEntity.ok(reclamationService.getRapport());
	}

}
