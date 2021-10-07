package CovidLoveit.Service.Services;

import CovidLoveit.Domain.InputModel.ClientInputModel;
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
    public Client addClient(ClientInputModel inputModel) {
        var emailVerification = clientRepository.findByEmail(inputModel.getEmail());
        if (emailVerification.isPresent()) {
            logger.warn(String.format("Email address {%s} has already been taken.", inputModel.getEmail()));
            throw new ClientException(String.format("Email address {%s} has already been taken.", inputModel.getEmail()));
        }

        var roles = inputModel.getRoles();
        Collection<Role> clientRoles = new ArrayList<Role>();
        for(String role : roles) {
            var roleVerification = roleRepository.findByRoleName(role);
            roleVerification.orElseThrow(() -> {
                logger.warn(String.format("Role {%s} not found in DB.", role));
                throw new RoleException(String.format("Role {%s} not found in DB.", role));
            });
            clientRoles.add(roleVerification.get());
        }

        // Encode the user's password before storing their credentials
        inputModel.setPassword(bCryptPasswordEncoder.encode(inputModel.getPassword()));

        var client = new Client(inputModel.getPassword(), clientRoles, inputModel.getEmail());
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
    public Client updateClient(UUID clientId, ClientInputModel inputModel) {
        var clientOptional = clientRepository.findById(clientId);
        clientOptional.orElseThrow(() -> {
            logger.warn(String.format("Client with ID {%s} does not exist in DB.", clientId));
            throw new ClientException(String.format("Client with ID {%s} not found.", clientId));
        });

        var emailVerification = clientRepository.findByEmail(inputModel.getEmail());
        if (emailVerification.get().getClientId() != clientOptional.get().getClientId()) {
            logger.warn(String.format("Client with email {%s} already exists.", inputModel.getEmail()));
            throw new ClientException(String.format("Client with email {%s} already exists.", inputModel.getEmail()));
        }

        var roles = inputModel.getRoles();
        Collection<Role> clientRoles = new ArrayList<Role>();
        for(String role : roles) {
            var roleVerification = roleRepository.findByRoleName(role);
            roleVerification.orElseThrow(() -> {
                logger.warn(String.format("Role {%s} not found in DB.", role));
                throw new RoleException(String.format("Role {%s} not found in DB.", role));
            });
            clientRoles.add(roleVerification.get());
        }

        var clientRecord = clientOptional.get();
        clientRecord.setEmail(inputModel.getEmail());
        clientRecord.setRoles(clientRoles);

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
    public void addRoleToClient(String email, String roleName) {
        var user = clientRepository.findByEmail(email);
        var role = roleRepository.findByRoleName(roleName);

        user.orElseThrow(() -> {
            logger.warn(String.format("Client with email address {%s} does not exist in DB.", email));
            throw new ClientException(String.format("Client with username {%s} not found.", email));
        });

        role.orElseThrow(() -> {
            logger.warn(String.format("Role {%s} does not exist in DB.", roleName));
            throw new ClientException(String.format("Role {%s} does not exist in DB.", roleName));
        });

        user.get().getRoles().add(role.get());
        logger.info(String.format("Added role {%s} to client {%s}.", roleName, email));
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
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    @Override
    public Client getClientByEmail(String email) {
        var client = clientRepository.findByEmail(email);

        client.orElseThrow(() -> {
            logger.warn(String.format("Client with email address {%s} does not exist in database.", email));
            throw new ClientException(String.format("Client with email address {%s} does not exist in database.", email));
        });

        return client.get();
    }

}
