package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Guideline;

import java.util.UUID;

import CovidLoveit.Domain.Models.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GuidelineRepository extends JpaRepository<Guideline, UUID> {

    @Query("SELECT g1 FROM Guideline g1 where g1.industry = :industry and g1.createdAt=(SELECT max(g2.createdAt) FROM Guideline g2 where g2.industry = :industry)")
    Guideline findLatestGuidelineByIndustry(@Param("industry") Industry industry);
}
