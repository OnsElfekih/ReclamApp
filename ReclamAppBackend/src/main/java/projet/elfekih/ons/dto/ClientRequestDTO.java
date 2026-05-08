package projet.elfekih.ons.dto;

import lombok.Data;

@Data
public class ClientRequestDTO {
	private String nom;
	private String email;
	private String telephone;
	private String motDePasse;
}