package projet.elfekih.ons.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import projet.elfekih.ons.dto.AgentSAVDTO;
import projet.elfekih.ons.dto.AgentSAVRequestDTO;
import projet.elfekih.ons.service.AgentSAVService;

@RestController
@RequestMapping("/api/agents")
@CrossOrigin(origins = "http://localhost:4200")
public class AgentSAVController {

	@Autowired
	private AgentSAVService agentService;

	@GetMapping
	public ResponseEntity<List<AgentSAVDTO>> findAll() {
		return ResponseEntity.ok(agentService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<AgentSAVDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(agentService.findById(id));
	}

	@PostMapping
	public ResponseEntity<AgentSAVDTO> save(@Valid @RequestBody AgentSAVRequestDTO dto) {
		AgentSAVDTO saved = agentService.save(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AgentSAVDTO> update(@PathVariable Long id, @Valid @RequestBody AgentSAVRequestDTO dto) {
		return ResponseEntity.ok(agentService.update(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		agentService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/search")
	public ResponseEntity<List<AgentSAVDTO>> search(@RequestParam String keyword) {
		return ResponseEntity.ok(agentService.search(keyword));
	}

	@GetMapping("/{id}/reclamations")
	public ResponseEntity<?> getReclamationsByAgent(@PathVariable Long id) {
		return ResponseEntity.ok(agentService.findReclamationsByAgent(id));
	}
}