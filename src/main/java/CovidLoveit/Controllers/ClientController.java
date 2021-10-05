package CovidLoveit.Controllers;

import CovidLoveit.Domain.DataTransferObjects.ClientDTO;
import CovidLoveit.Domain.DataTransferObjects.RoleDTO;
import CovidLoveit.Domain.InputModel.ClientInputModel;
import CovidLoveit.Domain.InputModel.RoleInputModel;
import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Role;
import CovidLoveit.Exception.ClientException;

import CovidLoveit.Exception.RoleException;
import CovidLoveit.Service.Services.Interfaces.ClientService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/v1")
public class ClientController {

    private Logger logger = LoggerFactory.getLogger(ClientController.class);
    private final ClientService clientService;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientController(ClientService clientService, ModelMapper modelMapper){
        this.clientService = clientService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/client/add")
    public ResponseEntity<ClientDTO> addClient(@RequestBody ClientInputModel inputModel){
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

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/client/add").toUriString());
        var client = new Client(inputModel.getPassword(),
                inputModel.getRoles(), inputModel.getEmail());

        return ResponseEntity.created(uri).body(convertToClientDTO(clientService.addClient(client)));
    }

    @PostMapping("/role/add")
    public ResponseEntity<RoleDTO> addRole(@RequestBody RoleInputModel inputModel) {
        Set<ConstraintViolation<RoleInputModel>> violations = inputModel.validate();
        StringBuilder error = new StringBuilder();

        if(!violations.isEmpty()){
            violations.stream().forEach(t -> {
                error.append(t.getMessage());
                error.append(System.getProperty("line.separator"));
                logger.warn(t.getMessage());
            });
            throw new RoleException(error.toString());
        }

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/role/add").toUriString());
        var role = new Role(inputModel.getRoleName());

        return ResponseEntity.created(uri).body(convertToRoleDTO(clientService.addRole(role)));
    }

    @PutMapping("/client/{clientId}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable String clientId, @RequestBody ClientInputModel inputModel){
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

        Client client = new Client(inputModel.getClientId(),
                inputModel.getPassword(), inputModel.getRoles(), inputModel.getEmail());
        return ResponseEntity.ok(convertToClientDTO(clientService.updateClient(UUID.fromString(clientId), client)));
    }

    @DeleteMapping("/client/{clientId}")
    public ResponseEntity<?> deleteClient(@PathVariable String clientId){
        clientService.deleteClient(UUID.fromString(clientId));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/role/{roleName}/updateClient/{username}")
    public ResponseEntity<?> addRoleToClient(@PathVariable String username, @PathVariable String roleName) {
        clientService.addRoleToClient(username, roleName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable String clientId) {
        var client = clientService.getClient(UUID.fromString(clientId));

        return ResponseEntity.ok(convertToClientDTO(client));
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<Client> clients = clientService.getAllClients();

        var clientRecords = clients.stream()
        .map(this::convertToClientDTO)
        .collect(Collectors.toList());

        return ResponseEntity.ok(clientRecords);
    }

    @GetMapping("/client/email/{email}")
    public ResponseEntity<ClientDTO> getClientByEmail(@PathVariable String email) {
        var client = clientService.getClientByEmail(email);

        return ResponseEntity.ok(convertToClientDTO(client));
    }

    // convert to data transfer object for http requests
    private ClientDTO convertToClientDTO(Client client) {
        return modelMapper.map(client, ClientDTO.class);
    }

    private RoleDTO convertToRoleDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }
}
