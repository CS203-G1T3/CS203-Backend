package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Guideline;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuidelineRepository extends JpaRepository<Guideline, UUID> {
}
