package projet.elfekih.ons.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projet.elfekih.ons.entities.AgentSAV;

public interface AgentSAVRepository extends JpaRepository<AgentSAV, Long> {
	List <AgentSAV> findByCompetenceContainingIgnoreCase(String competence);
	List<AgentSAV> findByNomContainingIgnoreCase(String nom);
}
