package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;
import java.util.List;

public interface IndustryRepository extends JpaRepository<Industry, UUID> {
    @Query("SELECT DISTINCT industryName FROM Industry")
    List<String> findDistinctIndustryName();

    List<Industry> findIndustryByIndustryName(String industryName);
}