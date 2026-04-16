package projet.elfekih.ons.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import projet.elfekih.ons.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	Optional<Client> findByEmail(String email);
	boolean existsByEmail(String email);

}
