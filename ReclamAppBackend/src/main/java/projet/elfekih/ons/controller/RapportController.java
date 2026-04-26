package projet.elfekih.ons.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import projet.elfekih.ons.service.ReclamationService;

@RestController
@RequestMapping("/api/rapport")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class RapportController {
    private final ReclamationService reclamationService;

    // GET /api/rapport/satisfaction
    // Retourne : { totalReclamations, moyenneNotes, parStatut }
    @GetMapping("/satisfaction")
    public ResponseEntity<Map<String, Object>> getSatisfaction() {
        return ResponseEntity.ok(reclamationService.getRapport());
    }

}
