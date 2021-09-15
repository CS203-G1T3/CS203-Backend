package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.RegisteredBusiness;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface RegisteredBusinessRepository extends CrudRepository<RegisteredBusiness, UUID>{
}
