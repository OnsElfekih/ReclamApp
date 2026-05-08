package projet.elfekih.ons.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "SuiviReclamations")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class SuiviReclamation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Le message est obligatoire")
	@Column(nullable = false, length = 500)
	private String message;

	@Column(length = 200)
	private String action;

	@Column(name = "date_suivi")
	private LocalDateTime date = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name = "idReclamation", nullable = false)
	private Reclamation reclamation;

	@ManyToOne
	@JoinColumn(name = "idAgentSAV")
	private AgentSAV agentSAV;
}
