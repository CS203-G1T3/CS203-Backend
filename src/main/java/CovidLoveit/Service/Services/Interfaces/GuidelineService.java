package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.InputModel.GuidelineInputModel;
import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Guideline;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GuidelineService {

    Guideline addGuideline(UUID clientId, GuidelineInputModel inputModel);

    Guideline updateGuideline(UUID clientId, GuidelineInputModel inputModel);

    void deleteGuideline(UUID clientId, UUID guidelineId);

    Optional<Guideline> getGuideline(UUID guidelineId);

    List<Guideline> getAllGuidelines();

}
