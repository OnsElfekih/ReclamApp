package projet.elfekih.ons.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AgentSAVRequestDTO {

	@NotBlank(message = "Le nom est obligatoire")
	@Size(min = 3, max = 100, message = "Le nom doit contenir entre 3 et 100 caractères")
	private String nom;

	@NotBlank(message = "La compétence est obligatoire")
	@Size(min = 3, max = 100, message = "La compétence doit contenir entre 3 et 100 caractères")
	private String competence;
}