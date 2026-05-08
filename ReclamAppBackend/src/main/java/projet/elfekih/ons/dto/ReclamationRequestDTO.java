package projet.elfekih.ons.dto;

import lombok.Data;
import projet.elfekih.ons.entities.StatutReclamation;

@Data
public class ReclamationRequestDTO {
	private Long clientId;
	private String produit;
	private String description;
	private Integer note;
	private StatutReclamation statut;
}