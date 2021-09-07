package CovidLoveit.Service.Services;

import CovidLoveit.Domain.Models.Guideline;
import CovidLoveit.Exception.ResourceNotFoundException;
import CovidLoveit.Repositories.GuidelineRepository;
import CovidLoveit.Service.Services.Interfaces.IGuidelineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class GuidelineService implements IGuidelineService {

    private Logger logger = LoggerFactory.getLogger(GuidelineService.class);
    private GuidelineRepository guidelineRepository;

    @Autowired
    public GuidelineService(GuidelineRepository guidelineRepository) {this.guidelineRepository = guidelineRepository; }

    @Override
    public Guideline addGuideline(LocalDateTime createdAt, boolean canOperateOnSite,
                                  String canOperateOnSiteDetails, int groupSize,
                                  String groupSizeDetails, int covidTestingVaccinated,
                                  int covidTestingUnvaccinated, String covidTestingDetails,
                                  String contactTracing, String contactTracingDetails,
                                  int operatingCapacity, String operatingCapacityDetails,
                                  String operationGuidelines, String referenceLink) {
        var guideline = new Guideline(createdAt, canOperateOnSite, canOperateOnSiteDetails, groupSizeDetails, groupSizeDetails, covidTestingVaccinated, covidTestingUnvaccinated, covidTestingDetails, contactTracing, contactTracingDetails, operatingCapacity, operatingCapacityDetails, operationGuidelines, referenceLink);
        var savedGuideline = guidelineRepository.save(guideline);

        logger.info(String.format("Successfully added guideline %s", savedGuideline.getGuidelineId()));

        return savedGuideline;
    }

    @Override
    public Guideline updateGuideline(Guideline guideline) {
        Optional<Guideline> guidelineRecord = guidelineRepository.findById(guideline.getGuidelineId());

        if (guidelineRecord.isPresent()) {
            Guideline guidelineUpdate = guidelineRecord.get();
            guidelineUpdate.setCanOperateOnSite(guideline.isCanOperateOnSite());
            guidelineUpdate.setCanOperateOnSiteDetails(guideline.isCanOperateOnSiteDetails());
            guidelineUpdate.setGroupSize(guideline.getGroupSize());
            guidelineUpdate.setGroupSizeDetails(guideline.getGroupSizeDetails());
            guidelineUpdate.setCovidTestingVaccinated(guideline.getCovidTestingVaccinated());
            guidelineUpdate.setCovidTestingUnvaccinated(guideline.getCovidTestingUnvaccinated());
            guidelineUpdate.setCovidTestingDetails(guideline.getCovidTestingDetails());
            guidelineUpdate.setContactTracing(guideline.getContactTracing());
            guidelineUpdate.setContactTracingDetails(guideline.getContactTracingDetails());
            guidelineUpdate.setOperatingCapacity(guideline.getOperatingCapacity());
            guidelineUpdate.setOperatingCapacityDetails(guideline.getOperatingCapacityDetails());
            guidelineUpdate.setOperationGuidelines(guideline.getOperationGuidelines());
            guidelineUpdate.setReferenceLink(guideline.getReferenceLink());

            guidelineRepository.save(guidelineUpdate);
            return guidelineUpdate;

        } else {
            logger.error(String.format("Guideline with ID %s does not exist in database.", guideline.getGuidelineId()));
            throw new ResourceNotFoundException(String.format("Guideline ID is not found.", guideline.getGuidelineId()));
        }
    }

    @Override
    public void deleteGuideline(Integer guidelineId) {
        Optional<Guideline> guidelineOptional = guidelineRepository.findById(guidelineId);

        if (guidelineOptional.isPresent()) {
            guidelineRepository.delete(guidelineOptional.get());
        } else {
            logger.error(String.format("Guideline with ID %s does not exist in database.", guidelineId));
            throw new ResourceNotFoundException(String.format("Guideline with ID %s not found.", guidelineId));
        }
    }
}
