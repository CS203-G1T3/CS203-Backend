package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Guideline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGuidelineRepository extends JpaRepository<Guideline, Integer> {
}
