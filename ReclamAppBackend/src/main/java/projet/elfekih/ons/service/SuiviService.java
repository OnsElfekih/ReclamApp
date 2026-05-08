package projet.elfekih.ons.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import projet.elfekih.ons.dto.SuiviReclamationDTO;
import projet.elfekih.ons.dto.SuiviReclamationRequestDTO;
import projet.elfekih.ons.entities.AgentSAV;
import projet.elfekih.ons.entities.Reclamation;
import projet.elfekih.ons.entities.SuiviReclamation;
import projet.elfekih.ons.mapper.SuiviReclamationMapper;
import projet.elfekih.ons.repository.AgentSAVRepository;
import projet.elfekih.ons.repository.ReclamationRepository;
import projet.elfekih.ons.repository.SuiviReclamationRepository;

@Service
public class SuiviService {

	@Autowired
	private SuiviReclamationRepository suiviRepository;

	@Autowired
	private ReclamationRepository reclamationRepository;

	@Autowired
	private AgentSAVRepository agentRepository;

	public List<SuiviReclamationDTO> findAll() {
		return suiviRepository.findAll().stream().map(SuiviReclamationMapper::toDTO).toList();
	}

	public List<SuiviReclamationDTO> findByReclamationId(Long reclamationId) {
		return suiviRepository.findByReclamationId(reclamationId).stream().map(SuiviReclamationMapper::toDTO).toList();
	}

	public SuiviReclamationDTO save(SuiviReclamationRequestDTO dto) {

		Reclamation reclamation = reclamationRepository.findById(dto.getReclamationId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réclamation introuvable"));

		AgentSAV agent = agentRepository.findById(dto.getAgentId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agent introuvable"));

		SuiviReclamation suivi = SuiviReclamationMapper.toEntity(dto);

		suivi.setReclamation(reclamation);
		suivi.setAgentSAV(agent);
		suivi.setDate(java.time.LocalDateTime.now());

		SuiviReclamation saved = suiviRepository.save(suivi);

		return SuiviReclamationMapper.toDTO(saved);
	}

	public void deleteById(Long id) {
		suiviRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Suivi introuvable, id=" + id));

		suiviRepository.deleteById(id);
	}
}