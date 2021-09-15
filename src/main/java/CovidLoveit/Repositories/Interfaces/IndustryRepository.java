package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IndustryRepository extends JpaRepository<Industry, UUID> {
}
