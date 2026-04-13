package projet.elfekih.ons.entities;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
public class SuiviReclamation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String message;
	private String action;
	private Date dataSuivi;
	
	@ManyToOne
	@JoinColumn(name="idReclamation")
	private Reclamation reclamation;
	
	@ManyToOne
	@JoinColumn(name="idAgentSAV")
	private AgentSAV agentSAV;
}
