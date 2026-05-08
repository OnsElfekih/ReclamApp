package projet.elfekih.ons.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import projet.elfekih.ons.entities.*;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
	List<Reclamation> findByClientId(Long clientId);

	List<Reclamation> findByAgentSAVId(Long agentSAVId);

	List<Reclamation> findByStatut(StatutReclamation statut);

	@Query("SELECT AVG(r.note) FROM Reclamation r WHERE r.note IS NOT NULL")
	Double findAverageNote();

	@Query("SELECT r.statut, COUNT(r) FROM Reclamation r GROUP BY r.statut")
	List<Object[]> countByStatut();

	List<Reclamation> findByAgentSAVNomContainingIgnoreCase(String nom);

	List<Reclamation> findByClientNomContainingIgnoreCase(String nom);
}
