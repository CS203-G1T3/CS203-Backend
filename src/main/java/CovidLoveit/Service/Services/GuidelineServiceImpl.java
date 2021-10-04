package CovidLoveit.Service.Services;

import CovidLoveit.Domain.InputModel.GuidelineInputModel;
import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Guideline;
import CovidLoveit.Exception.ClientException;
import CovidLoveit.Exception.GuidelineException;
import CovidLoveit.Exception.IndustryException;
import CovidLoveit.Repositories.Interfaces.GuidelineRepository;
import CovidLoveit.Service.Services.Interfaces.ClientService;
import CovidLoveit.Service.Services.Interfaces.GuidelineService;
import CovidLoveit.Service.Services.Interfaces.IndustryService;

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
    private IndustryService industryService;

    @Autowired
    public GuidelineServiceImpl(GuidelineRepository guidelineRepository, ClientService clientService, IndustryService industryService) {
        this.guidelineRepository = guidelineRepository;
        this.clientService = clientService;
        this.industryService = industryService;
    }

    @Override
    public Guideline addGuideline(String clientId, GuidelineInputModel inputModel)
    {
        var sessionUser = clientService.getClient(UUID.fromString(clientId));
        if (sessionUser == null)
            throw new ClientException(String.format("Client with ID {%s} not found", clientId));

        if (!sessionUser.getRoles().contains("ROLE_ADMIN"))
            throw new ClientException("Unauthorized access.");

        // check industry exists
        UUID industryId = inputModel.getIndustryId();
        var industry = industryService.getIndustry(industryId);
        industry.orElseThrow(() -> {
            // throw new IndustryException(String.format("Industry with ID {%s} not found", industryId.toString()));
            throw new IndustryException("Industry with ID {%s} not found");
        });

        var guideline = new Guideline(inputModel.isCanOpOnSite(),
                inputModel.getCanOpOnSiteDetails(), inputModel.getGroupSize(), inputModel.getGroupSizeDetails(),
                inputModel.getCovidTestingVaccinated(), inputModel.getCovidTestingUnvaccinated(), inputModel.getCovidTestingDetails(),
                inputModel.getContactTracing(), inputModel.getContactTracingDetails(), inputModel.getOpCapacity(),
                inputModel.getOpCapacityDetails(), inputModel.getOpGuidelines(), inputModel.getReferenceLink(), industry.get());

        var savedGuideline = guidelineRepository.save(guideline);

        logger.info(String.format("Successfully added guideline {%s}", savedGuideline.getGuidelineId().toString()));
        return savedGuideline;
    }

    @Override
    public Guideline updateGuideline(String clientId, GuidelineInputModel inputModel) {
        Optional<Guideline> guidelineOptional = guidelineRepository.findById(inputModel.getGuidelineId());

        var sessionUser = clientService.getClient(UUID.fromString(clientId));
        if (sessionUser == null)
            throw new ClientException(String.format("Client with ID {%s} not found", clientId));

        if (!sessionUser.getRoles().contains("ROLE_ADMIN"))
            throw new ClientException("Unauthorized access.");

        guidelineOptional.orElseThrow(() -> {
            logger.warn(String.format("Guideline with Id {%s} does not exist in DB.", inputModel.getGuidelineId().toString()));
            throw new GuidelineException(String.format("Guideline ID {%s} is not found.", inputModel.getGuidelineId().toString()));
        });

        // check industry exists
        UUID industryId = inputModel.getIndustryId();
        var industry = industryService.getIndustry(industryId);
        industry.orElseThrow(() -> {
            throw new IndustryException(String.format("Industry with ID {%s} not found", industryId.toString()));
        });
        
        Guideline guidelineRecord = guidelineOptional.get();
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
        guidelineRecord.setIndustry(industry.get());

        guidelineRepository.save(guidelineRecord);
        logger.info(String.format("Successfully updated Guideline {%s}", inputModel.getGuidelineId().toString()));
        return guidelineRecord;
    }

    @Override
    public void deleteGuideline(String clientId, String guidelineId) {
        Optional<Guideline> guidelineOptional = guidelineRepository.findById(UUID.fromString(guidelineId));

        var sessionUser = clientService.getClient(UUID.fromString(clientId));
        if (sessionUser == null)
            throw new ClientException(String.format("Client with ID {%s} not found", clientId));

        if (!sessionUser.getRoles().contains("ROLE_ADMIN"))
            throw new ClientException("Unauthorized access.");

        guidelineOptional.orElseThrow(() -> {
            logger.warn(String.format("Guideline with Id {%s} does not exist in DB.", guidelineId));
            throw new GuidelineException(String.format("Guideline ID {%s} is not found.", guidelineId));
        });

        guidelineRepository.delete(guidelineOptional.get());
        logger.info(String.format("Successfully removed Guideline {%s}", guidelineId));
    }

    @Override
    public Optional<Guideline> getGuideline(String guidelineId){
        return guidelineRepository.findById(UUID.fromString(guidelineId));
    }

    @Override
    public List<Guideline> getAllGuidelines(){
        return guidelineRepository.findAll();
    }
}
