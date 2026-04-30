package projet.elfekih.ons.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public List<SuiviReclamation> findByReclamationId(Long reclamationId) {
        return suiviRepository.findByReclamationId(reclamationId);
    }
    public SuiviReclamation save(SuiviReclamation suivi) {
        Reclamation reclamation = reclamationRepository.findById(suivi.getReclamation().getId())
            .orElseThrow(() -> new RuntimeException("Réclamation introuvable"));

        AgentSAV agent = agentRepository.findById(suivi.getAgentSAV().getId())
            .orElseThrow(() -> new RuntimeException("Agent introuvable"));

        suivi.setReclamation(reclamation);
        suivi.setAgentSAV(agent);
        suivi.setDate(java.time.LocalDateTime.now());

        return suiviRepository.save(suivi);
    }

    public void deleteById(Long id) {
        suiviRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Suivi introuvable, id=" + id));
        suiviRepository.deleteById(id);
    }

}
