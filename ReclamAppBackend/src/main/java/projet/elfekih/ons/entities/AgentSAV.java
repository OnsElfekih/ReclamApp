package projet.elfekih.ons.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "AgentsSAV")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentSAV {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Le nom est obligatoire")
	@Column(nullable = false, length = 100)
	private String nom;

	@NotEmpty(message = "La compétence est obligatoire")
	@Column(nullable = false, length = 100)
	private String competence;

	@OneToMany(mappedBy = "agentSAV")
	@JsonIgnore
	private List<Reclamation> reclamations;
}