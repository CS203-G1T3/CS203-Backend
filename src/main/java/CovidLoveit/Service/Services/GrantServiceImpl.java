package CovidLoveit.Service.Services;

import CovidLoveit.Domain.InputModel.GrantInputModel;
import CovidLoveit.Domain.InputModel.NotificationInputModel;
import CovidLoveit.Domain.Models.Grant;
import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Domain.Models.Role;
import CovidLoveit.Exception.ClientException;
import CovidLoveit.Exception.GrantException;
import CovidLoveit.Exception.IndustryException;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import CovidLoveit.Repositories.Interfaces.GrantRepository;
import CovidLoveit.Repositories.Interfaces.IndustryRepository;
import CovidLoveit.Repositories.Interfaces.RegisteredBusinessRepository;
import CovidLoveit.Service.Services.Interfaces.GrantService;
import CovidLoveit.Service.Services.Interfaces.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class GrantServiceImpl implements GrantService {

    private Logger logger = LoggerFactory.getLogger(GrantServiceImpl.class);
    private final GrantRepository grantRepository;
    private final ClientRepository clientRepository;
    private final IndustryRepository industryRepository;
    private final RegisteredBusinessRepository businessRepository;
    private final NotificationService notificationService;

    @Autowired
    public GrantServiceImpl(GrantRepository grantRepository, ClientRepository clientRepository,
                            IndustryRepository industryRepository, RegisteredBusinessRepository businessRepository,
                            NotificationService notificationService) {
        this.grantRepository = grantRepository;
        this.clientRepository = clientRepository;
        this.industryRepository = industryRepository;
        this.businessRepository = businessRepository;
        this.notificationService = notificationService;
    }

    @Override
    public Grant addGrant(String adminId, GrantInputModel inputModel) {
        var grantOptional = grantRepository.findGrantByGrantName(inputModel.getGrantName());
        if(grantOptional.isPresent()) {
            logger.warn("Grant already exists in DB.");
            throw new GrantException(String.format("Grant with name {%s} already exists in DB.", inputModel.getGrantName()));
        }

        var sessionUser = clientRepository.findById(UUID.fromString(adminId));
        sessionUser.orElseThrow(() -> {
            logger.warn(String.format("Client with ID {%s} not found", adminId));
            throw new ClientException(String.format("Client with ID {%s} not found", adminId));
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
            var grant = new Grant(inputModel.getGrantName(), inputModel.getProvider(), inputModel.getGrantDesc(),
                    inputModel.getValue(), inputModel.getEligibilityCriteria(), inputModel.getApplicationProcess(),
                    inputModel.getBenefits(), inputModel.getGrantLink());
            var savedGrant = grantRepository.save(grant);

            logger.info(String.format("Successfully added grant {%s}", savedGrant.getGrantId()));
            return savedGrant;

        } else {
            throw new ClientException("Unauthorized access.");
        }
    }

    @Override
    public Grant addIndustryToGrant(String adminId, String industryName, UUID grantId) {
        var sessionUser = clientRepository.findById(UUID.fromString(adminId));
        sessionUser.orElseThrow(() -> {
            logger.warn(String.format("Client with ID {%s} not found", adminId));
            throw new ClientException(String.format("Client with ID {%s} not found", adminId));
        });

        boolean isAdmin = false;
        var roles = sessionUser.get().getRoles();
        for(Role role: roles) {
            if(role.getRoleName().equals("ADMIN")){
                isAdmin = true;
                break;
            }
        }

        var industryRecords = industryRepository.findIndustryByIndustryName(industryName);
        if(industryRecords.isEmpty()){
            logger.warn(String.format("Industry Subtype with name {%s} not found", industryName));
            throw new IndustryException(String.format("Industry Subtype with name {%s} not found", industryName));
        }

        var grantOptional = grantRepository.findById(grantId);
        grantOptional.orElseThrow(() -> {
            logger.warn(String.format("Grant with ID {%s} not found", grantId));
            throw new IndustryException(String.format("Grant with ID {%s} not found", grantId));
        });

        var grant = grantOptional.get();
        grant.getIndustries().addAll(industryRecords);

        for(Industry industry : industryRecords) {
            var business = businessRepository.findRegisteredBusinessByIndustry(industry.getIndustryId());

            var message = "New grants available! Check it out.";
            var notification = new NotificationInputModel(message, business.getClient().getClientId());
            notificationService.addNotification(sessionUser.get().getClientId(), notification);
        }

        var savedGrant = grantRepository.save(grant);
        return savedGrant;
    }

    @Override
    public Grant updateGrant(String adminId, GrantInputModel inputModel) {
        var grantOptional = grantRepository.findById(inputModel.getGrantId());
        grantOptional.orElseThrow(() -> {
            logger.warn(String.format("Grant with ID {%s} not found", inputModel.getGrantId()));
            throw new GrantException(String.format("Grant with ID {%s} not found", inputModel.getGrantId()));
        });

        var sessionUser = clientRepository.findById(UUID.fromString(adminId));
        sessionUser.orElseThrow(() -> {
            logger.warn(String.format("Client with ID {%s} not found", adminId));
            throw new ClientException(String.format("Client with ID {%s} not found", adminId));
        });

        boolean isAdmin = false;
        var roles = sessionUser.get().getRoles();
        for(Role role: roles) {
            if(role.getRoleName().equals("ADMIN")){
                isAdmin = true;
                break;
            }
        }

        var industries = inputModel.getIndustryNames();
        List<Industry> grantIndustries = new ArrayList<>();
        if(industries != null && !industries.isEmpty()) {
            for(String industryName : industries) {
                var industryVerification = industryRepository.findIndustryByIndustryName(industryName);
                if(!industryVerification.isEmpty())
                    grantIndustries.addAll(industryVerification);
            }
        }

        for(Industry industry : grantIndustries) {
            var business = businessRepository.findRegisteredBusinessByIndustry(industry.getIndustryId());

            var message = "There is an update with regards to the grants available. Click here for more.";
            var notification = new NotificationInputModel(message, business.getClient().getClientId());
            notificationService.addNotification(sessionUser.get().getClientId(), notification);
        }

        if(isAdmin) {
            var grantRecord = grantOptional.get();
            grantRecord.setGrantName(inputModel.getGrantName());
            grantRecord.setProvider(inputModel.getProvider());
            grantRecord.setGrantDesc(inputModel.getGrantDesc());
            grantRecord.setValue(inputModel.getValue());
            grantRecord.setEligibilityCriteria(inputModel.getEligibilityCriteria());
            grantRecord.setApplicationProcess(inputModel.getApplicationProcess());
            grantRecord.setBenefits(inputModel.getBenefits());
            grantRecord.setGrantLink(inputModel.getGrantLink());
            grantRecord.setIndustries(grantIndustries);

            var savedGrant = grantRepository.save(grantRecord);

            logger.info(String.format("Successfully updated Grant with ID {%s}", savedGrant.getGrantId()));
            return savedGrant;

        } else {
            throw new ClientException("Unauthorized access.");
        }
    }

    @Override
    public void deleteGrant(String adminId, UUID grantId) {
        var sessionUser = clientRepository.findById(UUID.fromString(adminId));
        sessionUser.orElseThrow(() -> {
            logger.warn(String.format("Client with ID {%s} not found", adminId));
            throw new ClientException(String.format("Client with ID {%s} not found", adminId));
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
            var grant = grantRepository.findById(grantId);
            grant.orElseThrow(() -> {
                logger.warn(String.format("Grant with ID {%s} not found", grantId));
                throw new GrantException(String.format("Grant with ID {%s} not found", grantId));
            });

            grantRepository.delete(grant.get());
            logger.info(String.format("Successfully removed grant with ID {%s}", grantId));

        } else {
            throw new ClientException("Unauthorized access.");
        }
    }

    @Override
    public Grant getGrant(UUID grantId) {
        var grantOptional = grantRepository.findById(grantId);
        grantOptional.orElseThrow(() -> {
            logger.warn(String.format("Grant with ID {%s} not found", grantId));
            throw new GrantException(String.format("Grant with ID {%s} not found", grantId));
        });

        return grantOptional.get();
    }

    @Override
    public List<Grant> getAllGrants() {
        return grantRepository.findAll();
    }

    @Override
    public List<Grant> getAllGrantsByIndustry(String industrySubtypeName) {
        var industry = industryRepository.findIndustryByIndustrySubtype(industrySubtypeName);
        if(industry == null){
            logger.warn(String.format("Industry Subtype with name {%s} not found", industrySubtypeName));
            throw new IndustryException(String.format("Industry Subtype with name {%s} not found", industrySubtypeName));
        }
        return grantRepository.findGrantsByIndustries(industry);
    }
}
