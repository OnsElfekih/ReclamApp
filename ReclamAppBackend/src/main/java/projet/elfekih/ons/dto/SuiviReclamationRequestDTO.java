package projet.elfekih.ons.dto;

import lombok.Data;

@Data
public class SuiviReclamationRequestDTO {

	private String message;

	private String action;

	private Long reclamationId;

	private Long agentId;
}