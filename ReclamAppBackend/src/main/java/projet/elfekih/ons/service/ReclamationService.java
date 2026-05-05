package projet.elfekih.ons.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.elfekih.ons.repository.*;
import projet.elfekih.ons.entities.*;

@Service
public class ReclamationService {
	@Autowired
    private ReclamationRepository reclamationRepository;
	@Autowired
    private ClientRepository clientRepository;
	@Autowired
    private AgentSAVRepository agentRepository;
    
    public List<Reclamation> findAll() {
		return reclamationRepository.findAll();
	}
    public Reclamation findById(Long id) {
        return reclamationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Réclamation introuvable, id=" + id));
    }
    public List<Reclamation> findByStatut(StatutReclamation statut) {
        return reclamationRepository.findByStatut(statut);
    }

    public List<Reclamation> findByClientId(Long clientId) {
        return reclamationRepository.findByClientId(clientId);
    }
    
    public Reclamation save(Reclamation reclamation) {
        Client client = clientRepository.findById(reclamation.getClient().getId())
            .orElseThrow(() -> new RuntimeException("Client introuvable"));

        reclamation.setClient(client);
        reclamation.setStatut(StatutReclamation.OUVERTE);
        reclamation.setDate(java.time.LocalDate.now());

        return reclamationRepository.save(reclamation);
    }
    
    public Reclamation update(Long id, Reclamation reclamation) {
        Reclamation existing = reclamationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Réclamation introuvable, id=" + id));

        Client client = clientRepository.findById(reclamation.getClient().getId())
            .orElseThrow(() -> new RuntimeException("Client introuvable"));

        existing.setClient(client);
        existing.setProduit(reclamation.getProduit());
        existing.setDescription(reclamation.getDescription());
        existing.setStatut(reclamation.getStatut());
        existing.setNote(reclamation.getNote());

        return reclamationRepository.save(existing);
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
    public List<Reclamation> search(String agentNom, String clientNom, StatutReclamation statut, String date) {

        return reclamationRepository.findAll().stream()

            .filter(r -> agentNom == null || agentNom.isEmpty() ||
                (r.getAgentSAV() != null &&
                 r.getAgentSAV().getNom().toLowerCase().contains(agentNom.toLowerCase())))

            .filter(r -> clientNom == null || clientNom.isEmpty() ||
                (r.getClient() != null &&
                 r.getClient().getNom().toLowerCase().contains(clientNom.toLowerCase())))

            .filter(r -> statut == null ||
                r.getStatut() == statut)

            .filter(r -> date == null || date.isEmpty() ||
                r.getDate().toString().equals(date))

            .toList();
    }
}
