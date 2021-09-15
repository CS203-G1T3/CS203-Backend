package CovidLoveit.Service.Services;

import CovidLoveit.Domain.Models.RegisteredBusiness;
import CovidLoveit.Exception.ResourceNotFoundException;
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

        logger.info(String.format("Successfully added business {%s,%s}", savedBusiness.getBusinessName(), savedBusiness.getBusinessId()));

        return savedBusiness;

    }

    @Override
    public RegisteredBusiness updateBusiness(RegisteredBusiness registeredBusiness){
        Optional<RegisteredBusiness> businessRecord = registeredBusinessRepository.findById(registeredBusiness.getBusinessId());

        //statement will only be true of Optional object businessRecord was created with a non-null value
        if (businessRecord.isPresent()){
            RegisteredBusiness businessUpdate = businessRecord.get();
            businessUpdate.setBusinessName(registeredBusiness.getBusinessName());
            businessUpdate.setBusinessDesc(registeredBusiness.getBusinessDesc());

            registeredBusinessRepository.save(businessUpdate);
            logger.info(String.format("Successfully updated business with ID {%s}", registeredBusiness.getBusinessId()));
            return businessUpdate;

        } else {
            logger.error(String.format("Business with ID {%s} does not exist in database.", registeredBusiness.getBusinessId()));
            throw new ResourceNotFoundException(String.format("Business with ID {%s} not found.", registeredBusiness.getBusinessName()));
        }

    }

    @Override
    public void deleteBusiness(UUID businessId){
        Optional<RegisteredBusiness> businessOptional = registeredBusinessRepository.findById(businessId);

        if(businessOptional.isPresent()){
            registeredBusinessRepository.delete(businessOptional.get());
        } else {
            logger.error(String.format("Business with ID {%s} does not exist in database.", businessId));
            throw new ResourceNotFoundException(String.format("Business with ID {%s} does not exist in database.", businessId));
        }
    }



}
