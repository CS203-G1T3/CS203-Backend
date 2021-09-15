package CovidLoveit.Service.Services;

import CovidLoveit.Domain.Models.RegisteredBusiness;
import CovidLoveit.Exception.RegisteredBusinessNotFoundException;
import CovidLoveit.Repositories.Interfaces.RegisteredBusinessRepository;
import CovidLoveit.Service.Services.Interfaces.RegisteredBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RegisteredBusinessServiceImpl implements RegisteredBusinessService {

    private Logger logger = LoggerFactory.getLogger(RegisteredBusinessServiceImpl.class);
    private RegisteredBusinessRepository registeredBusinessRepository;

    @Autowired
    public RegisteredBusinessServiceImpl(RegisteredBusinessRepository registeredBusinessRepository) {this.registeredBusinessRepository = registeredBusinessRepository; }

    @Override
    public RegisteredBusiness addBusiness(String name, String description){
        var business = new RegisteredBusiness(name, description);
        var savedBusiness = registeredBusinessRepository.save(business);

        logger.info(String.format("Successfully added business {%s}", savedBusiness.getBusinessName(), savedBusiness.getBusinessId()));
        return savedBusiness;
    }

    @Override
    public RegisteredBusiness updateBusiness(UUID businessId, RegisteredBusiness registeredBusiness){
        Optional<RegisteredBusiness> businessOptional = registeredBusinessRepository.findById(registeredBusiness.getBusinessId());

        //statement will only be true of Optional object businessRecord was created with a non-null value
        businessOptional.orElseThrow(() -> {
            logger.warn(String.format("Registered Business {%s} does not exist in DB.", businessId));
            return new RegisteredBusinessNotFoundException(String.format("Business with ID {%s} not found.", businessId));
        });

        RegisteredBusiness businessRecord = registeredBusinessRepository.save(registeredBusiness);
        logger.info(String.format("Successfully updated business with ID {%s}", businessId));
        return businessRecord;

    }

    @Override
    public void deleteBusiness(UUID businessId){
        Optional<RegisteredBusiness> businessOptional = registeredBusinessRepository.findById(businessId);

        businessOptional.orElseThrow(() -> {
            logger.warn(String.format("Registered Business {%s} does not exist in DB.", businessId));
            return new RegisteredBusinessNotFoundException(String.format("Business with ID {%s} not found.", businessId));
        });
        
        registeredBusinessRepository.delete(businessOptional.get());
        logger.info(String.format("Successfully deleted business with ID {%s}", businessId));
    }



}
