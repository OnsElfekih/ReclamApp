package projet.elfekih.ons.mapper;

import projet.elfekih.ons.dto.ReclamationDTO;
import projet.elfekih.ons.dto.ReclamationRequestDTO;
import projet.elfekih.ons.entities.Reclamation;

public class ReclamationMapper {

	public static ReclamationDTO toDTO(Reclamation reclamation) {

		ReclamationDTO dto = new ReclamationDTO();

		dto.setId(reclamation.getId());
		dto.setProduit(reclamation.getProduit());
		dto.setDescription(reclamation.getDescription());
		dto.setStatut(reclamation.getStatut());
		dto.setDate(reclamation.getDate());
		dto.setNote(reclamation.getNote());

		if (reclamation.getClient() != null) {
			dto.setClientId(reclamation.getClient().getId());
			dto.setClientNom(reclamation.getClient().getNom());
		}

		if (reclamation.getAgentSAV() != null) {
			dto.setAgentId(reclamation.getAgentSAV().getId());
			dto.setAgentNom(reclamation.getAgentSAV().getNom());
		}

		return dto;
	}

	public static Reclamation toEntity(ReclamationRequestDTO dto) {

		Reclamation reclamation = new Reclamation();

		reclamation.setProduit(dto.getProduit());
		reclamation.setDescription(dto.getDescription());
		reclamation.setNote(dto.getNote());
		reclamation.setStatut(dto.getStatut());

		return reclamation;
	}
}