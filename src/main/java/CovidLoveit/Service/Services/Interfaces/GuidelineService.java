package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.InputModel.GuidelineInputModel;
import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Guideline;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GuidelineService {

    Guideline addGuideline(String clientId, GuidelineInputModel inputModel);

    Guideline updateGuideline(String clientId, GuidelineInputModel inputModel);

    void deleteGuideline(String clientId, int guidelineId);

    Optional<Guideline> getGuideline(int guidelineId);

    List<Guideline> getAllGuidelines();

}
