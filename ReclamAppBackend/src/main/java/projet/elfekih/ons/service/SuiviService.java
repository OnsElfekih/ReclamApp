package projet.elfekih.ons.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import projet.elfekih.ons.repository.*;
import projet.elfekih.ons.entities.*;

@Service
public class SuiviService {

	@Autowired
	private SuiviReclamationRepository suiviRepository;

	@Autowired
	private ReclamationRepository reclamationRepository;

	@Autowired
	private AgentSAVRepository agentRepository;

	public List<SuiviReclamation> findAll() {
		return suiviRepository.findAll();
	}

	public List<SuiviReclamation> findByReclamationId(Long reclamationId) {
		return suiviRepository.findByReclamationId(reclamationId);
	}

	public SuiviReclamation save(SuiviReclamation suivi) {
		Reclamation reclamation = reclamationRepository.findById(suivi.getReclamation().getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réclamation introuvable"));

		AgentSAV agent = agentRepository.findById(suivi.getAgentSAV().getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agent introuvable"));

		suivi.setReclamation(reclamation);
		suivi.setAgentSAV(agent);
		suivi.setDate(java.time.LocalDateTime.now());

		return suiviRepository.save(suivi);
	}

	public void deleteById(Long id) {
		suiviRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Suivi introuvable, id=" + id));

		suiviRepository.deleteById(id);
	}
}