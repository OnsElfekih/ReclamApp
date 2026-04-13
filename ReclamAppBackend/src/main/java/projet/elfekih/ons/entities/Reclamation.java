package projet.elfekih.ons.entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
public class Reclamation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String description;
	private String statut;
	private int note;
	private Date dateReclamation;
	
	@ManyToOne
	@JoinColumn(name="idClient")
	private Client client;
	
	@ManyToOne
	@JoinColumn(name="idAgentSAV")
	private AgentSAV agentSAV;
	

}
