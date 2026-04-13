package projet.elfekih.ons.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
public class AgentSAV {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false, unique = true, length = 25) 
	private String nom;
	private String competence;

}
