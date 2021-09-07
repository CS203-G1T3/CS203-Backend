package CovidLoveit.Repositories;

import CovidLoveit.Domain.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Client, UUID> {
}
