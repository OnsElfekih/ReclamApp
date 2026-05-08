package projet.elfekih.ons.mapper;

import projet.elfekih.ons.dto.AgentSAVDTO;
import projet.elfekih.ons.dto.AgentSAVRequestDTO;
import projet.elfekih.ons.entities.AgentSAV;

public class AgentSAVMapper {

	public static AgentSAVDTO toDTO(AgentSAV agent) {

		AgentSAVDTO dto = new AgentSAVDTO();

		dto.setId(agent.getId());
		dto.setNom(agent.getNom());
		dto.setCompetence(agent.getCompetence());

		return dto;
	}

	public static AgentSAV toEntity(AgentSAVRequestDTO dto) {

		AgentSAV agent = new AgentSAV();

		agent.setNom(dto.getNom());
		agent.setCompetence(dto.getCompetence());

		return agent;
	}
}