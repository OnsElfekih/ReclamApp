package projet.elfekih.ons.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SuiviReclamationDTO {
	private Long id;
	private String message;
	private String action;
	private LocalDateTime date;

	private Long reclamationId;
	private Long agentId;
	private String agentNom;
}