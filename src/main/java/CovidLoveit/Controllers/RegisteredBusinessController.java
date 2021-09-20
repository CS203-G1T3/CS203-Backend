package CovidLoveit.Controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import CovidLoveit.Controllers.DataTransferObjects.RegisteredBusinessDTO;
import CovidLoveit.Domain.InputModel.RegisteredBusinessInputModel;
import CovidLoveit.Domain.Models.RegisteredBusiness;
import CovidLoveit.Exception.RegisteredBusinessException;
import CovidLoveit.Service.Services.Interfaces.RegisteredBusinessService;


@RestController
@RequestMapping("api/v1")
public class RegisteredBusinessController {
    
    private Logger logger = LoggerFactory.getLogger(RegisteredBusinessController.class);
    private RegisteredBusinessService registeredBusinessService;
    private ModelMapper modelMapper;

    @Autowired
    public RegisteredBusinessController(RegisteredBusinessService registeredBusinessService, ModelMapper modelMapper){
        this.registeredBusinessService = registeredBusinessService;
        this.modelMapper = modelMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registered-business")
    public RegisteredBusinessDTO addRegisteredBusiness(@RequestBody RegisteredBusinessInputModel inputModel) {
        Set<ConstraintViolation<RegisteredBusinessInputModel>> violations = inputModel.validate();
        StringBuilder error = new StringBuilder();

        if(!violations.isEmpty()){
            violations.stream().forEach(t -> {
                error.append(t.getMessage());
                error.append(System.getProperty("line.separator"));
                logger.warn(t.getMessage());
            });
            throw new RegisteredBusinessException(error.toString());
        }

        return convertToDTO(registeredBusinessService.addBusiness(inputModel.getBusinessName(), inputModel.getBusinessDesc(), inputModel.getIndustryId(), inputModel.getClientId()));
    }

    @PutMapping("/registered-business/{businessId}")
    public RegisteredBusinessDTO updateRegisteredBusiness(@PathVariable String businessId, @RequestBody RegisteredBusinessInputModel inputModel){
        Set<ConstraintViolation<RegisteredBusinessInputModel>> violations = inputModel.validate();
        StringBuilder error = new StringBuilder();

        if(!violations.isEmpty()){
            violations.stream().forEach(t -> {
                error.append(t.getMessage());
                error.append(System.getProperty("line.separator"));
                logger.warn(t.getMessage());
            });
            throw new RegisteredBusinessException(error.toString());
        }

        return convertToDTO(registeredBusinessService.updateBusiness(UUID.fromString(businessId),
                inputModel.getBusinessName(), inputModel.getBusinessDesc(), inputModel.getBusinessId(),
                inputModel.getClientId()));
    }

    @DeleteMapping("/registered-business/{businessId}")
    public void deleteRegisteredBusiness(@PathVariable String businessId){
        registeredBusinessService.deleteBusiness(UUID.fromString(businessId));
    }

    @GetMapping("/registered-business/{businessId}")
    public RegisteredBusinessDTO getRegisteredBusiness(@PathVariable String businessId) {
        Optional<RegisteredBusiness> business = registeredBusinessService.getBusiness(UUID.fromString(businessId));

        business.orElseThrow(() -> new RegisteredBusinessException(String.format("Registered Business {%s} not found", businessId)));

        return convertToDTO(business.get());
    }

    @GetMapping("/registered-business")
    public List<RegisteredBusinessDTO> getAllRegisteredBusinesses() {
        List<RegisteredBusiness> registeredBusinesses = registeredBusinessService.getAllRegisteredBusinesses();
        
        return registeredBusinesses.stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
    }

    // convert to data transfer object for http requests
    private RegisteredBusinessDTO convertToDTO(RegisteredBusiness registeredBusiness) {
        return modelMapper.map(registeredBusiness, RegisteredBusinessDTO.class);
    }

}
