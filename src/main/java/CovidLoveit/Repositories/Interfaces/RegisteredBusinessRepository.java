package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Domain.Models.RegisteredBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RegisteredBusinessRepository extends JpaRepository<RegisteredBusiness, UUID>{

    @Query(value = "SELECT * FROM Registered_Business WHERE industry_id = :industryId", nativeQuery = true)
    List<RegisteredBusiness> findRegisteredBusinessByIndustry(@Param("industryId") UUID industryId);
}
