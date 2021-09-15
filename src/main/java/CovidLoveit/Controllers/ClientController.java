package CovidLoveit.Controllers;

import CovidLoveit.Domain.InputModel.ClientInputModel;
import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Exception.ClientNotFoundException;
import CovidLoveit.Service.Services.ClientServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.ConstraintViolation;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class ClientController {

    private Logger logger = LoggerFactory.getLogger(ClientController.class);
    private ClientServiceImpl clientService;

    @Autowired
    public ClientController(ClientServiceImpl clientService){
        this.clientService = clientService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/client")
    public Client addClient(@RequestBody ClientInputModel inputModel){
        Set<ConstraintViolation<ClientInputModel>> violations = inputModel.validate();

        if(!violations.isEmpty()){
            violations.stream().forEach(t -> logger.warn(t.getMessage()));
            throw new ClientNotFoundException("Client input does not conform with the required format.");
        }

        return clientService.addClient(inputModel.getEmail(), inputModel.isAdmin());
    }

    @PutMapping("/client/{clientId}")
    public Client updateClient(@PathVariable String clientId, @RequestBody ClientInputModel inputModel){
        Set<ConstraintViolation<ClientInputModel>> violations = inputModel.validate();

        if(!violations.isEmpty()){
            violations.stream().forEach(t -> logger.warn(t.getMessage()));
            throw new ClientNotFoundException("Client input does not conform with the required format.");
        }

        Client client = new Client(inputModel.getEmail(), inputModel.isAdmin());
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

        client.orElseThrow(() -> new ClientNotFoundException(String.format("Client {%s} not found", clientId)));

        return client.get();
    }
}
