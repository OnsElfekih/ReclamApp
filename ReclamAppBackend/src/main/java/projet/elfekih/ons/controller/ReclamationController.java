package projet.elfekih.ons.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import projet.elfekih.ons.entities.Reclamation;
import projet.elfekih.ons.entities.StatutReclamation;
import projet.elfekih.ons.service.ReclamationService;

@RestController
@RequestMapping("/api/reclamations")
@CrossOrigin(origins = "http://localhost:4200")
public class ReclamationController {
	@Autowired
	private ReclamationService reclamationService;
	
	// GET /api/reclamations
    // GET /api/reclamations?statut=OUVERTE  — filtrage optionnel par statut
    @GetMapping
    public ResponseEntity<List<Reclamation>> findAll(
            @RequestParam(required = false) StatutReclamation statut) {
        if (statut != null) {
            return ResponseEntity.ok(reclamationService.findByStatut(statut));
        }
        return ResponseEntity.ok(reclamationService.findAll());
    }

    // GET /api/reclamations/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Reclamation> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reclamationService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // GET /api/reclamations/client/{clientId}
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Reclamation>> findByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(reclamationService.findByClientId(clientId));
    }
    
    // POST /api/reclamations
    // Le body JSON doit contenir : { "client": {"id": 1}, "produit": "...", "description": "..." }
    @PostMapping
    public ResponseEntity<Reclamation> save(@Valid @RequestBody Reclamation reclamation) {
        try {
            Reclamation saved = reclamationService.save(reclamation);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
        	throw e;
        }
    }

    // PUT /api/reclamations/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Reclamation> update(
            @PathVariable Long id,
            @Valid @RequestBody Reclamation reclamation) {
        try {
            return ResponseEntity.ok(reclamationService.update(id, reclamation));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // PUT /api/reclamations/{id}/affecter/{agentId} — affectation agent
    @PutMapping("/{id}/affecter/{agentId}")
    public ResponseEntity<Reclamation> affecter(
            @PathVariable Long id,
            @PathVariable Long agentId) {
        try {
            return ResponseEntity.ok(reclamationService.affecter(id, agentId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE /api/reclamations/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            reclamationService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
