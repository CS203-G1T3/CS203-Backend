package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.Models.Guideline;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IGuidelineService {

    Guideline addGuideline(LocalDateTime createdAt, boolean canOperateOnSite, String canOperateOnSiteDetails, int groupSize, String groupSizeDetails, int covidTestingVaccinated, int covidTestingUnvaccinated, String covidTestingDetails, String contactTracing, String contactTracingDetails, int operatingCapacity, String operatingCapacityDetails, String operationGuidelines, String referenceLink);

    Guideline updateGuideline(Guideline guideline);

    void deleteGuideline(Integer guidelineId);

}
