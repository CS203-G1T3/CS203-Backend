package CovidLoveit.Service.Services;

import CovidLoveit.Domain.InputModel.ClientInputModel;
import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Role;
import CovidLoveit.Exception.ClientException;
import CovidLoveit.Exception.RoleException;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import CovidLoveit.Repositories.Interfaces.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class ClientServiceTest {

    @Autowired
    private ClientRepository clientRepository;
    private ClientServiceImpl clientService;

    @Autowired
    private RoleRepository roleRepository;

    @Mock
    private AutoCloseable autoCloseable;

    @InjectMocks
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        clientService = new ClientServiceImpl(clientRepository, roleRepository, bCryptPasswordEncoder);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void addClient_Successful_ReturnClient() {
        //given
        Client client = new Client("123456", "email@gmail.com");
        ClientInputModel clientInputModel = new ClientInputModel("email@gmail.com", "123456");

        //when
        Client savedClient = clientService.addClient(clientInputModel);

        //then
        assertNotNull(savedClient);
    }

    @Test
    void addClient_UnsuccessfulClientAlreadyExist_ReturnClientException() {
        ClientInputModel clientInputModel = new ClientInputModel("email@gmail.com", "123456");
        Client savedClient = new Client("password", "email@gmail.com");
        clientRepository.save(savedClient);

        assertThrows(ClientException.class, () -> {
            Client addedClient = clientService.addClient(clientInputModel);
        });
    }

    @Test
    void addRole_Successful_ReturnRole() {
        Role role = new Role("TESTER");
        Role savedRole = clientService.addRole(role);

        assertEquals(savedRole.getRoleName(), role.getRoleName());
    }

    @Test
    void addRole_UnsuccessfullyRoleALreadyExist_ReturnRoleException() {
        Role role = new Role("TESTER");
        roleRepository.save(role);

        assertThrows(RoleException.class, () -> {
          Role savedRole = clientService.addRole(role);
        });
    }

    @Test
    void updateClient_Successfully_ReturnClient() {
        Client existingClient =  clientRepository.save(new Client("password", "email@gmail.com"));
        ClientInputModel changedEmailClient = new ClientInputModel("newEmail@gmail.com", "password");

        Client updatedClient = clientService.updateClient(existingClient.getClientId(), changedEmailClient);

        assertEquals(updatedClient.getEmail(), changedEmailClient.getEmail());
        assertTrue(updatedClient.getClientId().equals(existingClient.getClientId())
                && updatedClient.getEmail().equals(changedEmailClient.getEmail()));
    }

    @Test
    void updatedClient_UnsuccessfulClientEmailAlreadyExist_ReturnClientException() {
        Client existingClient =  clientRepository.save(new Client("password", "email@gmail.com"));
        ClientInputModel changedEmailClient = new ClientInputModel("email@gmail.com", "password");

        //finds email that exists but do not match the ID of the client
        assertThrows(ClientException.class, () -> {
            Client updatedClient = clientService.updateClient(UUID.randomUUID(), changedEmailClient);

        });
    }

    @Test
    void updatedClient_UnsuccessfulClientNotFound_ReturnClientException() {
        ClientInputModel clientInputModel = new ClientInputModel("email@gmail.com", "123456");

        assertThrows(ClientException.class, () -> {
            Client updatedClient = clientService.updateClient(UUID.randomUUID(), clientInputModel);
        });
    }

    @Test
    void deleteClient_Successful() {
        Client existingClient =  clientRepository.save(new Client("password", "email@gmail.com"));

        clientService.deleteClient(existingClient.getClientId());

        assertTrue(clientRepository.findById(existingClient.getClientId()).isEmpty());
    }

    @Test
    void deleteClient_UnsuccessfulClientNotFound_ReturnClientException() {
        //when a UUID that does not exist is given, client exception is thrown
        assertThrows(ClientException.class, () -> {
            clientService.deleteClient(UUID.randomUUID());
        });
    }

    @Test
    void addRoleToClient_Successful_ReturnVoid() {
        Client existingClient =  clientRepository.save(new Client("password", "email@gmail.com"));

        Role role = roleRepository.save(new Role("ADMIN"));
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);

        clientService.addRoleToClient(existingClient.getEmail(), role.getRoleName());

        assertTrue(existingClient.getRoles().contains(role));
    }

    @Test
    void addRoleToClient_UnsuccessfulClientNotFound_ReturnClientException() {
        Client client = new Client("password", "email@gmail.com");

        Role role = roleRepository.save(new Role("ADMIN"));
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);

        assertThrows(ClientException.class, () -> {
            clientService.addRoleToClient(client.getEmail(), role.getRoleName());
        });
    }

    @Test
    void addRoleToClient_UnsuccessfulRoleNotFound_ReturnClientException() {
        Client existingClient =  clientRepository.save(new Client("password", "email@gmail.com"));

        Role role = new Role("ADMIN");
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);

        assertThrows(ClientException.class, () -> {
            clientService.addRoleToClient(existingClient.getEmail(), role.getRoleName());
        });
    }

    @Test
    void getClient_Success_ReturnClient() {
        Client existingClient =  clientRepository.save(new Client("password", "email@gmail.com"));

        Client returnedClient = clientService.getClient(existingClient.getClientId());

        assertTrue(returnedClient.getClientId().equals(existingClient.getClientId())
                && returnedClient.getEmail().equals(existingClient.getEmail()));
    }

    @Test
    void getClient_UnsuccessfulClientNotFound_ReturnClientException() {
        assertThrows(ClientException.class, () -> {
            Client returnedClient = clientService.getClient(UUID.randomUUID());
        });
    }

    @Test
    void getAllClients_Successful_ReturnListClient() {
        Client existingClient =  clientRepository.save(new Client("password", "email@gmail.com"));
        Client existingClient1 =  clientRepository.save(new Client("password", "yahoo@gmail.com"));

        List<Client> clientList = clientService.getAllClients();

        assertTrue(clientList.get(0).getClientId().equals(existingClient.getClientId()));
    }

    @Test
    void getClientByEmail_Successful_returnClient() {
        Client existingClient =  clientRepository.save(new Client("password", "email@gmail.com"));

        Client returnedClient = clientService.getClientByEmail(existingClient.getEmail());

        assertTrue(returnedClient.getClientId().equals(existingClient.getClientId()));
    }

    @Test
    void getClientByEmail_UnsuccessfulClientNotFound_ReturnClientException() {
        Client client = new Client("123456", "outlook@gmai.com");
        assertThrows(ClientException.class, () -> {
            Client returnedClient = clientService.getClientByEmail(client.getEmail());
        });
    }



}
