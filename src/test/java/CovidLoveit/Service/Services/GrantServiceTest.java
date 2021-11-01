package CovidLoveit.Service.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

import CovidLoveit.Domain.InputModel.GrantInputModel;
import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Grant;
import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Domain.Models.Role;
import CovidLoveit.Exception.ClientException;
import CovidLoveit.Exception.GrantException;
import CovidLoveit.Exception.IndustryException;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import CovidLoveit.Repositories.Interfaces.GrantRepository;
import CovidLoveit.Repositories.Interfaces.IndustryRepository;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class GrantServiceTest {
    @Autowired
    private GrantRepository grantRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Mock
    private AutoCloseable autoCloseable;

    @InjectMocks
    private GrantServiceImpl grantService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        grantService = new GrantServiceImpl(grantRepository, clientRepository, industryRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void addGrant_SuccessfullyAddedGrant_ReturnGrant(){
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));
        Client client = new Client("123456", roles, "tester@gmail.com");
        Client savedClient = clientRepository.save(client);

        Grant grant = new Grant("grantName", "provider", "grantDesc", 0, "eligibilityCriteria", "applicationProcess", "benefits", "grantLink");
        GrantInputModel grantInputModel = new GrantInputModel(grant.getGrantName(), grant.getProvider(), grant.getGrantDesc(), grant.getValue(), grant.getEligibilityCriteria(), grant.getApplicationProcess(), grant.getBenefits(), grant.getGrantLink());
        Grant savedGrant = grantService.addGrant(savedClient.getClientId().toString(), grantInputModel);
        assertNotNull(savedGrant);
    }

    @Test
    void addGrant_GrantAlreadyExists_ThrowGrantException(){
        Role savedRole = roleRepository.save(new Role("ADMIN"));
        List<Role> roles = new ArrayList<>();
        roles.add(savedRole);
        Client client = new Client("123456", roles, "tester@gmail.com");
        Client savedClient = clientRepository.save(client);

        Grant grant = new Grant("grantName", "provider", "grantDesc", 0, "eligibilityCriteria", "applicationProcess", "benefits", "grantLink");
        GrantInputModel grantInputModel = new GrantInputModel(grant.getGrantName(), grant.getProvider(), grant.getGrantDesc(), grant.getValue(), grant.getEligibilityCriteria(), grant.getApplicationProcess(), grant.getBenefits(), grant.getGrantLink());
        grantRepository.save(grant);
        assertThrows(GrantException.class, () -> {
            grantService.addGrant(savedClient.getClientId().toString(), grantInputModel);
        });
    }

    @Test
    void addGrant_ClientNotFound_ThrowClientException(){
        Grant grant = new Grant("grantName", "provider", "grantDesc", 0, "eligibilityCriteria", "applicationProcess", "benefits", "grantLink");
        GrantInputModel grantInputModel = new GrantInputModel(grant.getGrantName(), grant.getProvider(), grant.getGrantDesc(), grant.getValue(), grant.getEligibilityCriteria(), grant.getApplicationProcess(), grant.getBenefits(), grant.getGrantLink());
        assertThrows(ClientException.class, () -> {
            grantService.addGrant(UUID.randomUUID().toString(), grantInputModel);
        });
    }

    @Test
    void addGrant_ClientNotAdmin_ThrowClientException(){
        Client client = new Client("123456", "tester@gmail.com");
        Client savedClient = clientRepository.save(client);

        Grant grant = new Grant("grantName", "provider", "grantDesc", 0, "eligibilityCriteria", "applicationProcess", "benefits", "grantLink");
        GrantInputModel grantInputModel = new GrantInputModel(grant.getGrantName(), grant.getProvider(), grant.getGrantDesc(), grant.getValue(), grant.getEligibilityCriteria(), grant.getApplicationProcess(), grant.getBenefits(), grant.getGrantLink());
        assertThrows(ClientException.class, () -> {
            grantService.addGrant(savedClient.getClientId().toString(), grantInputModel);
        });
    }

    @Test
    void addIndustryToGrant_SuccessfullyAddedIndustryToGrant_ReturnGrant(){
        Role savedRole = roleRepository.save(new Role("ADMIN"));
        List<Role> roles = new ArrayList<>();
        roles.add(savedRole);
        Client client = new Client("123456", roles, "tester@gmail.com");
        Client savedClient = clientRepository.save(client);
        Industry industry = new Industry("industryName", "industrySubtype", "industryDesc");
        Industry savedIndustry = industryRepository.save(industry);
        Grant grant = new Grant("grantName", "provider", "grantDesc", 0, "eligibilityCriteria", "applicationProcess", "benefits", "grantLink");
        Grant savedGrant = grantRepository.save(grant);

        Grant updatedGrant = grantService.addIndustryToGrant(savedClient.getClientId().toString(), savedIndustry.getIndustrySubtype(), savedGrant.getGrantId());
        assertNotNull(updatedGrant);
    }

    @Test
    void addIndustryToGrant_ClientNotFound_ThrowClientException(){
        Industry industry = new Industry("industryName", "industrySubtype", "industryDesc");
        Industry savedIndustry = industryRepository.save(industry);
        Grant grant = new Grant("grantName", "provider", "grantDesc", 0, "eligibilityCriteria", "applicationProcess", "benefits", "grantLink");
        Grant savedGrant = grantRepository.save(grant);

        assertThrows(ClientException.class, () -> {
            grantService.addIndustryToGrant(UUID.randomUUID().toString(), savedIndustry.getIndustrySubtype(), savedGrant.getGrantId());
        });
    }

    @Test
    void addIndustryToGrant_IndustryNotFound_ThrowIndustryException(){
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));
        Client client = new Client("123456", roles, "tester@gmail.com");
        Client savedClient = clientRepository.save(client);
        Grant grant = new Grant("grantName", "provider", "grantDesc", 0, "eligibilityCriteria", "applicationProcess", "benefits", "grantLink");
        Grant savedGrant = grantRepository.save(grant);

        assertThrows(IndustryException.class, () -> {
            grantService.addIndustryToGrant(savedClient.getClientId().toString(), "", savedGrant.getGrantId());
        });
    }

    @Test
    void addIndustryToGrant_GrantNotFound_ThrowGrantException(){
        Role savedRole = roleRepository.save(new Role("ADMIN"));
        List<Role> roles = new ArrayList<>();
        roles.add(savedRole);
        Client client = new Client("123456", roles, "tester@gmail.com");
        Client savedClient = clientRepository.save(client);
        Industry industry = new Industry("industryName", "industrySubtype", "industryDesc");
        Industry savedIndustry = industryRepository.save(industry);

        assertThrows(GrantException.class, () -> {
            grantService.addIndustryToGrant(savedClient.getClientId().toString(), savedIndustry.getIndustrySubtype(), UUID.randomUUID());
        });
    }

    @Test
    void updateGrant_SuccessfullyUpdatedGrant_ReturnUpdatedGrant(){
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));
        Client client = new Client("123456", roles, "tester@gmail.com");
        Client savedClient = clientRepository.save(client);

        Grant grant = new Grant("grantName", "provider", "grantDesc", 0, "eligibilityCriteria", "applicationProcess", "benefits", "grantLink");
        Grant savedGrant = grantRepository.save(grant);

        GrantInputModel grantInputModel = new GrantInputModel("newGrantName", "newProvider", "newGrantDesc", 1, "newEligibilityCriteria", "newApplicationProcess", "newBenefits", "newGrantLink");
        grantInputModel.setGrantId(savedGrant.getGrantId());
        Grant updatedGrant = grantService.updateGrant(savedClient.getClientId().toString(), grantInputModel);
        assertEquals(savedGrant.getGrantId(), updatedGrant.getGrantId());
        assertEquals(grantInputModel.getGrantName(), updatedGrant.getGrantName());
        assertEquals(grantInputModel.getProvider(), updatedGrant.getProvider());
        assertEquals(grantInputModel.getGrantDesc(), updatedGrant.getGrantDesc());
        assertEquals(grantInputModel.getValue(), updatedGrant.getValue());
        assertEquals(grantInputModel.getEligibilityCriteria(), updatedGrant.getEligibilityCriteria());
        assertEquals(grantInputModel.getApplicationProcess(), updatedGrant.getApplicationProcess());
        assertEquals(grantInputModel.getBenefits(), updatedGrant.getBenefits());
        assertEquals(grantInputModel.getGrantLink(), updatedGrant.getGrantLink());
    }

    @Test
    void updateGrant_GrantNotFound_ThrowGrantException(){
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));
        Client client = new Client("123456", roles, "tester@gmail.com");
        Client savedClient = clientRepository.save(client);

        GrantInputModel grantInputModel = new GrantInputModel("newGrantName", "newProvider", "newGrantDesc", 1, "newEligibilityCriteria", "newApplicationProcess", "newBenefits", "newGrantLink");
        grantInputModel.setGrantId(UUID.randomUUID());
        assertThrows(GrantException.class, () -> {
            grantService.updateGrant(savedClient.getClientId().toString(), grantInputModel);
        });
    }

    @Test
    void updateGrant_ClientNotFound_ThrowClientException(){
        Grant grant = new Grant("grantName", "provider", "grantDesc", 0, "eligibilityCriteria", "applicationProcess", "benefits", "grantLink");
        Grant savedGrant = grantRepository.save(grant);

        GrantInputModel grantInputModel = new GrantInputModel("newGrantName", "newProvider", "newGrantDesc", 1, "newEligibilityCriteria", "newApplicationProcess", "newBenefits", "newGrantLink");
        grantInputModel.setGrantId(savedGrant.getGrantId());
        assertThrows(ClientException.class, () -> {
            grantService.updateGrant(UUID.randomUUID().toString(), grantInputModel);
        });
    }

    @Test
    void updateGrant_ClientNotAdmin_ThrowClientException(){
        Client client = new Client("123456", "tester@gmail.com");
        Client savedClient = clientRepository.save(client);

        Grant grant = new Grant("grantName", "provider", "grantDesc", 0, "eligibilityCriteria", "applicationProcess", "benefits", "grantLink");
        Grant savedGrant = grantRepository.save(grant);

        GrantInputModel grantInputModel = new GrantInputModel("newGrantName", "newProvider", "newGrantDesc", 1, "newEligibilityCriteria", "newApplicationProcess", "newBenefits", "newGrantLink");
        grantInputModel.setGrantId(savedGrant.getGrantId());
        assertThrows(ClientException.class, () -> {
            grantService.updateGrant(savedClient.getClientId().toString(), grantInputModel);
        });
    }

    @Test
    void deleteGrant_SuccessfullyDeletedGrant_Return(){
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));
        Client client = new Client("123456", roles, "tester@gmail.com");
        Client savedClient = clientRepository.save(client);

        Grant grant = new Grant("grantName", "provider", "grantDesc", 0, "eligibilityCriteria", "applicationProcess", "benefits", "grantLink");
        Grant savedGrant = grantRepository.save(grant);

        grantService.deleteGrant(savedClient.getClientId().toString(), savedGrant.getGrantId());
        assertTrue(grantRepository.findById(savedGrant.getGrantId()).isEmpty());
    }

    @Test
    void deleteGrant_ClientNotFound_ThrowClientException(){
        Grant grant = new Grant("grantName", "provider", "grantDesc", 0, "eligibilityCriteria", "applicationProcess", "benefits", "grantLink");
        Grant savedGrant = grantRepository.save(grant);

        assertThrows(ClientException.class, () -> {
            grantService.deleteGrant(UUID.randomUUID().toString(), savedGrant.getGrantId());
        });
    }

    @Test
    void deleteGrant_GrantNotFound_ThrowGrantException(){
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));
        Client client = new Client("123456", roles, "tester@gmail.com");
        Client savedClient = clientRepository.save(client);

        assertThrows(GrantException.class, () -> {
            grantService.deleteGrant(savedClient.getClientId().toString(), UUID.randomUUID());
        });
    }

    @Test
    void deleteGrant_ClientNotAdmin_ThrowClientException(){
        Client client = new Client("123456", "tester@gmail.com");
        Client savedClient = clientRepository.save(client);

        Grant grant = new Grant("grantName", "provider", "grantDesc", 0, "eligibilityCriteria", "applicationProcess", "benefits", "grantLink");
        Grant savedGrant = grantRepository.save(grant);

        assertThrows(ClientException.class, () -> {
            grantService.deleteGrant(savedClient.getClientId().toString(), savedGrant.getGrantId());
        });
    }

    @Test
    void getGrant_SuccessfullyRetrievedGrant_ReturnGrant(){
        Grant grant = new Grant("grantName", "provider", "grantDesc", 0, "eligibilityCriteria", "applicationProcess", "benefits", "grantLink");
        Grant savedGrant = grantRepository.save(grant);
        Grant retrievedGrant = grantService.getGrant(savedGrant.getGrantId());
        assertNotNull(retrievedGrant);
    }

    @Test
    void getGrant_GrantNotFound_ThrowGrantException(){
        assertThrows(GrantException.class, () -> {
            grantService.getGrant(UUID.randomUUID());
        });
    }

    @Test
    void getAllGrants_SuccessfullyRetrievedGrants_ReturnGrants(){
        Grant grant = new Grant("grantName", "provider", "grantDesc", 0, "eligibilityCriteria", "applicationProcess", "benefits", "grantLink");
        Grant savedGrant = grantRepository.save(grant);
        List<Grant> returnedList = grantService.getAllGrants();
        assertTrue(returnedList.contains(savedGrant));
    }

    @Test
    void getAllGrantsByIndustry_SuccessfullyRetrievedGrantsByIndustry_ReturnGrantsByIndustry(){
        Industry industry = new Industry("industryName1", "industrySubtype1", "industryDesc1");
        Industry savedIndustry = industryRepository.save(industry);
        List<Industry> industryList = new ArrayList<>();
        industryList.add(savedIndustry);

        Grant grant1 = new Grant("grantName1", "provider1", "grantDesc1", 0, "eligibilityCriteria1", "applicationProcess1", "benefits1", "grantLink1");
        grant1.setIndustries(industryList);
        Grant grant2 = new Grant("grantName2", "provider2", "grantDesc2", 0, "eligibilityCriteria2", "applicationProcess2", "benefits2", "grantLink2");
        grant2.setIndustries(industryList);
        Grant grant3 = new Grant("grantName3", "provider3", "grantDesc3", 0, "eligibilityCriteria3", "applicationProcess3", "benefits3", "grantLink3");
        Grant savedGrant1 = grantRepository.save(grant1);
        Grant savedGrant2 = grantRepository.save(grant2);
        Grant savedGrant3 = grantRepository.save(grant3);

        List<Grant> returnedList = grantService.getAllGrantsByIndustry(savedIndustry.getIndustrySubtype());
        assertTrue(returnedList.contains(savedGrant1));
        assertTrue(returnedList.contains(savedGrant2));
        assertFalse(returnedList.contains(savedGrant3));
    }
}
