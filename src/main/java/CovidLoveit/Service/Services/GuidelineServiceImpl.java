package CovidLoveit.Service.Services;

import CovidLoveit.Domain.Models.Guideline;
import CovidLoveit.Exception.GuidelineNotFoundException;
import CovidLoveit.Repositories.Interfaces.GuidelineRepository;
import CovidLoveit.Service.Services.Interfaces.GuidelineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class GuidelineServiceImpl implements GuidelineService {

    private Logger logger = LoggerFactory.getLogger(GuidelineServiceImpl.class);
    private GuidelineRepository guidelineRepository;

    @Autowired
    public GuidelineServiceImpl(GuidelineRepository guidelineRepository) {this.guidelineRepository = guidelineRepository; }

    @Override
    public Guideline addGuideline(boolean canOperateOnSite, String canOperateOnSiteDetails, int groupSize,
                                  String groupSizeDetails, int covidTestingVaccinated, int covidTestingUnvaccinated,
                                  String covidTestingDetails, String contactTracing, String contactTracingDetails,
                                  int operatingCapacity, String operatingCapacityDetails, String operationGuidelines,
                                  String referenceLink)
    {
        var guideline = new Guideline(canOperateOnSite,
                canOperateOnSiteDetails, groupSize, groupSizeDetails,
                covidTestingVaccinated, covidTestingUnvaccinated, covidTestingDetails,
                contactTracing, contactTracingDetails, operatingCapacity, operatingCapacityDetails,
                operationGuidelines, referenceLink);

        var savedGuideline = guidelineRepository.save(guideline);

        logger.info(String.format("Successfully added guideline {%d}", savedGuideline.getGuidelineId()));
        return savedGuideline;
    }

    @Override
    public Guideline updateGuideline(int guidelineId, Guideline guideline) {
        Optional<Guideline> guidelineOptional = guidelineRepository.findById(guidelineId);

        guidelineOptional.orElseThrow(() -> {
            logger.warn(String.format("Guideline with Id {%d} does not exist in DB.", guidelineId));
            throw new GuidelineNotFoundException(String.format("Guideline ID {%d} is not found.", guidelineId));
        });

        Guideline guidelineRecord = guidelineRepository.save(guideline);
        logger.info(String.format("Successfully updated Guideline {%d}", guidelineId));
        return guidelineRecord;
    }

    @Override
    public void deleteGuideline(int guidelineId) {
        Optional<Guideline> guidelineOptional = guidelineRepository.findById(guidelineId);

        guidelineOptional.orElseThrow(() -> {
            logger.warn(String.format("Guideline with Id {%d} does not exist in DB.", guidelineId));
            throw new GuidelineNotFoundException(String.format("Guideline ID {%d} is not found.", guidelineId));
        });

        guidelineRepository.delete(guidelineOptional.get());
        logger.info(String.format("Sucessfully removed Guideline {%d}", guidelineId));
    }
}
