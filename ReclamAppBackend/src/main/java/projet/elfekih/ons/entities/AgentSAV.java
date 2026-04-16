package projet.elfekih.ons.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "AgentsSAV")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentSAV {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @NotEmpty(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100)
    @Column(nullable = false, length = 100)
	private String nom;
	
    @NotEmpty(message = "La compétence est obligatoire")
    @Column(nullable = false, length = 200)
    private String competence;

}
