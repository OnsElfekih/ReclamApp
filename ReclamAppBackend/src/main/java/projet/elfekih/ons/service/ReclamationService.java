package projet.elfekih.ons.service;
//Import LinkedHashMap pour garder l'ordre d'insertion
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import projet.elfekih.ons.dto.ReclamationDTO;
import projet.elfekih.ons.dto.ReclamationRequestDTO;
import projet.elfekih.ons.entities.*;
import projet.elfekih.ons.mapper.ReclamationMapper;
import projet.elfekih.ons.repository.*;

@Service
public class ReclamationService {

	@Autowired
	private ReclamationRepository reclamationRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private AgentSAVRepository agentRepository;

	public List<ReclamationDTO> findAll() {
		return reclamationRepository.findAll()
				.stream()
				.map(ReclamationMapper::toDTO)
				.toList();
	}

	public ReclamationDTO findById(Long id) {
		Reclamation reclamation = reclamationRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"Réclamation introuvable, id=" + id
				));

		return ReclamationMapper.toDTO(reclamation);
	}

	public List<ReclamationDTO> findByStatut(StatutReclamation statut) {
		return reclamationRepository.findByStatut(statut)
				.stream()
				.map(ReclamationMapper::toDTO)
				.toList();
	}

	public List<ReclamationDTO> findByClientId(Long clientId) {
		return reclamationRepository.findByClientId(clientId)
				.stream()
				.map(ReclamationMapper::toDTO)
				.toList();
	}

	public ReclamationDTO save(ReclamationRequestDTO dto) {
		Client client = clientRepository.findById(dto.getClientId())
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"Client introuvable"
				));

		Reclamation reclamation = ReclamationMapper.toEntity(dto);

		reclamation.setClient(client);
		// Statut initial automatique
		reclamation.setStatut(StatutReclamation.OUVERTE);
		reclamation.setDate(java.time.LocalDate.now());

		Reclamation saved = reclamationRepository.save(reclamation);

		return ReclamationMapper.toDTO(saved);
	}

	public ReclamationDTO update(Long id, ReclamationRequestDTO dto) {
		Reclamation existing = reclamationRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"Réclamation introuvable, id=" + id
				));

		Client client = clientRepository.findById(dto.getClientId())
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"Client introuvable"
				));

		existing.setClient(client);
		existing.setProduit(dto.getProduit());
		existing.setDescription(dto.getDescription());
		existing.setNote(dto.getNote());

		if (dto.getStatut() != null) {
			existing.setStatut(dto.getStatut());
		}

		Reclamation updated = reclamationRepository.save(existing);

		return ReclamationMapper.toDTO(updated);
	}

	public ReclamationDTO affecter(Long reclamationId, Long agentId) {
		Reclamation reclamation = reclamationRepository.findById(reclamationId)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"Réclamation introuvable, id=" + reclamationId
				));

		AgentSAV agent = agentRepository.findById(agentId)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"Agent introuvable, id=" + agentId
				));

		reclamation.setAgentSAV(agent);
		reclamation.setStatut(StatutReclamation.EN_COURS);

		Reclamation updated = reclamationRepository.save(reclamation);

		return ReclamationMapper.toDTO(updated);
	}

	public void deleteById(Long id) {
		reclamationRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"Réclamation introuvable, id=" + id
				));

		try {
			reclamationRepository.deleteById(id);
		} catch (Exception e) {
			throw new ResponseStatusException(
					HttpStatus.CONFLICT,
					"Impossible de supprimer cette réclamation car elle possède des suivis"
			);
		}
	}
// car le rapport contient des types de données diff
	public Map<String, Object> getRapport() {
		Double moyenne = reclamationRepository.findAverageNote();
		Long total = reclamationRepository.count();
		List<Object[]> parStatutRaw = reclamationRepository.countByStatut();
		//linkedHashMap pour garder l'ordre d'insertion des statuts
		Map<String, Long> parStatut = new LinkedHashMap<>();

		for (Object[] row : parStatutRaw) {
			parStatut.put(row[0].toString(), (Long) row[1]);
		}

		Map<String, Object> rapport = new LinkedHashMap<>();
		rapport.put("totalReclamations", total);
		rapport.put("moyenneNotes", moyenne != null ? Math.round(moyenne * 10.0) / 10.0 : 0);
		rapport.put("parStatut", parStatut);

		return rapport;
	}

	public List<ReclamationDTO> search(
			String agentNom,
			String clientNom,
			String produit,
			StatutReclamation statut,
			String date
	) {

		return reclamationRepository.findAll()
				.stream()

				.filter(r -> agentNom == null || agentNom.isEmpty()
						|| (r.getAgentSAV() != null
						&& r.getAgentSAV().getNom().toLowerCase().contains(agentNom.toLowerCase())))

				.filter(r -> clientNom == null || clientNom.isEmpty()
						|| (r.getClient() != null
						&& r.getClient().getNom().toLowerCase().contains(clientNom.toLowerCase())))

				.filter(r -> produit == null || produit.isEmpty()
						|| (r.getProduit() != null
						&& r.getProduit().toLowerCase().contains(produit.toLowerCase())))

				.filter(r -> statut == null || r.getStatut() == statut)

				.filter(r -> date == null || date.isEmpty()
						|| r.getDate().toString().equals(date))

				.map(ReclamationMapper::toDTO)
				.toList();
	}
}