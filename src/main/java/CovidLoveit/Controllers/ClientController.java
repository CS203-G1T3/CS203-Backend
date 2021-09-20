package CovidLoveit.Controllers;

import CovidLoveit.Controllers.DataTransferObjects.ClientDTO;
import CovidLoveit.Domain.InputModel.ClientInputModel;
import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Exception.ClientException;

import CovidLoveit.Service.Services.Interfaces.ClientService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.ConstraintViolation;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class ClientController {

    private Logger logger = LoggerFactory.getLogger(ClientController.class);
    private ClientService clientService;
    private ModelMapper modelMapper;

    @Autowired
    public ClientController(ClientService clientService, ModelMapper modelMapper){
        this.clientService = clientService;
        this.modelMapper = modelMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/client")
    public ClientDTO addClient(@RequestBody ClientInputModel inputModel){
        Set<ConstraintViolation<ClientInputModel>> violations = inputModel.validate();
        StringBuilder error = new StringBuilder();

        if(!violations.isEmpty()){
            violations.stream().forEach(t -> {
                error.append(t.getMessage());
                error.append(System.getProperty("line.separator"));
                logger.warn(t.getMessage());
            });
            throw new ClientException(error.toString());
        }

        return convertToDTO(clientService.addClient(inputModel.getEmail(), inputModel.isAdmin()));
    }

    @PutMapping("/client/{clientId}")
    public ClientDTO updateClient(@PathVariable String clientId, @RequestBody ClientInputModel inputModel){
        Set<ConstraintViolation<ClientInputModel>> violations = inputModel.validate();
        StringBuilder error = new StringBuilder();

        if(!violations.isEmpty()){
            violations.stream().forEach(t -> {
                error.append(t.getMessage());
                error.append(System.getProperty("line.separator"));
                logger.warn(t.getMessage());
            });
            throw new ClientException(error.toString());
        }

        Client client = new Client(inputModel.getEmail(), inputModel.isAdmin());
        return convertToDTO(clientService.updateClient(UUID.fromString(clientId), client));
    }

    @DeleteMapping("/client/{clientId}")
    public void deleteClient(@PathVariable String clientId){
        clientService.deleteClient(UUID.fromString(clientId));
    }

    @GetMapping("/client/{clientId}")
    public ClientDTO getClient(@PathVariable String clientId) {
        Optional<Client> client = clientService.getClient(UUID.fromString(clientId));

        client.orElseThrow(() -> new ClientException(String.format("Client {%s} not found", clientId)));

        return convertToDTO(client.get());
    }

    @GetMapping("/client")
    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientService.getAllClients();

        return clients.stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
    }


    // convert to data transfer object for http requests
    private ClientDTO convertToDTO(Client client) {
        return modelMapper.map(client, ClientDTO.class);
    }
}
