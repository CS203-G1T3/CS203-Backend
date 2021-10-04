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
import java.util.List;
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
                inputModel.getContactTracing(), inputModel.getContactTracingDetails(), inputModel.getOpCapacity(),
                inputModel.getOpCapacityDetails(), inputModel.getOpGuidelines(), inputModel.getReferenceLink(), inputModel.getIndustry());

        var savedGuideline = guidelineRepository.save(guideline);

        logger.info(String.format("Successfully added guideline {%d}", savedGuideline.getGuidelineId()));
        return savedGuideline;
    }

    @Override
    public Guideline updateGuideline(String clientId, GuidelineInputModel inputModel) {
        Optional<Guideline> guidelineOptional = guidelineRepository.findById(inputModel.getGuidelineId());

        var sessionUser = clientService.getClient(UUID.fromString(clientId));
        sessionUser.orElseThrow(() -> {
            throw new ClientException(String.format("Client with ID {%s} not found", clientId));
        });

        if (!sessionUser.get().isAdmin()) {
            throw new ClientException("Unauthorized access.");
        }
        guidelineOptional.orElseThrow(() -> {
            logger.warn(String.format("Guideline with Id {%d} does not exist in DB.", inputModel.getGuidelineId()));
            throw new GuidelineException(String.format("Guideline ID {%d} is not found.", inputModel.getGuidelineId()));
        });

        var guidelineRecord = guidelineOptional.get();
        guidelineRecord.setCanOpOnSite(inputModel.isCanOpOnSite());
        guidelineRecord.setCanOpOnSiteDetails(inputModel.getCanOpOnSiteDetails());
        guidelineRecord.setGroupSize(inputModel.getGroupSize());
        guidelineRecord.setGroupSizeDetails(inputModel.getGroupSizeDetails());
        guidelineRecord.setCovidTestingVaccinated(inputModel.getCovidTestingVaccinated());
        guidelineRecord.setCovidTestingUnvaccinated(inputModel.getCovidTestingUnvaccinated());
        guidelineRecord.setCovidTestingDetails(inputModel.getCovidTestingDetails());
        guidelineRecord.setContactTracing(inputModel.getContactTracing());
        guidelineRecord.setContactTracingDetails(inputModel.getContactTracingDetails());
        guidelineRecord.setOpCapacity(inputModel.getOpCapacity());
        guidelineRecord.setOpCapacityDetails(inputModel.getOpCapacityDetails());
        guidelineRecord.setOpGuidelines(inputModel.getOpGuidelines());
        guidelineRecord.setReferenceLink(inputModel.getReferenceLink());
        guidelineRecord.setIndustry(inputModel.getIndustry());

        guidelineRepository.save(guidelineRecord);
        logger.info(String.format("Successfully updated Guideline {%d}", inputModel.getGuidelineId()));
        return guidelineRecord;
    }

    @Override
    public void deleteGuideline(String clientId, int guidelineId) {
        Optional<Guideline> guidelineOptional = guidelineRepository.findById(guidelineId);

        var sessionUser = clientService.getClient(UUID.fromString(clientId));
        sessionUser.orElseThrow(() -> {
            throw new ClientException(String.format("Client with ID {%s} not found", clientId));
        });

        if (!sessionUser.get().isAdmin()) {
            throw new ClientException("Unauthorized access.");
        }
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

    @Override
    public List<Guideline> getAllGuidelines(){
        return guidelineRepository.findAll();
    }
}
