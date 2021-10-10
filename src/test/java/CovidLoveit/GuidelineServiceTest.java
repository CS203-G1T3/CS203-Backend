package CovidLoveit;

import CovidLoveit.Domain.InputModel.ClientInputModel;
import CovidLoveit.Domain.InputModel.GuidelineInputModel;
import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Guideline;
import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import CovidLoveit.Repositories.Interfaces.GuidelineRepository;
import CovidLoveit.Repositories.Interfaces.IndustryRepository;
import CovidLoveit.Repositories.Interfaces.RoleRepository;
import CovidLoveit.Service.Services.ClientServiceImpl;
import CovidLoveit.Service.Services.GuidelineServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GuidelineServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private IndustryRepository industryRepository;

    @Mock
    private AutoCloseable autoCloseable;

    @Mock
    private GuidelineRepository guidelineRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private GuidelineServiceImpl guidelineService;

    @InjectMocks
    private GuidelineInputModel mockGuidelineInputModel;

    @InjectMocks
    private Client mockClient;

    @InjectMocks
    private Guideline mockGuideline;

    @InjectMocks
    private Industry mockIndustry;

    @InjectMocks
    private ClientServiceImpl clientService;

    @InjectMocks
    private ClientInputModel mockClientInputModel;

    @InjectMocks
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        guidelineService = new GuidelineServiceImpl(guidelineRepository, clientRepository, industryRepository);
        clientService = new ClientServiceImpl(clientRepository, roleRepository, bCryptPasswordEncoder);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void addGuideline_SuccessfullyAddedGuideline_ReturnGuideline() {
//        mockClientInputModel.setPassword("123456");
//        mockClient = clientService.addClient(mockClientInputModel);
        Client client = new Client(UUID.fromString("b4d3065c-ccfe-46bf-928d-ed177dee9fee"), "123456", null, "tester123@gmail.com");
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        clientRepository.save(client);

        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        when(industryRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        when(guidelineRepository.save(any(Guideline.class))).thenReturn(mockGuideline);

//        Client savedClient = clientService.addClient(new ClientInputModel(client.getEmail(), client.getPassword()));
        Guideline savedGuideline = guidelineService.addGuideline(client.getClientId().toString(), mockGuidelineInputModel);

        assertNotNull(savedGuideline);
        verify(clientRepository).findById(client.getClientId());
        verify(industryRepository).findById(mockIndustry.getIndustryId());
        verify(guidelineRepository).save(mockGuideline);
    }

    @Test
    @Disabled
    void deleteGuideline_SuccessfullyDeletedGuideline_void() {
        //Client client = new Client(UUID.fromString("1df791bb-fd17-4c85-a80d-75463524b69d"),  "123456", null, "email@gmail.com");


        when(guidelineRepository.findById(any(UUID.class))).thenReturn(Optional.of(mockGuideline));
        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(mockClient));

        guidelineRepository.delete(mockGuideline);

        verify(guidelineRepository).findById(mockGuideline.getGuidelineId());
        verify(clientRepository).findById(mockClient.getClientId());

    }
}
