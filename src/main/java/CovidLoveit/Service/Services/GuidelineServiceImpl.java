package CovidLoveit.Service.Services;

import CovidLoveit.Domain.InputModel.GuidelineInputModel;
import CovidLoveit.Domain.Models.Guideline;
import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Domain.Models.RegisteredBusiness;
import CovidLoveit.Domain.Models.Role;
import CovidLoveit.Exception.ClientException;
import CovidLoveit.Exception.GuidelineException;
import CovidLoveit.Exception.IndustryException;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import CovidLoveit.Repositories.Interfaces.GuidelineRepository;
import CovidLoveit.Repositories.Interfaces.IndustryRepository;
import CovidLoveit.Service.Services.Interfaces.GuidelineService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class GuidelineServiceImpl implements GuidelineService {

    private Logger logger = LoggerFactory.getLogger(GuidelineServiceImpl.class);
    private GuidelineRepository guidelineRepository;
    private IndustryRepository industryRepository;
    private ClientRepository clientRepository;

    @Autowired
    public GuidelineServiceImpl(GuidelineRepository guidelineRepository, ClientRepository clientRepository,
                                IndustryRepository industryRepository)
    {
        this.guidelineRepository = guidelineRepository;
        this.clientRepository = clientRepository;
        this.industryRepository = industryRepository;
    }

    @Autowired
    EmailServiceImpl emailService;

    @Override
    public Guideline addGuideline(String clientId, GuidelineInputModel inputModel)
    {
        var sessionUser = clientRepository.findById(UUID.fromString(clientId));
        sessionUser.orElseThrow(() -> {
            logger.warn(String.format("Client with ID {%s} not found", clientId));
            throw new ClientException(String.format("Client with ID {%s} not found", clientId));
        });

        // check industry exists
        UUID industryId = inputModel.getIndustryId();
        var industry = industryRepository.findById(industryId);
        industry.orElseThrow(() -> {
            // throw new IndustryException(String.format("Industry with ID {%s} not found", industryId.toString()));
            throw new IndustryException(String.format("Industry with ID {%s} not found", industryId));
        });

        boolean isAdmin = false;
        var roles = sessionUser.get().getRoles();
        for(Role role: roles) {
            if(role.getRoleName().equals("ADMIN")){
                isAdmin = true;
                break;
            }
        }

        if(isAdmin) {
            var guideline = new Guideline(inputModel.isCanOpOnSite(),
                    inputModel.getCanOpOnSiteDetails(), inputModel.getGroupSize(), inputModel.getGroupSizeDetails(),
                    inputModel.getCovidTestingVaccinated(), inputModel.getCovidTestingUnvaccinated(), inputModel.getCovidTestingDetails(),
                    inputModel.getContactTracing(), inputModel.getContactTracingDetails(), inputModel.getOpCapacity(),
                    inputModel.getOpCapacityDetails(), inputModel.getOpGuidelines(), inputModel.getReferenceLink(), industry.get());

            var savedGuideline = guidelineRepository.save(guideline);

            List<RegisteredBusiness> registeredBusinessList = industry.get().getRegisteredBusinesses();
            for (RegisteredBusiness business : registeredBusinessList) {
                emailService.newGuidelineCreationEmail(business.getClient().getEmail(), inputModel);
            }


            logger.info(String.format("Successfully added guideline {%s}", savedGuideline.getGuidelineId().toString()));
            return savedGuideline;

        } else {
            throw new GuidelineException("Unauthorized access.");
        }
    }

    @Override
    public Guideline updateGuideline(String clientId, GuidelineInputModel inputModel) {
        Optional<Guideline> guidelineOptional = guidelineRepository.findById(inputModel.getGuidelineId());
        guidelineOptional.orElseThrow(() -> {
            logger.warn(String.format("Guideline with Id {%s} does not exist in DB.", inputModel.getGuidelineId().toString()));
            throw new GuidelineException(String.format("Guideline ID {%s} is not found.", inputModel.getGuidelineId().toString()));
        });

        // check industry exists
        UUID industryId = inputModel.getIndustryId();
        var industry = industryRepository.findById(industryId);
        industry.orElseThrow(() -> {
            logger.warn(String.format("Industry with ID {%s} not found", industryId.toString()));
            throw new IndustryException(String.format("Industry with ID {%s} not found", industryId.toString()));
        });

        var sessionUser = clientRepository.findById(UUID.fromString(clientId));
        sessionUser.orElseThrow(() -> {
            logger.warn(String.format("Client with ID {%s} not found", clientId));
            throw new ClientException(String.format("Client with ID {%s} not found", clientId));
        });

        boolean isAdmin = false;
        var roles = sessionUser.get().getRoles();
        for(Role role: roles) {
            if(role.getRoleName().equals("ADMIN")){
                isAdmin = true;
                break;
            }
        }

        if(isAdmin) {
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

            List<RegisteredBusiness> registeredBusinessList = industry.get().getRegisteredBusinesses();
            for (RegisteredBusiness business : registeredBusinessList) {
                emailService.updateGuidelineNotificationEmail(business.getClient().getEmail(), inputModel);
            }

            logger.info(String.format("Successfully updated Guideline {%s}", inputModel.getGuidelineId().toString()));
            return guidelineRecord;

        } else {
            throw new GuidelineException("Unauthorized access.");
        }
    }

    @Override
    public void deleteGuideline(String clientId, String guidelineId) {
        Optional<Guideline> guidelineOptional = guidelineRepository.findById(UUID.fromString(guidelineId));
        guidelineOptional.orElseThrow(() -> {
            logger.warn(String.format("Guideline with Id {%s} does not exist in DB.", guidelineId));
            throw new GuidelineException(String.format("Guideline ID {%s} is not found.", guidelineId));
        });

        var sessionUser = clientRepository.findById(UUID.fromString(clientId));
        sessionUser.orElseThrow(() -> {
            logger.warn(String.format("Client with ID {%s} not found", clientId));
            throw new ClientException(String.format("Client with ID {%s} not found", clientId));
        });

        boolean isAdmin = false;
        var roles = sessionUser.get().getRoles();
        for(Role role: roles) {
            if(role.getRoleName().equals("ADMIN")){
                isAdmin = true;
                break;
            }
        }

        if(isAdmin) {
            guidelineRepository.delete(guidelineOptional.get());
            logger.info(String.format("Successfully removed Guideline {%s}", guidelineId));
            return;

        } else {
            throw new GuidelineException("Unauthorized access.");
        }
    }

    @Override
    public Guideline getGuideline(String guidelineId){
        Optional<Guideline> guidelineOptional = guidelineRepository.findById(UUID.fromString(guidelineId));
        guidelineOptional.orElseThrow(() -> {
            logger.warn(String.format("Guideline with Id {%s} does not exist in DB.", guidelineId));
            throw new GuidelineException(String.format("Guideline ID {%s} is not found.", guidelineId));
        });

        return guidelineOptional.get();
    }

    @Override
    public Guideline getLatestGuidelineByIndustry(String industryId){
        var industry = industryRepository.findById(UUID.fromString(industryId));
        industry.orElseThrow(() -> {
            logger.warn(String.format("Industry with ID {%s} not found", industryId.toString()));
            throw new IndustryException(String.format("Industry with ID {%s} not found", industryId.toString()));
        });

        return guidelineRepository.findLatestGuidelineByIndustry(industry.get());
    }

    @Override
    public List<Guideline> getAllGuidelines(){
        return guidelineRepository.findAll();
    }
}
