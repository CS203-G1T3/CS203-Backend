package CovidLoveit;

import CovidLoveit.Domain.InputModel.ClientInputModel;
import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Role;
import CovidLoveit.Exception.ClientException;
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
//        verify(clientRepository).save(savedClient);

    }

    @Test
    @Disabled
    void addClient_SameClientEmail_ReturnClientException() {
        Client client = new Client("123456", "tester123@gmail.com");
        Client newClient = new Client("123456", "tester123@gmail.com");

        when(clientRepository.findByEmail(newClient.getEmail())).thenReturn(Optional.of(newClient));
        when(clientRepository.save(newClient)).thenReturn(newClient);

        Client updatedClient = clientService.addClient(new ClientInputModel(newClient.getEmail(), newClient.getPassword()));

        Throwable exception = assertThrows(ClientException.class, () -> clientRepository.findByEmail(newClient.getEmail()));
        clientRepository.save(newClient);
        assertThat(exception).isInstanceOf(ClientException.class)
                .hasMessage(exception.getMessage());

        verify(clientRepository).findByEmail(newClient.getEmail());
    }

    @Test
    @Disabled
    void updateClient_ClientEmail_ReturnSaveClient() {
        Collection<Role> roleCollection = new ArrayList<>();
        Client client = new Client(UUID.fromString("b4d3065c-ccfe-46bf-928d-ed177dee9fee"), "123456", roleCollection, "tester123@gmail.com");
        Client client2 = new Client(UUID.fromString("b4d3065c-ccfe-46bf-928d-ed177dee9fee"), "123456", roleCollection, "tester@gmail.com");

        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));
        when(clientRepository.findByEmail(any(String.class))).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client2);

        Client updatedClient = clientService.updateClient(client.getClientId(), new ClientInputModel(client2.getEmail(), client2.getPassword()));

        assertNotNull(updatedClient);
        verify(clientRepository).findByEmail(client.getEmail());

    }

    @Test
    @Disabled
    void updateClient_ClientNotFound_ReturnNull() {
//        Collection<Role> roleCollection = new ArrayList<>();
        Client client = new Client( "123456", "tester123@gmail.com");

        when(clientRepository.findById(client.getClientId())).thenReturn(Optional.of(client));
        when(clientRepository.findByEmail(client.getEmail())).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client updatedClient = clientService.updateClient(client.getClientId(), new ClientInputModel(client.getEmail(), client.getPassword()));

        assertNull(updatedClient);

        verify(clientRepository).findById(client.getClientId());
        verify(clientRepository).findByEmail(client.getEmail());
        verify(clientRepository).save(client);

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
//    @Disabled
    void deleteClient_SuccessfullyDelete_ReturnNull() {
        Client client = new Client(UUID.fromString("1df791bb-fd17-4c85-a80d-75463524b69d"),  "123456", null, "email@gmail.com");

        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));

        clientService.deleteClient(client.getClientId());

         verify(clientRepository).findById(client.getClientId());
    }

    @Test
    void getClient_SuccessfullyRetrievedCLient_ReturnClient() {
        Client client = new Client(UUID.fromString("1df791bb-fd17-4c85-a80d-75463524b69d"),  "123456", null, "email@gmail.com");

        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));

        Client gotClient = clientService.getClient(client.getClientId());

        assertNotNull(gotClient);
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

}
