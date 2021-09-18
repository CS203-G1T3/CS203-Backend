package CovidLoveit.Controllers;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import CovidLoveit.Domain.InputModel.RegisteredBusinessInputModel;
import CovidLoveit.Domain.Models.RegisteredBusiness;
import CovidLoveit.Exception.RegisteredBusinessException;
import CovidLoveit.Service.Services.Interfaces.RegisteredBusinessService;


@RestController
@RequestMapping("api/v1")
public class RegisteredBusinessController {
    
    private Logger logger = LoggerFactory.getLogger(RegisteredBusinessController.class);
    private RegisteredBusinessService registeredBusinessService;

    @Autowired
    public RegisteredBusinessController(RegisteredBusinessService registeredBusinessService){
        this.registeredBusinessService = registeredBusinessService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registered-business")
    public RegisteredBusiness addRegisteredBusiness(@RequestBody RegisteredBusinessInputModel inputModel) {
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

        return registeredBusinessService.addBusiness(inputModel.getBusinessName(), inputModel.getBusinessDesc(), inputModel.getIndustryId(), inputModel.getClientId());
    }

    @PutMapping("/registered-business/{businessId}")
    public RegisteredBusiness updateRegisteredBusiness(@PathVariable String businessId, @RequestBody RegisteredBusinessInputModel inputModel){
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

        //TODO PICK UP FROM HERE
        RegisteredBusiness registeredBusiness = new RegisteredBusiness(inputModel.getEmail(), inputModel.isAdmin());
        Client clientRecord = clientService.updateClient(UUID.fromString(clientId), client);

        return clientRecord;
    }

    @DeleteMapping("/client/{clientId}")
    public void deleteClient(@PathVariable String clientId){
        clientService.deleteClient(UUID.fromString(clientId));
    }

    @GetMapping("/client/{clientId}")
    public Client getClient(@PathVariable String clientId) {
        Optional<Client> client = clientService.getClient(UUID.fromString(clientId));

        client.orElseThrow(() -> new ClientException(String.format("Client {%s} not found", clientId)));

        return client.get();
    }

    

}
