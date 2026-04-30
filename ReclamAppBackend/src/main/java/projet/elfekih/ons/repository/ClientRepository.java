package projet.elfekih.ons.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import projet.elfekih.ons.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	Optional<Client> findByEmail(String email);

}
