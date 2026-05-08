package projet.elfekih.ons.dto;

import java.time.LocalDate;

import lombok.Data;
import projet.elfekih.ons.entities.StatutReclamation;

@Data
public class ReclamationDTO {
	private Long id;
	private String produit;
	private String description;
	private StatutReclamation statut;
	private LocalDate date;
	private Integer note;

	private Long clientId;
	private String clientNom;

	private Long agentId;
	private String agentNom;
}