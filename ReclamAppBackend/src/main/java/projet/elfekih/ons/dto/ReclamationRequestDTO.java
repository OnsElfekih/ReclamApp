package projet.elfekih.ons.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import projet.elfekih.ons.entities.StatutReclamation;

@Data
public class ReclamationRequestDTO {

	@NotNull(message = "Le client est obligatoire")
	private Long clientId;

	@NotBlank(message = "Le produit est obligatoire")
	@Size(min = 2, max = 200, message = "Le produit doit contenir entre 2 et 200 caractères")
	private String produit;

	@NotBlank(message = "La description est obligatoire")
	@Size(min = 5, max = 500, message = "La description doit contenir entre 5 et 500 caractères")
	private String description;

	@Min(value = 0, message = "Note minimale : 0")
	@Max(value = 5, message = "Note maximale : 5")
	private Integer note;

	private StatutReclamation statut;
}