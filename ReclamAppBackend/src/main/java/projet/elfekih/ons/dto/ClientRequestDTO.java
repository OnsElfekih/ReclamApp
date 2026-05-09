package projet.elfekih.ons.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClientRequestDTO {

	@NotBlank(message = "Le nom est obligatoire")
	@Size(min = 3, max = 100, message = "Le nom doit contenir entre 3 et 100 caractères")
	private String nom;

	@NotBlank(message = "L'email est obligatoire")
	@Email(message = "Format email invalide")
	private String email;

	@NotBlank(message = "Le téléphone est obligatoire")
	@Pattern(regexp = "^[0-9]{8}$", message = "Le téléphone doit contenir exactement 8 chiffres")
	private String telephone;

	@Size(max = 100, message = "Le mot de passe ne doit pas dépasser 100 caractères")
	private String motDePasse;
}