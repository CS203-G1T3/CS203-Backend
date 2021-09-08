package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Client;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface IClientRepository extends CrudRepository<Client, UUID> {
}
