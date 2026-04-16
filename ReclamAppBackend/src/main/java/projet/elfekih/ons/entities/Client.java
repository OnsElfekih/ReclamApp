package projet.elfekih.ons.entities;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
@Entity
@Table(name = "Clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, unique = true, length = 25)
	@NotBlank(message = "Le nom est obligatoire")
	private String nom;
	
	@NotBlank(message = "L'email est obligatoire")
	@Email(message = "Format email invalide")
	@Column(nullable = false, unique = true, length = 50)
	private String email;
	
	@NotBlank(message = "Le telephone est obligatoire")
	@Pattern(regexp = "^[0-9]{8}$", message = "Téléphone doit contenir 8 chiffres")
	private String telephone;
	
}
