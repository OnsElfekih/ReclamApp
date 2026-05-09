package projet.elfekih.ons.entities;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Le nom est obligatoire")
	@Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
	@Column(nullable = false, length = 100)
	private String nom;

	@NotEmpty(message = "L'email est obligatoire")
	@Email(message = "Format email invalide")
	@Column(nullable = false, length = 100, unique = true)
	private String email;

	@Pattern(regexp = "^[0-9]{8}$", message = "Le téléphone doit contenir 8 chiffres")
	@Column(length = 8)
	private String telephone;

	@NotEmpty(message = "Le mot de passe est obligatoire")
	@Column(nullable = false)
	private String motDePasse;
	
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	@JsonIgnore // Pour éviter la sérialisation infinie
	private List<Reclamation> reclamations;
}