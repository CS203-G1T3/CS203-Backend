package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.Models.Guideline;

public interface IGuidelineService {

    Guideline addGuideline(boolean canOperateOnSite, String canOperateOnSiteDetails, int groupSize, String groupSizeDetails, int covidTestingVaccinated, int covidTestingUnvaccinated, String covidTestingDetails, String contactTracing, String contactTracingDetails, int operatingCapacity, String operatingCapacityDetails, String operationGuidelines, String referenceLink);

    Guideline updateGuideline(Guideline guideline);

    void deleteGuideline(Integer guidelineId);

}
