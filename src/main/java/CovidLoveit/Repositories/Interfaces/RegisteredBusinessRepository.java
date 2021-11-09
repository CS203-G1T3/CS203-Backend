package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Domain.Models.RegisteredBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RegisteredBusinessRepository extends JpaRepository<RegisteredBusiness, UUID>{

    @Query(value = "SELECT * FROM RegisteredBusiness WHERE industry_id = :industryId", nativeQuery = true)
    RegisteredBusiness findRegisteredBusinessByIndustry(@Param("industryId") UUID industryId);
}
