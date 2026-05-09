package projet.elfekih.ons.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import projet.elfekih.ons.dto.SuiviReclamationDTO;
import projet.elfekih.ons.dto.SuiviReclamationRequestDTO;
import projet.elfekih.ons.service.SuiviService;

@RestController
@RequestMapping("/api/suivis")
@CrossOrigin(origins = { "http://localhost:4200", "https://reclamapp-frontend-47755772899.us-central1.run.app" })
public class SuiviController {

	@Autowired
	private SuiviService suiviService;

	@GetMapping("/reclamation/{reclamationId}")
	public ResponseEntity<List<SuiviReclamationDTO>> findByReclamation(@PathVariable Long reclamationId) {
		return ResponseEntity.ok(suiviService.findByReclamationId(reclamationId));
	}

	@PostMapping
	public ResponseEntity<SuiviReclamationDTO> save(@Valid @RequestBody SuiviReclamationRequestDTO dto) {
		SuiviReclamationDTO saved = suiviService.save(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		suiviService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<SuiviReclamationDTO>> findAll() {
		return ResponseEntity.ok(suiviService.findAll());
	}
}