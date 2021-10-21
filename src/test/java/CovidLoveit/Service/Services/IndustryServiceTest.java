package CovidLoveit.Service.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import CovidLoveit.Domain.InputModel.IndustryInputModel;
import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Domain.Models.Role;
import CovidLoveit.Exception.ClientException;
import CovidLoveit.Exception.IndustryException;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import CovidLoveit.Repositories.Interfaces.IndustryRepository;
import CovidLoveit.Repositories.Interfaces.RoleRepository;
import CovidLoveit.Service.Services.ClientServiceImpl;
import CovidLoveit.Service.Services.IndustryServiceImpl;

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

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class IndustryServiceTest {
    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AutoCloseable autoCloseable;
    
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private IndustryServiceImpl industryService;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        clientService = new ClientServiceImpl(clientRepository, roleRepository, bCryptPasswordEncoder);
        industryService = new IndustryServiceImpl(industryRepository, clientRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void addIndustry_SuccessfullyAddedIndustry_ReturnIndustry(){
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));
        Client client = new Client("123456", roles, "tester@gmail.com");
        Client savedClient = clientRepository.save(client);

        Industry industry = new Industry("industryName", "industrySubtype", "industryDesc");
        IndustryInputModel industryInputModel = new IndustryInputModel(industry.getIndustryName(), industry.getIndustrySubtype(), industry.getIndustryDesc());
        Industry savedIndustry = industryService.addIndustry(savedClient.getClientId().toString(), industryInputModel);
        assertNotNull(savedIndustry);
    }

    @Test
    void addIndustry_ClientNotFound_ThrowClientException(){
        Industry industry = new Industry("industryName", "industrySubtype", "industryDesc");
        IndustryInputModel industryInputModel = new IndustryInputModel(industry.getIndustryName(), industry.getIndustrySubtype(), industry.getIndustryDesc());
        assertThrows(ClientException.class, () -> {
            industryService.addIndustry(UUID.randomUUID().toString(), industryInputModel);
        });
    }

    @Test
    void updateIndustry_SuccessfullyUpdatedIndustry_ReturnUpdatedIndustry(){
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));
        Client client = new Client("123456", roles, "tester@gmail.com");
        Client savedClient = clientRepository.save(client);

        Industry industry = new Industry("industryName", "industrySubtype", "industryDesc");
        Industry savedIndustry = industryRepository.save(industry);

        IndustryInputModel industryInputModel = new IndustryInputModel(savedIndustry.getIndustryId(), "newName", "newSubtype", "newDesc");
        Industry updatedIndustry = industryService.updateIndustry(savedClient.getClientId().toString(), industryInputModel);
        assertEquals(savedIndustry.getIndustryId(), updatedIndustry.getIndustryId());
        assertEquals(industryInputModel.getIndustryName(), updatedIndustry.getIndustryName());
        assertEquals(industryInputModel.getIndustrySubtype(), updatedIndustry.getIndustrySubtype());
        assertEquals(industryInputModel.getIndustryDesc(), updatedIndustry.getIndustryDesc());
    }

    @Test
    void updateIndustry_IndustryNotFound_ThrowIndustryException(){
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));
        Client client = new Client("123456", roles, "tester@gmail.com");
        Client savedClient = clientRepository.save(client);

        IndustryInputModel industryInputModel = new IndustryInputModel(UUID.randomUUID(), "newName", "newSubtype", "newDesc");
        assertThrows(IndustryException.class, () -> {
            industryService.updateIndustry(savedClient.getClientId().toString(), industryInputModel);
        });
    }

    @Test
    void updateIndustry_ClientNotFound_ThrowClientException(){
        Industry industry = new Industry("industryName", "industrySubtype", "industryDesc");
        Industry savedIndustry = industryRepository.save(industry);

        IndustryInputModel industryInputModel = new IndustryInputModel(savedIndustry.getIndustryId(), "newName", "newSubtype", "newDesc");
        assertThrows(ClientException.class, () -> {
            industryService.updateIndustry(UUID.randomUUID().toString(), industryInputModel);
        });
    }

    @Test
    void deleteIndustry_SuccessfullyDeletedIndustry_Return(){
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));
        Client client = new Client("123456", roles, "tester@gmail.com");
        Client savedClient = clientRepository.save(client);

        Industry industry = new Industry("industryName", "industrySubtype", "industryDesc");
        Industry savedIndustry = industryRepository.save(industry);

        industryService.deleteIndustry(savedClient.getClientId().toString(), savedIndustry.getIndustryId());
        assertTrue(industryRepository.findById(savedIndustry.getIndustryId()).isEmpty());
    }

    @Test
    void deleteIndustry_IndustryNotFound_ThrowIndustryException(){
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));
        Client client = new Client("123456", roles, "tester@gmail.com");
        Client savedClient = clientRepository.save(client);

        assertThrows(IndustryException.class, () -> {
            industryService.deleteIndustry(savedClient.getClientId().toString(), UUID.randomUUID());
        });
    }

    @Test
    void deleteIndustry_ClientNotFound_ThrowClientException(){
        Industry industry = new Industry("industryName", "industrySubtype", "industryDesc");
        Industry savedIndustry = industryRepository.save(industry);

        assertThrows(ClientException.class, () -> {
            industryService.deleteIndustry(UUID.randomUUID().toString(), savedIndustry.getIndustryId());
        });
    }

    @Test
    void getIndustry_SuccessfullyRetrievedIndustry_ReturnIndustry(){
        Industry industry = new Industry("industryName", "industrySubtype", "industryDesc");
        Industry savedIndustry = industryRepository.save(industry);

        Optional<Industry> toReturn = industryService.getIndustry(savedIndustry.getIndustryId());
        assertNotNull(toReturn);
    }

    @Test
    void getAllIndustrySubtypes_SuccessfullyRetrievedIndustrySubtypes_ReturnIndustrySubtypes(){
        Industry industry = new Industry("industryName", "industrySubtype", "industryDesc");
        Industry industry2 = new Industry("industryName2", "industrySubtype2", "industryDesc2");
        Industry industry3 = new Industry("industryName3", "industrySubtype3", "industryDesc3");
        Industry savedIndustry = industryRepository.save(industry);
        Industry savedIndustry2 = industryRepository.save(industry2);
        Industry savedIndustry3 = industryRepository.save(industry3);

        List<Industry> returnedList = industryService.getAllIndustrySubtypes();
        assertNotNull(returnedList);
        assertTrue(returnedList.contains(savedIndustry));
        assertTrue(returnedList.contains(savedIndustry2));
        assertTrue(returnedList.contains(savedIndustry3));
    }

    @Test
    void getAllIndustries_SuccessfullyRetrievedIndustries_ReturnIndustries(){
        Industry industry = new Industry("industryName", "industrySubtype", "industryDesc");
        Industry industry2 = new Industry("industryName2", "industrySubtype2", "industryDesc2");
        Industry industry3 = new Industry("industryName3", "industrySubtype3", "industryDesc3");
        industryRepository.save(industry);
        industryRepository.save(industry2);
        industryRepository.save(industry3);

        List<String> returnedList = industryService.getAllIndustries();
        assertNotNull(returnedList);
        assertTrue(returnedList.contains(industry.getIndustryName()));
        assertTrue(returnedList.contains(industry2.getIndustryName()));
        assertTrue(returnedList.contains(industry3.getIndustryName()));
    }

    @Test
    void getIndustrySubtypesByIndustry_ExistingIndustry_ReturnIndustrySubtypes(){
        Industry industry = new Industry("industryName", "industrySubtype", "industryDesc");
        Industry industry2 = new Industry("industryName", "industrySubtype2", "industryDesc2");
        Industry industry3 = new Industry("industryName", "industrySubtype3", "industryDesc3");
        Industry savedIndustry = industryRepository.save(industry);
        Industry savedIndustry2 = industryRepository.save(industry2);
        Industry savedIndustry3 = industryRepository.save(industry3);
        List<Industry> returnedList = industryService.getIndustrySubtypesByIndustry(industry.getIndustryName());
        assertNotNull(returnedList);
        assertTrue(returnedList.contains(savedIndustry));
        assertTrue(returnedList.contains(savedIndustry2));
        assertTrue(returnedList.contains(savedIndustry3));
    }
}
