package projet.elfekih.ons.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import projet.elfekih.ons.entities.SuiviReclamation;
import projet.elfekih.ons.service.SuiviService;

@RestController
@RequestMapping("/api/suivis")
@CrossOrigin(origins = "http://localhost:4200")
public class SuiviController {
	@Autowired
    private SuiviService suiviService;

    // GET /api/suivis/reclamation/{reclamationId}
    @GetMapping("/reclamation/{reclamationId}")
    public ResponseEntity<List<SuiviReclamation>> findByReclamation(
            @PathVariable Long reclamationId) {
        return ResponseEntity.ok(suiviService.findByReclamationId(reclamationId));
    }
    
    // POST /api/suivis
    // Body : { "message": "...", "action": "...", "reclamation": {"id": 1}, "agent": {"id": 2} }
    @PostMapping
    public ResponseEntity<SuiviReclamation> save(
            @Valid @RequestBody SuiviReclamation suivi) {
        try {
            SuiviReclamation saved = suiviService.save(suivi);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();}
    }
    
    // DELETE /api/suivis/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            suiviService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


}
