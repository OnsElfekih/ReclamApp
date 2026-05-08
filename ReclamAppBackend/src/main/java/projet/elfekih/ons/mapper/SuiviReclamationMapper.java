package projet.elfekih.ons.mapper;

import projet.elfekih.ons.dto.SuiviReclamationDTO;
import projet.elfekih.ons.dto.SuiviReclamationRequestDTO;
import projet.elfekih.ons.entities.SuiviReclamation;

public class SuiviReclamationMapper {

	public static SuiviReclamationDTO toDTO(SuiviReclamation suivi) {

		SuiviReclamationDTO dto = new SuiviReclamationDTO();

		dto.setId(suivi.getId());
		dto.setMessage(suivi.getMessage());
		dto.setAction(suivi.getAction());
		dto.setDate(suivi.getDate());

		if (suivi.getReclamation() != null) {
			dto.setReclamationId(suivi.getReclamation().getId());
		}

		if (suivi.getAgentSAV() != null) {
			dto.setAgentId(suivi.getAgentSAV().getId());
			dto.setAgentNom(suivi.getAgentSAV().getNom());
		}

		return dto;
	}

	public static SuiviReclamation toEntity(SuiviReclamationRequestDTO dto) {

		SuiviReclamation suivi = new SuiviReclamation();

		suivi.setMessage(dto.getMessage());
		suivi.setAction(dto.getAction());

		return suivi;
	}
}