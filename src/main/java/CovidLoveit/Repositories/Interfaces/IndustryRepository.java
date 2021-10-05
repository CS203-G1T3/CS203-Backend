package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;
import java.util.List;

public interface IndustryRepository extends JpaRepository<Industry, UUID> {
    @Query("SELECT DISTINCT industryName FROM Industry")
    List<String> findDistinctIndustryName();

    List<Industry> findByIndustryName(String industryName);

    @Query("SELECT industrySubtype FROM Industry")
    List<Industry> getIndustrySubtypes();

    @Query("SELECT industrySubtype FROM Industry WHERE industryName = :#{#industry_name}")
    List<Industry> findIndustrySubtypesByIndustryName(@Param("industry_name") String industryName);
}