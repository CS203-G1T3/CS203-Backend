package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}
