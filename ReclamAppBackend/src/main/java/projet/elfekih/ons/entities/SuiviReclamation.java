package projet.elfekih.ons.entities;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "SuiviReclamations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuiviReclamation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "Le message est obligatoire")
	private String message;
	
	private String action;
	
	@Builder.Default
	private LocalDateTime dateSuivi = LocalDateTime.now();
	
	@ManyToOne
	@JoinColumn(name="idReclamation")
	private Reclamation reclamation;
	
	@ManyToOne
	@JoinColumn(name="idAgentSAV")
	private AgentSAV agentSAV;
}
