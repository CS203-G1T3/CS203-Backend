package CovidLoveit;

import CovidLoveit.Domain.InputModel.ClientInputModel;
import CovidLoveit.Domain.InputModel.GuidelineInputModel;
import CovidLoveit.Domain.InputModel.IndustryInputModel;
import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Guideline;
import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Domain.Models.Role;
import CovidLoveit.Exception.ClientException;
import CovidLoveit.Exception.GuidelineException;
import CovidLoveit.Exception.IndustryException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class GuidelineServiceTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private IndustryRepository industryRepository;

    @Mock
    private AutoCloseable autoCloseable;

    @Autowired
    private GuidelineRepository guidelineRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private GuidelineServiceImpl guidelineService;

    @InjectMocks
    private ClientServiceImpl clientService;

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
//    @Disabled
    void addGuideline_SuccessfullyAddedGuideline_ReturnGuideline() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));
        Client client = new Client("123456",
                null, "tester123@gmail.com");
        client.setRoles(roles);
        Industry industry = new Industry( "F&B",
                "Hawker", "Chicken Rice Stall");

        Client returnedClient = clientRepository.save(client);
        // This will return your industry object from the DB
        Industry returnType = industryRepository.save(new Industry());
        GuidelineInputModel guidelineInputModel = new GuidelineInputModel(
                true,
                "Can operate",
                5,
                "Maximum capacity 5 pax",
                500000,
                500001,
                "Tested all negatives",
                "Call me maybe",
                "Trace together as one",
                10,
                "Only maximum 10 staff allowed",
                "Please maintain a safe distance of 100m apart",
                "www.tiktok.com",
                returnType.getIndustryId()
        );

        //when
        Guideline returnedGuideline = guidelineService.addGuideline(returnedClient.getClientId().toString(), guidelineInputModel);

        //then
        assertNotNull(returnedGuideline);

    }

    @Test
    void addGuideline_UnsuccessfullyClientNotFound_ReturnClientException() {
        Industry returnType = industryRepository.save(new Industry());
        GuidelineInputModel guidelineInputModel = new GuidelineInputModel(
                true,
                "Can operate",
                5,
                "Maximum capacity 5 pax",
                500000,
                500001,
                "Tested all negatives",
                "Call me maybe",
                "Trace together as one",
                10,
                "Only maximum 10 staff allowed",
                "Please maintain a safe distance of 100m apart",
                "www.tiktok.com",
                returnType.getIndustryId()
        );
        String clientId = UUID.randomUUID().toString();
//        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Client client = new Client();
        Client savedClient = clientRepository.save(client);
//        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        String testUUID = UUID.randomUUID().toString();
//        System.out.println(testUUID);
        assertThrows(ClientException.class, () -> {
           Guideline savedGuideline = guidelineService.addGuideline(testUUID, guidelineInputModel);
        });
    }

    @Test
    void addGuideline_UnsuccessfulIndustryNotFound_ReturnIndustryException() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));

        Client client = new Client("123456",
                null, "tester123@gmail.com");
        client.setRoles(roles);

        Industry industry = new Industry( "F&B",
                "Hawker", "Chicken Rice Stall");


        Client returnedClient = clientRepository.save(client);
        GuidelineInputModel guidelineInputModel = new GuidelineInputModel(
                true,
                "Can operate",
                5,
                "Maximum capacity 5 pax",
                500000,
                500001,
                "Tested all negatives",
                "Call me maybe",
                "Trace together as one",
                10,
                "Only maximum 10 staff allowed",
                "Please maintain a safe distance of 100m apart",
                "www.tiktok.com",
                UUID.randomUUID()
        );


        assertThrows(IndustryException.class, () -> {
           Guideline savedGuideline = guidelineService.addGuideline(returnedClient.getClientId().toString(), guidelineInputModel);
        });

    }

    @Test
    void updateGuideline_Successfully_ReturnGuideline() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));

        Client client = new Client("123456",
                null, "tester123@gmail.com");
        client.setRoles(roles);

        Industry industry = new Industry( "F&B",
                "Hawker", "Chicken Rice Stall");
        IndustryInputModel industryInputModel = new IndustryInputModel();

        Guideline guideline = new Guideline(
                true,
                "Can operate",
                5,
                "Maximum capacity 5 pax",
                500000,
                500001,
                "Tested all negatives",
                "Call me maybe",
                "Trace together as one",
                10,
                "Only maximum 10 staff allowed",
                "Please maintain a safe distance of 100m apart",
                "www.tiktok.com",
                industry
        );
        GuidelineInputModel guidelineInputModel = new GuidelineInputModel(
                true,
                "Can operate",
                5,
                "Maximum capacity 5 pax",
                500000,
                500001,
                "Tested all negatives",
                "Call me maybe",
                "Trace together as one",
                10,
                "Only maximum 10 staff allowed",
                "Please maintain a safe distance of 100m apart",
                "www.tiktok.com",
                UUID.randomUUID()
        );

        Guideline savedGuideline = guidelineRepository.save(guideline);
        Industry savedIndustry = industryRepository.save(industry);
        Client savedClient = clientRepository.save(client);
        guidelineInputModel.setGuidelineId(savedGuideline.getGuidelineId());
        guidelineInputModel.setIndustryId(savedIndustry.getIndustryId());
        industryInputModel.setIndustryId(savedIndustry.getIndustryId());

        //when
        Guideline updatedGuideline = guidelineService.updateGuideline(savedClient.getClientId().toString(),
                guidelineInputModel);

        //then
        assertEquals(updatedGuideline.getGuidelineId(), savedGuideline.getGuidelineId());


    }

    @Test
    void updateGuideline_UnsuccessfullyGuidelineNotFound_ReturnGuidelineException() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));

        Client client = new Client("123456",
                null, "tester123@gmail.com");
        client.setRoles(roles);
        Client savedClient = clientRepository.save(client);

        GuidelineInputModel guidelineInputModel = new GuidelineInputModel(
                true,
                "Can operate",
                5,
                "Maximum capacity 5 pax",
                500000,
                500001,
                "Tested all negatives",
                "Call me maybe",
                "Trace together as one",
                10,
                "Only maximum 10 staff allowed",
                "Please maintain a safe distance of 100m apart",
                "www.tiktok.com",
                UUID.randomUUID()
        );
        guidelineInputModel.setGuidelineId(UUID.randomUUID());


        assertThrows(GuidelineException.class, () -> {
           Guideline updatedGuideline = guidelineService.updateGuideline(savedClient.getClientId().toString(), guidelineInputModel);
        });

    }

    @Test
    void updateGuideline_UnsuccessfullyIndustryNotFound_ReturnIndustryException() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));

        Client client = new Client("123456",
                null, "tester123@gmail.com");
        client.setRoles(roles);
        Client savedClient = clientRepository.save(client);

        Industry industry = new Industry();

        GuidelineInputModel guidelineInputModel = new GuidelineInputModel(
                true,
                "Can operate",
                5,
                "Maximum capacity 5 pax",
                500000,
                500001,
                "Tested all negatives",
                "Call me maybe",
                "Trace together as one",
                10,
                "Only maximum 10 staff allowed",
                "Please maintain a safe distance of 100m apart",
                "www.tiktok.com",
                UUID.randomUUID()
        );
        Guideline guideline = new Guideline(
                true,
                "Can operate",
                5,
                "Maximum capacity 5 pax",
                500000,
                500001,
                "Tested all negatives",
                "Call me maybe",
                "Trace together as one",
                10,
                "Only maximum 10 staff allowed",
                "Please maintain a safe distance of 100m apart",
                "www.tiktok.com",
                industry
        );

        Guideline savedGuideline = guidelineRepository.save(guideline);
        guidelineInputModel.setGuidelineId(savedGuideline.getGuidelineId());

        assertThrows(IndustryException.class, () -> {
           Guideline updatedGuideline = guidelineService.updateGuideline(savedClient.getClientId().toString(), guidelineInputModel);
        });
    }

    @Test
    void deleteGuideline_Successfully_ReturnNull() {
        Industry industry = new Industry();

        Guideline guideline = new Guideline(
                true,
                "Can operate",
                5,
                "Maximum capacity 5 pax",
                500000,
                500001,
                "Tested all negatives",
                "Call me maybe",
                "Trace together as one",
                10,
                "Only maximum 10 staff allowed",
                "Please maintain a safe distance of 100m apart",
                "www.tiktok.com",
                industry
        );

        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));
        Client client = new Client("123456",
                null, "tester123@gmail.com");
        client.setRoles(roles);

        Client savedClient = clientRepository.save(client);
        Guideline savedGuideline = guidelineRepository.save(guideline);

        guidelineService.deleteGuideline(savedClient.getClientId().toString(), savedGuideline.getGuidelineId().toString());

        assertTrue(guidelineRepository.findById(savedGuideline.getGuidelineId()).isEmpty());
    }
}
