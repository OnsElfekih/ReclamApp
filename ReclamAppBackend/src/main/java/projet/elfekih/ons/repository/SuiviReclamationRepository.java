package projet.elfekih.ons.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projet.elfekih.ons.entities.SuiviReclamation;

public interface SuiviReclamationRepository extends JpaRepository<SuiviReclamation, Long> {
	List<SuiviReclamation> findByReclamationId(Long reclamationId);

	List<SuiviReclamation> findByAgentSAVId(Long agentSAVId);

}
