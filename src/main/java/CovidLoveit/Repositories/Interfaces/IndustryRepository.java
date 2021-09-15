package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Industry;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface IndustryRepository extends CrudRepository<Industry, UUID> {
}
