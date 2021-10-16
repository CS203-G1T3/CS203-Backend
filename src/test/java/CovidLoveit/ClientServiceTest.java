package CovidLoveit;

import CovidLoveit.Domain.InputModel.ClientInputModel;
import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Role;
import CovidLoveit.Exception.ClientException;
import CovidLoveit.Exception.RoleException;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import CovidLoveit.Repositories.Interfaces.RoleRepository;
import CovidLoveit.Service.Services.ClientServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.TypedQuery;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AutoCloseable autoCloseable;


    @InjectMocks
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ClientServiceImpl clientService;
    private Client mockedClient;
    private ClientException clientException;
    private Role mockedRole;

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
    void addClient_NewClient_ReturnSavedClient() {
        Client client = new Client ("123456", "handsomejon@gmail.com");
        ClientInputModel clientInputModel = new ClientInputModel(client.getEmail(), client.getPassword());

        when(clientRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(clientRepository.save(any(Client.class))).thenReturn(new Client(clientInputModel.getPassword(), clientInputModel.getEmail()));

        Client savedClient = clientService.addClient(clientInputModel);


        assertNotNull(savedClient);
        verify(clientRepository).findByEmail(client.getEmail());
    }

    @Test
    void addClient_ClientAlreadyExists_ReturnNull() {
        Client client = new Client("123456", "handsomeLad@gmail.com");

        Optional<Client> savedClient = Optional.<Client>of(client);
        when(this.clientRepository.findByEmail((String) any())).thenReturn(savedClient);
        assertThrows(ClientException.class,
                () -> this.clientService.addClient(new ClientInputModel("handsomeLad@gmail.com", "123456")));
        verify(this.clientRepository).findByEmail((String) any());
    }

    @Test
    void updateClient_ClientEmail_ReturnSaveClient() {
        Collection<Role> roleCollection = new ArrayList<>();
        Client client = new Client(UUID.fromString("b4d3065c-ccfe-46bf-928d-ed177dee9fee"), "123456", roleCollection, "tester123@gmail.com");
        Client client2 = new Client(UUID.fromString("b4d3065c-ccfe-46bf-928d-ed177dee9fee"), "123456", roleCollection, "tester@gmail.com");

        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));
        when(clientRepository.findByEmail(any(String.class))).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client2);

        Client updatedClient = clientService.updateClient(client.getClientId(), new ClientInputModel(client2.getEmail(), client2.getPassword()));

        assertEquals(updatedClient.getEmail(), client.getEmail());
        verify(clientRepository).findByEmail(client.getEmail());

    }

    @Test
    void updateClient_ClientNotFound_ReturnClientException() {
        Collection<Role> roleCollection = new ArrayList<>();
        Client client = new Client(UUID.fromString("b4d3065c-ccfe-46bf-928d-ed177dee9fee"), "123456", roleCollection, "tester123@gmail.com");

        Optional<Client> savedClient = Optional.<Client>of(client);
        when(clientRepository.findById(client.getClientId())).thenReturn(Optional.empty());

        assertThrows(ClientException.class, () ->
                clientService.updateClient(client.getClientId(), new ClientInputModel("updatedEmail@gmail.com", "newpassword")));

        verify(clientRepository).findById(client.getClientId());

    }

    @Test
    void addRole_SuccessfullyAddRole_returnRole() {
        Role role = new Role("TESTER");

        when(roleRepository.findByRoleName(any(String.class))).thenReturn(Optional.empty());
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        Role savedRole = clientService.addRole(role);

        assertNotNull(savedRole);
        verify(roleRepository).findByRoleName(role.getRoleName());
        verify(roleRepository).save(savedRole);
    }

    @Test
    void addRole_UnsuccessfullyAddRole_returnClientException() {
        Role role = new Role("Tester");

        when(roleRepository.findByRoleName(any(String.class))).thenReturn(Optional.of(role));

        Role duplicatedRole = new Role("Tester");

        assertThrows(RoleException.class,
                () -> this.clientService.addRole(duplicatedRole));
        verify(roleRepository).findByRoleName(role.getRoleName());

    }

    @Test
    void addRoleToClient_SuccessfullyAddRole_ReturnNothing() {
        Collection<Role> roleCollection = new ArrayList<>();
        Client client = new Client(UUID.fromString("1df791bb-fd17-4c85-a80d-75463524b69d"),  "123456", roleCollection, "email@gmail.com");
        Role role = new Role("tester");

        when(clientRepository.findByEmail(any(String.class))).thenReturn(Optional.of(client));
        when(roleRepository.findByRoleName(any(String.class))).thenReturn(Optional.of(role));

        this.clientService.addRoleToClient(client.getEmail(), role.getRoleName());

        assertNotNull(client.getRoles());

        verify(clientRepository).findByEmail(any(String.class));
        verify(roleRepository).findByRoleName(any(String.class));
    }

    @Test
    void addRoleToClient_UnsuccessfullyAddRole_ReturnClientException() { //client does not exist
        Collection<Role> roleCollection = new ArrayList<>();
        Client client = new Client(UUID.fromString("1df791bb-fd17-4c85-a80d-75463524b69d"),  "123456", roleCollection, "email@gmail.com");
        Role role = new Role("tester");

        when(clientRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        assertThrows(ClientException.class, () -> {
            this.clientService.addRoleToClient(client.getEmail(), role.getRoleName());
        });

        verify(clientRepository).findByEmail(client.getEmail());
    }

    @Test
    void addRoleToClient_UnsuccessfullyRoleDoesNotExist_ReturnClientException() {
        Collection<Role> roleCollection = new ArrayList<>();
        Client client = new Client(UUID.fromString("1df791bb-fd17-4c85-a80d-75463524b69d"),  "123456", roleCollection, "email@gmail.com");
        Role role = new Role("tester");

        when(roleRepository.findByRoleName(any(String.class))).thenReturn(Optional.empty());

        assertThrows(ClientException.class, () -> {
            this.clientService.addRoleToClient(client.getEmail(), role.getRoleName());
        });

        verify(roleRepository).findByRoleName(any(String.class));
    }

    @Test
    void deleteClient_SuccessfullyDelete_ReturnNull() {
        Client client = new Client(UUID.fromString("1df791bb-fd17-4c85-a80d-75463524b69d"),  "123456", null, "email@gmail.com");

        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));

        clientService.deleteClient(client.getClientId());

        assertTrue(clientService.getAllClients().isEmpty());

        verify(clientRepository).findById(client.getClientId());
    }

    @Test
    void deleteClient_Unsuccessfully_ReturnClientException() {
        Client client = new Client(UUID.fromString("1df791bb-fd17-4c85-a80d-75463524b69d"),  "123456", null, "email@gmail.com");

        when(clientRepository.findById(client.getClientId())).thenReturn(Optional.empty());

        assertThrows(ClientException.class, () -> {
            clientService.deleteClient(client.getClientId());
        });

        verify(clientRepository).findById(client.getClientId());
    }

    @Test
    void getClient_SuccessfullyRetrievedCLient_ReturnClient() {
        Client client = new Client(UUID.fromString("1df791bb-fd17-4c85-a80d-75463524b69d"),  "123456", null, "email@gmail.com");

        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));

        Client expectedClient = clientService.getClient(client.getClientId());

        assertNotNull(expectedClient);
        verify(clientRepository).findById(client.getClientId());
    }

    @Test
    void getClient_UnsuccessfullyRetrievedClient_ReturnClientException() {
        Client client = new Client(UUID.fromString("1df791bb-fd17-4c85-a80d-75463524b69d"),  "123456", null, "email@gmail.com");

        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ClientException.class, () -> {
            clientService.getClient(client.getClientId());
        });

        verify(clientRepository).findById(client.getClientId());
    }

    @Test
    void getAllClients_Success_ReturnListClients() {
        ArrayList<Client> tempList = mock(ArrayList.class);

        when(clientRepository.findAll()).thenReturn(tempList);

        List<Client> returnedClientList = clientService.getAllClients();

        assertNotNull(returnedClientList);
        verify(clientRepository).findAll();

    }


    @Test
    void getClientByEmail_Success_ReturnClient() {
        Client client = new Client(UUID.fromString("1df791bb-fd17-4c85-a80d-75463524b69d"),  "123456", null, "email@gmail.com");

        when(clientRepository.findByEmail(any(String.class))).thenReturn(Optional.of(client));

        Client selectedClient = clientService.getClientByEmail(client.getEmail());

        assertEquals(client.getEmail(), selectedClient.getEmail());
        verify(clientRepository).findByEmail(client.getEmail());
    }

    @Test
    void getClientByEmail_UnsuccessfullyEmailNotFound_ReturnClientException() {
        Client client = new Client(UUID.fromString("1df791bb-fd17-4c85-a80d-75463524b69d"),  "123456", null, "email@gmail.com");

        when(clientRepository.findById(client.getClientId())).thenReturn(Optional.empty());

        assertThrows(ClientException.class, () -> {
           clientService.getClient(client.getClientId());
        });

        verify(clientRepository).findById(client.getClientId());
    }

}
