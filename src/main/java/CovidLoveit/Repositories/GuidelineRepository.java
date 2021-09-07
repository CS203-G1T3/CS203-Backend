package CovidLoveit.Repositories;

import CovidLoveit.Domain.Models.Guideline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GuidelineRepository extends JpaRepository<Guideline, Integer> {
}
