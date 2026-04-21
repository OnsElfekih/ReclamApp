package projet.elfekih.ons.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import projet.elfekih.ons.repository.*;
import projet.elfekih.ons.entities.*;

@Service
@RequiredArgsConstructor
public class ReclamationService {
    private final ReclamationRepository reclamationRepository;
    private final ClientRepository clientRepository;
    private final AgentSAVRepository agentRepository;
    
    public List<Reclamation> findAll() {
		return reclamationRepository.findAll();
	}
    public List<Reclamation> findByStatut(StatutReclamation statut) {
        return reclamationRepository.findByStatut(statut);
    }

    public List<Reclamation> findByClientId(Long clientId) {
        return reclamationRepository.findByClientId(clientId);
    }
    public Reclamation save(Reclamation reclamation) {
        // Vérifier que le client existe
        clientRepository.findById(reclamation.getClient().getId())
            .orElseThrow(() -> new RuntimeException("Client introuvable"));
        reclamation.setStatut(StatutReclamation.OUVERTE);
        reclamation.setDate(java.time.LocalDate.now());
        return reclamationRepository.save(reclamation);
    }
    public Reclamation update(Long id, Reclamation reclamation) {
        reclamationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Réclamation introuvable, id=" + id));
        reclamation.setId(id);
        return reclamationRepository.save(reclamation);
    }
    // Affectation d'un agent — statut passe à EN_COURS
    public Reclamation affecter(Long reclamationId, Long agentId) {
        Reclamation r = reclamationRepository.findById(reclamationId)
            .orElseThrow(() -> new RuntimeException("Réclamation introuvable, id=" + reclamationId));
        AgentSAV agent = agentRepository.findById(agentId)
            .orElseThrow(() -> new RuntimeException("Agent introuvable, id=" + agentId));
        r.setAgentSAV(agent);
        r.setStatut(StatutReclamation.EN_COURS);
        return reclamationRepository.save(r);  // save() = update si id existe — cours page 54
    }

    public void deleteById(Long id) {
        reclamationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Réclamation introuvable, id=" + id));
        reclamationRepository.deleteById(id);
    }
 // Rapport satisfaction
    public Map<String, Object> getRapport() {
        Double moyenne = reclamationRepository.findAverageNote();
        Long total = reclamationRepository.count();  // count() — cours page 49
        List<Object[]> parStatutRaw = reclamationRepository.countByStatut();

        Map<String, Long> parStatut = new LinkedHashMap<>();
        for (Object[] row : parStatutRaw) {
            parStatut.put(row[0].toString(), (Long) row[1]);
        }

        Map<String, Object> rapport = new LinkedHashMap<>();
        rapport.put("totalReclamations", total);
        rapport.put("moyenneNotes", moyenne != null ? Math.round(moyenne * 10.0) / 10.0 : 0);
        rapport.put("parStatut", parStatut);
        return rapport;
    }
}
