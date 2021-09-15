package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.RegisteredBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RegisteredBusinessRepository extends JpaRepository<RegisteredBusiness, UUID>{
}
