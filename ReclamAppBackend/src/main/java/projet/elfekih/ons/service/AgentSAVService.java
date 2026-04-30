package projet.elfekih.ons.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;
import projet.elfekih.ons.entities.AgentSAV;
import projet.elfekih.ons.repository.AgentSAVRepository;

@Service
public class AgentSAVService {
	@Autowired
	private AgentSAVRepository agentRepository;
	
    public List<AgentSAV> findAll() {
        return agentRepository.findAll();
    }

    public AgentSAV findById(Long id) {
        return agentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Agent introuvable, id=" + id
            ));
    }

    public AgentSAV save(AgentSAV agent) {
        return agentRepository.save(agent);
    }

    public AgentSAV update(Long id, AgentSAV agent) {
        agentRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Agent introuvable, id=" + id
            ));
        agent.setId(id);
        return agentRepository.save(agent);
    }
    public void deleteById(Long id) {
        agentRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Agent introuvable, id=" + id
            ));
        agentRepository.deleteById(id);
    }
}
