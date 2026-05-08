package projet.elfekih.ons.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import projet.elfekih.ons.dto.AgentSAVDTO;
import projet.elfekih.ons.dto.AgentSAVRequestDTO;
import projet.elfekih.ons.entities.AgentSAV;
import projet.elfekih.ons.mapper.AgentSAVMapper;
import projet.elfekih.ons.repository.AgentSAVRepository;

@Service
public class AgentSAVService {

	@Autowired
	private AgentSAVRepository agentRepository;

	public List<AgentSAVDTO> findAll() {
		return agentRepository.findAll().stream().map(AgentSAVMapper::toDTO).toList();
	}

	public AgentSAVDTO findById(Long id) {
		AgentSAV agent = agentRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agent introuvable, id=" + id));

		return AgentSAVMapper.toDTO(agent);
	}

	public AgentSAVDTO save(AgentSAVRequestDTO dto) {
		AgentSAV agent = AgentSAVMapper.toEntity(dto);
		AgentSAV saved = agentRepository.save(agent);

		return AgentSAVMapper.toDTO(saved);
	}

	public AgentSAVDTO update(Long id, AgentSAVRequestDTO dto) {
		AgentSAV existing = agentRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agent introuvable, id=" + id));

		existing.setNom(dto.getNom());
		existing.setCompetence(dto.getCompetence());

		AgentSAV updated = agentRepository.save(existing);

		return AgentSAVMapper.toDTO(updated);
	}

	public void deleteById(Long id) {
		agentRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agent introuvable, id=" + id));

		try {
			agentRepository.deleteById(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"Impossible de supprimer cet agent car il est lié à des réclamations ou suivis");
		}
	}

	public List<AgentSAVDTO> search(String keyword) {
		return agentRepository.findByNomContainingIgnoreCase(keyword).stream().map(AgentSAVMapper::toDTO).toList();
	}
}