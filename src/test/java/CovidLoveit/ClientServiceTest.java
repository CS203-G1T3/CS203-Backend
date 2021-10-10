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

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    private RoleRepository roleRepository;
    private AutoCloseable autoCloseable;


    @InjectMocks
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ClientServiceImpl clientService;
    private Client mockedClient;
    private ClientException clientException;
    private Role mockedRole;

    @BeforeEach
    void setUp() {
//        autoCloseable = MockitoAnnotations.openMocks(this);
        clientService = new ClientServiceImpl(clientRepository, roleRepository, bCryptPasswordEncoder);
    }

//    @AfterEach
//    void tearDown() throws Exception {
//        autoCloseable.close();
//    }

    @Test
    void addClient_NewClient_ReturnSavedClient() {
        Client client = new Client ("123456", "handsomejon@gmail.com");

        when(clientRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client savedClient = clientService.addClient(new ClientInputModel(client.getEmail(), client.getPassword()));

        assertNotNull(savedClient);
        verify(clientRepository).findByEmail(client.getEmail());
        verify(clientRepository).save(client);

    }

    @Test
    @Disabled
    void addClient_SameClientEmail_ReturnClientException() {
        Client client = new Client("123456", "tester123@gmail.com");
        Client newClient = new Client("123456", "tester123@gmail.com");

        when(clientRepository.findByEmail(newClient.getEmail())).thenReturn(Optional.of(newClient));
        when(clientRepository.save(newClient)).thenReturn(newClient);

        Client updatedClient = clientService.addClient(newClient);

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
//
//        OngoingStubbing<Optional<Client>> optionalOngoingStubbing = when(clientRepository.findByEmail(client.getEmail())).thenReturn(client);
//        when(clientRepository.save(any(Client.class))).thenReturn(client2);

//        mockedClient = new Client(UUID.fromString("b4d3065c-ccfe-46bf-928d-ed177dee9fee"), "123456", roleCollection, "tester123@gmail.com");
//        Client client = mock(Client.class);
//        client.setEmail("tester2345");
//        Client newClient = mock(Client.class);
//        newClient.setEmail("tester123@gmail.com");
//        Client mockClient = new Client(UUID.fromString("b4d3065c-ccfe-46bf-928d-ed177dee9fee"), "123456", roleCollection, "tester123@gmail.com");
        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));
        when(clientRepository.findByEmail(any(String.class))).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client2);

        Client updatedClient = clientService.updateClient(client.getClientId(), client2);

        assertNotNull(updatedClient);
        verify(clientRepository).findByEmail(client.getEmail());

    }

    @Test
    @Disabled
    void updateClient_ClientNotFound_ReturnException() {
//        Collection<Role> roleCollection = new ArrayList<>();
        Client client = new Client( "123456", "tester123@gmail.com");

        when(clientRepository.findById(client.getClientId())).thenReturn(Optional.of(client));
        when(clientRepository.findByEmail(client.getEmail())).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Throwable exception = assertThrows(ClientException.class, () -> clientRepository.findByEmail(client.getEmail()));
        clientRepository.save(client);
        assertEquals(exception, exception.getMessage());


        verify(clientRepository).findByEmail(client.getEmail());

    }

    @Test
    void addRole_SuccessfullyAddRole_returnRole() {
        mockedRole = new Role("TESTER");

        when(roleRepository.findByRoleName(any(String.class))).thenReturn(Optional.empty());
        when(roleRepository.save(any(Role.class))).thenReturn(mockedRole);

        Role savedRole = roleRepository.save(mockedRole);

        assertNotNull(savedRole);
        verify(roleRepository).findByRoleName(mockedRole.getRoleName());
        verify(roleRepository).save(savedRole);
    }

    @Test
    void deleteClient_SuccessfullyDelete_ReturnNull() {
        mockedClient = new Client("123456", "email@gmail.com");

        doNothing().when(clientRepository.findById(any(UUID.class)));

        clientRepository.delete(mockedClient);
    }

}
