package CovidLoveit.Service.Services;

import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.CustomUserDetails;
import CovidLoveit.Domain.Models.Role;
import CovidLoveit.Exception.ClientException;
import CovidLoveit.Exception.RoleException;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import CovidLoveit.Repositories.Interfaces.RoleRepository;
import CovidLoveit.Service.Services.Interfaces.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.*;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    // Use this logger object to log information of user's actions
    private Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // Injecting the required dependencies here
    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.clientRepository = clientRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Client addClient(Client client) {
        var emailVerification = clientRepository.findByEmail(client.getEmail());
        if (emailVerification.isPresent()) {
            logger.warn(String.format("Email address {%s} has already been taken.", client.getEmail()));
            throw new ClientException(String.format("Email address {%s} has already been taken.", client.getEmail()));
        }

        var usernameVerification = clientRepository.findByUsername(client.getUsername());
        if (usernameVerification.isPresent()) {
            logger.warn(String.format("Username {%s} has already been taken.", client.getUsername()));
            throw new ClientException(String.format("Username {%s} has already been taken.", client.getUsername()));
        }

        // Encode the user's password before storing their credentials
        client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
        var savedClient = clientRepository.save(client);

        logger.info(String.format("Successfully added client {%s}", savedClient.getClientId()));
        return savedClient;
    }

    @Override
    public Role addRole(Role role){
        var roleRecord = roleRepository.findByRoleName(role.getRoleName());
        if (roleRecord.isPresent()) {
            logger.warn(String.format("Role {%s} already exists in DB.", role.getRoleName()));
            throw new RoleException(String.format("Role {%s} already exists", role));
        }

        var savedRole = roleRepository.save(role);
        logger.info(String.format("Successfully added role {%s}", savedRole.getRoleName()));
        return savedRole;
    }

    @Override
    public Client updateClient(UUID clientId, Client client) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);

        clientOptional.orElseThrow(() -> {
            logger.warn(String.format("Client with ID {%s} does not exist in DB.", clientId));
            throw new ClientException(String.format("Client with ID {%s} not found.", clientId));
        });

        var emailVerification = clientRepository.findByEmail(client.getEmail());
        if (emailVerification.get().getClientId() != clientOptional.get().getClientId()) {
            logger.warn(String.format("Client with email {%s} already exists.", client.getEmail()));
            throw new ClientException(String.format("Client with email {%s} already exists.", client.getEmail()));
        }

        var usernameVerification = clientRepository.findByUsername(client.getUsername());
        if (usernameVerification.get().getClientId() != clientOptional.get().getClientId()) {
            logger.warn(String.format("Username {%s} has already been taken.", client.getUsername()));
            throw new ClientException(String.format("Username {%s} has already been taken.", client.getUsername()));
        }

        var clientRecord = clientOptional.get();
        clientRecord.setEmail(client.getEmail());
        clientRecord.setUsername(client.getUsername());

        logger.info(String.format("Successfully updated client with ID {%s}", clientId));
        return clientRepository.save(clientRecord);
    }

    @Override
    public void deleteClient(UUID clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);

        clientOptional.orElseThrow(() -> {
            logger.warn(String.format("Client with ID {%s} does not exist in DB.", clientId));
            throw new ClientException(String.format("Client with ID {%s} not found.", clientId));
        });

        clientRepository.delete(clientOptional.get());
        logger.info(String.format("Successfully removed client with ID {%s}", clientId));
    }

    @Override
    public void addRoleToClient(String username, String roleName) {
        var user = clientRepository.findByUsername(username);
        var role = roleRepository.findByRoleName(roleName);

        user.orElseThrow(() -> {
            logger.warn(String.format("Client with username {%s} does not exist in DB.", username));
            throw new ClientException(String.format("Client with username {%s} not found.", username));
        });

        role.orElseThrow(() -> {
            logger.warn(String.format("Role {%s} does not exist in DB.", roleName));
            throw new ClientException(String.format("Role {%s} does not exist in DB.", roleName));
        });

        user.get().getRoles().add(role.get());
        logger.info(String.format("Added role {%s} to client {%s}.", roleName, username));
    }

    @Override
    public Client getClient(UUID clientId){
        var clientOptional = clientRepository.findById(clientId);

        clientOptional.orElseThrow(() -> {
            logger.warn(String.format("Client with ID {%s} does not exist in DB.", clientId));
            throw new ClientException(String.format("Client with ID {%s} not found.", clientId));
        });

        return clientOptional.get();
    }

    @Override
    public Client getClientByUsername(String username) {
        var client = clientRepository.findByUsername(username);
        client.orElseThrow(() -> {
            logger.warn(String.format("Client with username {%s} does not exist in database.", username));
            throw new ClientException(String.format("Client with username {%s} does not exist in database.", username));
        });

        return client.get();
    }

    @Override
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

}
