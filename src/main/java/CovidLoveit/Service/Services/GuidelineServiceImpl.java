package CovidLoveit.Service.Services;

import CovidLoveit.Domain.InputModel.GuidelineInputModel;
import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Guideline;
import CovidLoveit.Exception.ClientException;
import CovidLoveit.Exception.GuidelineException;
import CovidLoveit.Repositories.Interfaces.GuidelineRepository;
import CovidLoveit.Service.Services.Interfaces.ClientService;
import CovidLoveit.Service.Services.Interfaces.GuidelineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class GuidelineServiceImpl implements GuidelineService {

    private Logger logger = LoggerFactory.getLogger(GuidelineServiceImpl.class);
    private GuidelineRepository guidelineRepository;
    private ClientService clientService;

    @Autowired
    public GuidelineServiceImpl(GuidelineRepository guidelineRepository, ClientService clientService) {
        this.guidelineRepository = guidelineRepository;
        this.clientService = clientService;
    }

    @Override
    public Guideline addGuideline(String clientId, GuidelineInputModel inputModel)
    {
        var sessionUser = clientService.getClient(UUID.fromString(clientId));
        sessionUser.orElseThrow(() -> {
            throw new ClientException(String.format("Client with ID {%s} not found", clientId));
        });

        if (!sessionUser.get().isAdmin()) {
            throw new ClientException("Unauthorized access.");
        }
        var guideline = new Guideline(inputModel.isCanOpOnSite(),
                inputModel.getCanOpOnSiteDetails(), inputModel.getGroupSize(), inputModel.getGroupSizeDetails(),
                inputModel.getCovidTestingVaccinated(), inputModel.getCovidTestingUnvaccinated(), inputModel.getCovidTestingDetails(),
                inputModel.getContactTracing(), inputModel.getContactTracingDetails(), inputModel.getOperatingCapacity(), inputModel.getOpCapacityDetails(),
                inputModel.getOpGuidelines(), inputModel.getReferenceLink());

        var savedGuideline = guidelineRepository.save(guideline);

        logger.info(String.format("Successfully added guideline {%d}", savedGuideline.getGuidelineId()));
        return savedGuideline;
    }

    @Override
    public Guideline updateGuideline(int guidelineId, Guideline guideline) {
        Optional<Guideline> guidelineOptional = guidelineRepository.findById(guidelineId);

        guidelineOptional.orElseThrow(() -> {
            logger.warn(String.format("Guideline with Id {%d} does not exist in DB.", guidelineId));
            throw new GuidelineException(String.format("Guideline ID {%d} is not found.", guidelineId));
        });

        var guidelineRecord = guidelineOptional.get();
        guidelineRecord.setCanOpOnSite(guideline.getCanOpOnSite());
        guidelineRecord.setCanOpOnSiteDetails(guideline.getCanOpOnSiteDetails());
        guidelineRecord.setGroupSize(guideline.getGroupSize());
        guidelineRecord.setGroupSizeDetails(guideline.getGroupSizeDetails());
        guidelineRecord.setCovidTestingVaccinated(guideline.getCovidTestingVaccinated());
        guidelineRecord.setCovidTestingUnvaccinated(guideline.getCovidTestingUnvaccinated());
        guidelineRecord.setCovidTestingDetails(guideline.getCovidTestingDetails());
        guidelineRecord.setContactTracing(guideline.getContactTracing());
        guidelineRecord.setContactTracingDetails(guideline.getContactTracingDetails());
        guidelineRecord.setOperatingCapacity(guideline.getOperatingCapacity());
        guidelineRecord.setOpCapacityDetails(guideline.getOpCapacityDetails());
        guidelineRecord.setOpGuidelines(guideline.getOpGuidelines());
        guidelineRecord.setReferenceLink(guideline.getReferenceLink());

        guidelineRepository.save(guidelineRecord);
        logger.info(String.format("Successfully updated Guideline {%d}", guidelineId));
        return guidelineRecord;
    }

    @Override
    public void deleteGuideline(int guidelineId) {
        Optional<Guideline> guidelineOptional = guidelineRepository.findById(guidelineId);

        guidelineOptional.orElseThrow(() -> {
            logger.warn(String.format("Guideline with Id {%d} does not exist in DB.", guidelineId));
            throw new GuidelineException(String.format("Guideline ID {%d} is not found.", guidelineId));
        });

        guidelineRepository.delete(guidelineOptional.get());
        logger.info(String.format("Successfully removed Guideline {%d}", guidelineId));
    }

    @Override
    public Optional<Guideline> getGuideline(int guidelineId){
        return guidelineRepository.findById(guidelineId);
    }
}
