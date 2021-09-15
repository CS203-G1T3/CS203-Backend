package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Guideline;
import org.springframework.data.repository.CrudRepository;

public interface GuidelineRepository extends CrudRepository<Guideline, Integer> {
}
