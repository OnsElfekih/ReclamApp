package projet.elfekih.ons.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SuiviReclamationRequestDTO {

	@NotBlank(message = "Le message est obligatoire")
	@Size(max = 500, message = "Le message ne doit pas dépasser 500 caractères")
	private String message;

	@Size(max = 200, message = "L'action ne doit pas dépasser 200 caractères")
	private String action;

	@NotNull(message = "La réclamation est obligatoire")
	private Long reclamationId;

	@NotNull(message = "L'agent est obligatoire")
	private Long agentId;
}