package CovidLoveit.Service.Services;

import CovidLoveit.Domain.InputModel.IndustryInputModel;
import CovidLoveit.Domain.Models.Role;
import CovidLoveit.Exception.ClientException;
import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Exception.IndustryException;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import CovidLoveit.Repositories.Interfaces.IndustryRepository;
import CovidLoveit.Service.Services.Interfaces.ClientService;
import CovidLoveit.Service.Services.Interfaces.IndustryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
@Transactional
public class IndustryServiceImpl implements IndustryService {

    // Use this logger object to log information of user's actions
    private Logger logger = LoggerFactory.getLogger(IndustryServiceImpl.class);
    private IndustryRepository industryRepository;
    private ClientRepository clientRepository;

    // Injecting the required dependencies here
    @Autowired
    public IndustryServiceImpl(IndustryRepository industryRepository, ClientRepository clientRepository){
        this.industryRepository = industryRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Industry addIndustry(String clientId, IndustryInputModel inputModel) {
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
            var industry = new Industry(inputModel.getIndustryName(), inputModel.getIndustrySubtype(), inputModel.getIndustryDesc());
            var savedIndustry = industryRepository.save(industry);

            logger.info(String.format("Successfully added industry {%s}", savedIndustry.getIndustryId()));
            return savedIndustry;

        } else {
            throw new ClientException("Unauthorized access.");
        }
    }

    @Override
    public Industry updateIndustry(String clientId, IndustryInputModel inputModel) {
        Optional<Industry> industryOptional = industryRepository.findById(inputModel.getIndustryId());
        industryOptional.orElseThrow(() -> {
            logger.warn(String.format("Industry with ID {%s} does not exist in DB.", inputModel.getIndustryId()));
            throw new IndustryException(String.format("Industry with ID {%s} not found.", inputModel.getIndustryId()));
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
            var industryRecord = industryOptional.get();
            industryRecord.setIndustryName(inputModel.getIndustryName());
            industryRecord.setIndustrySubtype(inputModel.getIndustrySubtype());
            industryRecord.setIndustryDesc(inputModel.getIndustryDesc());

            industryRepository.save(industryRecord);
            logger.info(String.format("Successfully updated industry with ID {%s}", inputModel.getIndustryId()));
            return industryRecord;

        } else {
            throw new IndustryException("Unauthorized access.");
        }
    }

    @Override
    public void deleteIndustry(String clientId, UUID industryId) {
        Optional<Industry> industryOptional = industryRepository.findById(industryId);
        industryOptional.orElseThrow(() -> {
            logger.warn(String.format("Industry with ID {%s} does not exist in DB.", industryId));
            throw new IndustryException(String.format("Industry with ID {%s} not found.",industryId));
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
            industryRepository.delete(industryOptional.get());
            logger.info(String.format("Successfully removed Industry with ID {%s}", industryId));
            return;

        } else {
            throw new IndustryException("Unauthorized access.");
        }
    }

    @Override
    public Optional<Industry> getIndustry(UUID industryId) {
        return industryRepository.findById(industryId);
    }

    @Override
    public List<Industry> getAllIndustrySubtypes() {
        return industryRepository.findAll();
    }

    @Override
    public List<String> getAllIndustries() {
        return industryRepository.findDistinctIndustryName();
    }

    @Override
    public List<Industry> getIndustrySubtypesByIndustry(String industryName) {
        return industryRepository.findIndustryByIndustryName(industryName);
    }
}
