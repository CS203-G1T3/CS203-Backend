package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Guideline;

import java.util.Optional;
import java.util.UUID;

public interface GuidelineService {

    Guideline addGuideline(boolean canOperateOnSite, String canOperateOnSiteDetails, int groupSize, String groupSizeDetails, int covidTestingVaccinated, int covidTestingUnvaccinated, String covidTestingDetails, String contactTracing, String contactTracingDetails, int operatingCapacity, String operatingCapacityDetails, String operationGuidelines, String referenceLink);

    Guideline updateGuideline(int guidelineId, Guideline guideline);

    void deleteGuideline(int guidelineId);

    Optional<Guideline> getGuideline(int guidelineId);

}
