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
@Builder
public class Reclamation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
    @NotBlank(message = "La description est obligatoire")
	@Column(nullable= false, length = 500)
	private String description;
    
    @Column(nullable= false, length = 25)
    @NotBlank(message = "Le produit est obligatoire")
    private String produit;
    
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private StatutReclamation statut = StatutReclamation.OUVERTE;

    @Min(value = 1, message = "Note minimale : 1")
    @Max(value = 5, message = "Note maximale : 5")
	private int note;
    
    @Builder.Default
	private LocalDate dateReclamation = LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name="idClient")
	private Client client;
	
	@ManyToOne
	@JoinColumn(name="idAgentSAV")
	private AgentSAV agentSAV;
	

}
