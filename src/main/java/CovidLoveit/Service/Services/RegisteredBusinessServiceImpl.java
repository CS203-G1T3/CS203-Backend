package CovidLoveit.Service.Services;

import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Domain.Models.RegisteredBusiness;
import CovidLoveit.Exception.ClientException;
import CovidLoveit.Exception.IndustryException;
import CovidLoveit.Exception.RegisteredBusinessException;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import CovidLoveit.Repositories.Interfaces.IndustryRepository;
import CovidLoveit.Repositories.Interfaces.RegisteredBusinessRepository;
import CovidLoveit.Service.Services.Interfaces.RegisteredBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RegisteredBusinessServiceImpl implements RegisteredBusinessService {

    private Logger logger = LoggerFactory.getLogger(RegisteredBusinessServiceImpl.class);
    private RegisteredBusinessRepository registeredBusinessRepository;
    private ClientRepository clientRepository;
    private IndustryRepository industryRepository;

    @Autowired
    public RegisteredBusinessServiceImpl(RegisteredBusinessRepository registeredBusinessRepository, ClientRepository clientRepository, IndustryRepository industryRepository) {
        this.registeredBusinessRepository = registeredBusinessRepository; 
        this.clientRepository = clientRepository;
        this.industryRepository = industryRepository;
    }

    @Override
    public RegisteredBusiness addBusiness(String name, String description, UUID industryId, UUID clientId){
        var business = new RegisteredBusiness(name, description);
        
        //check that client and industry exist in db
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            business.setClient(client);
        } else {
            logger.warn(String.format("Client {%s} does not exist in DB.", clientId));
            throw new ClientException(String.format("Client with ID {%s} not found.", clientId));
        }

        Optional<Industry> industryOptional = industryRepository.findById(industryId);
        if (industryOptional.isPresent()) {
            Industry industry = industryOptional.get();
            business.setIndustry(industry);
        } else {
            logger.warn(String.format("Industry {%s} does not exist in DB.", industryId));
            throw new IndustryException(String.format("Industry with ID {%s} not found.", industryId));
        }
        
        var savedBusiness = registeredBusinessRepository.save(business);

        logger.info(String.format("Successfully added business {%s}", savedBusiness.getBusinessName(), savedBusiness.getBusinessId()));
        return savedBusiness;
    }

    @Override
    public RegisteredBusiness updateBusiness(UUID businessId, String name, String description, UUID industryId, UUID clientId){
        Optional<RegisteredBusiness> businessOptional = registeredBusinessRepository.findById(businessId);

        //statement will only be true of Optional object businessRecord was created with a non-null value
        businessOptional.orElseThrow(() -> {
            logger.warn(String.format("Registered Business {%s} does not exist in DB.", businessId));
            throw new RegisteredBusinessException(String.format("Business with ID {%s} not found.", businessId));
        });

        var businessRecord = businessOptional.get();
        businessRecord.setBusinessName(name);
        businessRecord.setBusinessDesc(description);

        //check that client and industry exist in db
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            businessRecord.setClient(client);
        } else {
            logger.warn(String.format("Client {%s} does not exist in DB.", clientId));
            throw new ClientException(String.format("Client with ID {%s} not found.", clientId));
        }

        Optional<Industry> industryOptional = industryRepository.findById(industryId);
        if (industryOptional.isPresent()) {
            Industry industry = industryOptional.get();
            businessRecord.setIndustry(industry);
        } else {
            logger.warn(String.format("Industry {%s} does not exist in DB.", industryId));
            throw new IndustryException(String.format("Industry with ID {%s} not found.", industryId));
        }

        registeredBusinessRepository.save(businessRecord);
        logger.info(String.format("Successfully updated business with ID {%s}", businessId));
        return businessRecord;

    }

    @Override
    public void deleteBusiness(UUID businessId){
        Optional<RegisteredBusiness> businessOptional = registeredBusinessRepository.findById(businessId);

        businessOptional.orElseThrow(() -> {
            logger.warn(String.format("Registered Business {%s} does not exist in DB.", businessId));
            throw new RegisteredBusinessException(String.format("Business with ID {%s} not found.", businessId));
        });
        
        registeredBusinessRepository.delete(businessOptional.get());
        logger.info(String.format("Successfully deleted business with ID {%s}", businessId));
    }

    @Override
    public Optional<RegisteredBusiness> getBusiness(UUID businessId){
        return registeredBusinessRepository.findById(businessId);
    }

    @Override
    public List<RegisteredBusiness> listRegisteredBusinesses(){
        return registeredBusinessRepository.findAll();
    }




}
