package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.InputModel.GuidelineInputModel;
import CovidLoveit.Domain.Models.Guideline;

import java.util.List;

public interface GuidelineService {

    Guideline addGuideline(String clientId, GuidelineInputModel inputModel);

    Guideline updateGuideline(String clientId, GuidelineInputModel inputModel);

    void deleteGuideline(String clientId, String guidelineId);

    Guideline getGuideline(String guidelineId);

    Guideline getLatestGuidelineByIndustry(String industryId);

    List<Guideline> getAllGuidelines();

}
