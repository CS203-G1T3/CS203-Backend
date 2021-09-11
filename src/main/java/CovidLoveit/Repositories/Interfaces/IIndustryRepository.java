package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Industry;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface IIndustryRepository extends CrudRepository<Industry, UUID> {
}
