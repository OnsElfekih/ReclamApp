package projet.elfekih.ons.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Table(name = "AgentsSAV")
@Data
@AllArgsConstructor
@Builder
public class AgentSAV {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, unique = true, length = 25)
	@NotBlank(message = "Le nom est obligatoire")
	private String nom;
	
	@NotEmpty(message = "La compétence est obligatoire")
	private String competence;

}
