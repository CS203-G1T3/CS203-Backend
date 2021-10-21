package CovidLoveit.Service.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import CovidLoveit.Domain.InputModel.RegisteredBusinessInputModel;
import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Domain.Models.RegisteredBusiness;
import CovidLoveit.Exception.ClientException;
import CovidLoveit.Exception.IndustryException;
import CovidLoveit.Exception.RegisteredBusinessException;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import CovidLoveit.Repositories.Interfaces.IndustryRepository;
import CovidLoveit.Repositories.Interfaces.RegisteredBusinessRepository;

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

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class RegisteredBusinessServiceTest {
    @Autowired
    private RegisteredBusinessRepository businessRepository;
    
    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Mock
    private AutoCloseable autoCloseable;

    @InjectMocks
    private RegisteredBusinessServiceImpl businessService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        businessService = new RegisteredBusinessServiceImpl(businessRepository, clientRepository, industryRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void addBusiness_SuccessfullyAddedBusiness_ReturnBusiness(){
        Client client = new Client("123456", "tester@gmail.com");
        Client savedClient = clientRepository.save(client);
        Industry industry = new Industry("industryName", "industrySubtype", "industryDesc");
        Industry savedIndustry = industryRepository.save(industry);

        RegisteredBusiness business = new RegisteredBusiness("BusinessName", "BusinessDesc");
        RegisteredBusinessInputModel businessInputModel = new RegisteredBusinessInputModel(business.getBusinessName(), business.getBusinessDesc(), savedIndustry.getIndustryId(), savedClient.getClientId());
        RegisteredBusiness savedBusiness = businessService.addBusiness(businessInputModel);
        assertNotNull(savedBusiness);
    }

    @Test
    void addBusiness_ClientNotFound_ThrowClientException(){
        Industry industry = new Industry("industryName", "industrySubtype", "industryDesc");
        Industry savedIndustry = industryRepository.save(industry);
        
        RegisteredBusiness business = new RegisteredBusiness("BusinessName", "BusinessDesc");
        RegisteredBusinessInputModel businessInputModel = new RegisteredBusinessInputModel(business.getBusinessName(), business.getBusinessDesc(), savedIndustry.getIndustryId(), UUID.randomUUID());
        assertThrows(ClientException.class, () -> {
        businessService.addBusiness(businessInputModel);
        });
    }

    @Test
    void addBusiness_ClientAlreadyHasBusiness_ThrowClientException(){
        Client client = new Client("123456", "tester@gmail.com");
        client.setRegisteredBusiness(new RegisteredBusiness());
        Client savedClient = clientRepository.save(client);
        Industry industry = new Industry("industryName", "industrySubtype", "industryDesc");
        Industry savedIndustry = industryRepository.save(industry);

        RegisteredBusiness business = new RegisteredBusiness("BusinessName", "BusinessDesc");
        RegisteredBusinessInputModel businessInputModel = new RegisteredBusinessInputModel(business.getBusinessName(), business.getBusinessDesc(), savedIndustry.getIndustryId(), savedClient.getClientId());
        assertThrows(ClientException.class, () -> {
        businessService.addBusiness(businessInputModel);
        });
    }

    @Test
    void addBusiness_IndustryNotFound_ThrowIndustryException(){
        Client client = new Client("123456", "tester@gmail.com");
        Client savedClient = clientRepository.save(client);

        RegisteredBusiness business = new RegisteredBusiness("BusinessName", "BusinessDesc");
        RegisteredBusinessInputModel businessInputModel = new RegisteredBusinessInputModel(business.getBusinessName(), business.getBusinessDesc(), UUID.randomUUID(), savedClient.getClientId());
        assertThrows(IndustryException.class, () -> {
            businessService.addBusiness(businessInputModel);
            });
    }

    @Test
    void updateBusiness_SuccessfullyUpdatedBusiness_ReturnUpdatedBusiness(){
        Client client = new Client("123456", "tester@gmail.com");
        Client savedClient = clientRepository.save(client);
        Industry industry = new Industry("industryName", "industrySubtype", "industryDesc");
        Industry savedIndustry = industryRepository.save(industry);
        RegisteredBusiness business = new RegisteredBusiness("BusinessName", "BusinessDesc");
        RegisteredBusiness savedBusiness = businessRepository.save(business);

        RegisteredBusinessInputModel businessInputModel = new RegisteredBusinessInputModel("newName", "newDesc", savedIndustry.getIndustryId(), savedClient.getClientId());
        RegisteredBusiness updatedBusiness = businessService.updateBusiness(savedBusiness.getBusinessId(), businessInputModel);
        assertEquals(savedBusiness.getBusinessId(), updatedBusiness.getBusinessId());
        assertEquals(businessInputModel.getBusinessName(), updatedBusiness.getBusinessName());
        assertEquals(businessInputModel.getBusinessDesc(), updatedBusiness.getBusinessDesc());
    }

    @Test
    void updateBusiness_BusinessNotFound_ThrowBusinessException(){
        Client client = new Client("123456", "tester@gmail.com");
        Client savedClient = clientRepository.save(client);
        Industry industry = new Industry("industryName", "industrySubtype", "industryDesc");
        Industry savedIndustry = industryRepository.save(industry);

        RegisteredBusinessInputModel businessInputModel = new RegisteredBusinessInputModel("newName", "newDesc", savedIndustry.getIndustryId(), savedClient.getClientId());
        assertThrows(RegisteredBusinessException.class, () -> {
            businessService.updateBusiness(UUID.randomUUID(), businessInputModel);
            });
    }

    @Test
    void updateBusiness_ClientNotFound_ThrowClientException(){
        Industry industry = new Industry("industryName", "industrySubtype", "industryDesc");
        Industry savedIndustry = industryRepository.save(industry);
        
        RegisteredBusiness business = new RegisteredBusiness("BusinessName", "BusinessDesc");
        RegisteredBusiness savedBusiness = businessRepository.save(business);
        RegisteredBusinessInputModel businessInputModel = new RegisteredBusinessInputModel("newName", "newDesc", savedIndustry.getIndustryId(), UUID.randomUUID());
        assertThrows(ClientException.class, () -> {
        businessService.updateBusiness(savedBusiness.getBusinessId(), businessInputModel);
        });
    }

    @Test
    void updateBusiness_IndustryNotFound_ThrowIndustryException(){
        Client client = new Client("123456", "tester@gmail.com");
        Client savedClient = clientRepository.save(client);
        
        RegisteredBusiness business = new RegisteredBusiness("BusinessName", "BusinessDesc");
        RegisteredBusiness savedBusiness = businessRepository.save(business);
        RegisteredBusinessInputModel businessInputModel = new RegisteredBusinessInputModel("newName", "newDesc", UUID.randomUUID(), savedClient.getClientId());
        assertThrows(IndustryException.class, () -> {
        businessService.updateBusiness(savedBusiness.getBusinessId(), businessInputModel);
        });
    }

    @Test
    void deleteBusiness_SuccessfullyDeletedBusiness_Return(){
        RegisteredBusiness business = new RegisteredBusiness("BusinessName", "BusinessDesc");
        RegisteredBusiness savedBusiness = businessRepository.save(business);
        businessService.deleteBusiness(savedBusiness.getBusinessId());
        assertTrue(businessRepository.findById(savedBusiness.getBusinessId()).isEmpty());
    }

    @Test
    void deleteBusiness_BusinessNotFound_ThrowBusinessException(){
        assertThrows(RegisteredBusinessException.class, () -> {
            businessService.deleteBusiness(UUID.randomUUID());
        });
    }

    @Test
    void getBusiness_SuccessfullyRetrievedBusiness_ReturnBusiness(){
        RegisteredBusiness business = new RegisteredBusiness("BusinessName", "BusinessDesc");
        RegisteredBusiness savedBusiness = businessRepository.save(business);
        Optional<RegisteredBusiness> toReturn = businessService.getBusiness(savedBusiness.getBusinessId());
        assertNotNull(toReturn);
    }

    @Test
    void getAllRegisteredBusinesses_SuccessfullyRetrievedBusinesses_ReturnBusinesses(){
        RegisteredBusiness business = new RegisteredBusiness("BusinessName", "BusinessDesc");
        RegisteredBusiness savedBusiness = businessRepository.save(business);

        List<RegisteredBusiness> returnedList = businessService.getAllRegisteredBusinesses();
        assertNotNull(returnedList);
        assertTrue(returnedList.contains(savedBusiness));
    }
}
