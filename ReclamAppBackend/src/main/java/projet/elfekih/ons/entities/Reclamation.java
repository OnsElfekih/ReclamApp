package projet.elfekih.ons.entities;

import java.time.LocalDate;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Reclamations")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Reclamation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "La description est obligatoire")
	@Size(max = 500)
	@Column(length = 500)
	private String description;

	@NotEmpty(message = "Le produit est obligatoire")
	@Column(nullable = false, length = 200)
	private String produit;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private StatutReclamation statut = StatutReclamation.OUVERTE;

	@Min(value = 0, message = "Note minimale : 0")
	@Max(value = 5, message = "Note maximale : 5")
	private Integer note;

	@Column(name = "date_reclamation")
	private LocalDate date = LocalDate.now();

	@ManyToOne
	@JoinColumn(name = "idClient", nullable = false)
	private Client client;

	@ManyToOne
	@JoinColumn(name = "idAgentSAV")
	private AgentSAV agentSAV;

}
