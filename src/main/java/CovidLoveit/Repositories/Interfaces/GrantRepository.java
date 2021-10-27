package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Grant;
import CovidLoveit.Domain.Models.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GrantRepository extends JpaRepository<Grant, UUID> {

    List<Grant> findGrantsByIndustries(Industry industry);

    Optional<Grant> findGrantByGrantName(String grantName);
}
