package projet.elfekih.ons.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import projet.elfekih.ons.entities.AgentSAV;
import projet.elfekih.ons.service.AgentSAVService;

@RestController
@RequestMapping("/api/agents")
@CrossOrigin(origins = "http://localhost:4200")
public class AgentSAVController {
	@Autowired
	private AgentSAVService agentService;
	 @GetMapping
	    public ResponseEntity<List<AgentSAV>> findAll() {
	        return ResponseEntity.ok(agentService.findAll());
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<AgentSAV> findById(@PathVariable Long id) {
	        try {
	            return ResponseEntity.ok(agentService.findById(id));
	        } catch (RuntimeException e) {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    @PostMapping
	    public ResponseEntity<AgentSAV> save(@Valid @RequestBody AgentSAV agent) {
	        AgentSAV saved = agentService.save(agent);
	        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<AgentSAV> update(
	            @PathVariable Long id,
	            @Valid @RequestBody AgentSAV agent) {
	        try {
	            return ResponseEntity.ok(agentService.update(id, agent));
	        } catch (RuntimeException e) {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
	        try {
	            agentService.deleteById(id);
	            return ResponseEntity.noContent().build();
	        } catch (RuntimeException e) {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    @GetMapping("/search")
	    public List<AgentSAV> search(@RequestParam String keyword) {
	        return agentService.search(keyword);
	    }
}
