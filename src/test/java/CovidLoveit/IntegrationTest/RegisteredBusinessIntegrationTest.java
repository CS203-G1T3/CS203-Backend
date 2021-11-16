package CovidLoveit.IntegrationTest;

import CovidLoveit.Domain.Models.RegisteredBusiness;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import CovidLoveit.Repositories.Interfaces.RegisteredBusinessRepository;
import CovidLoveit.Repositories.Interfaces.RoleRepository;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisteredBusinessIntegrationTest {
    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RegisteredBusinessRepository registeredBusinessRepository;

    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    void teardown() {
        clientRepository.deleteAll();
        roleRepository.deleteAll();

    }

    private final String ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWhtYXQzMjlAZ21haWwuY29tIiwicm9sZXMiOlsiQURNSU4iXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2FwaS9sb2dpbiIsImV4cCI6MTYzODE5MDMxMn0.QFi_8BJt4RHKnbp_oEeZcF2I_AJMhF9LN_Zes1Bz7os";

    @Test
    @DisplayName("Should get 200 - Return List of Registered Businesses")
    public void getRegisteredBusinesses_Success() throws Exception{
        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/registered-businesses");
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 200 - return business by Id")
    public void getRegisteredBusinessById_Success() throws Exception {
        RegisteredBusiness business = registeredBusinessRepository.save(new RegisteredBusiness("TestCafe", "Sell tests"));
        UUID businessId = business.getBusinessId();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/registered-business/" + businessId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 400 - businesses Id does not exist in DB")
    public void getRegisteredBusinessById_Fail() throws Exception {
        UUID businessId = UUID.randomUUID();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/registered-business/" + businessId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 200 - Successfully deleted Business")
    public void deleteRegisteredBusiness_Success() throws Exception {
        RegisteredBusiness business = registeredBusinessRepository.save(new RegisteredBusiness("TestCafe", "Sell tests"));
        UUID businessId = business.getBusinessId();

        HttpDelete request = new HttpDelete(baseUrl + port + "/api/v1/registered-business/" + businessId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 400 - BusinessId does not exist in DB")
    public void deleteRegisteredBusiness_Fail() throws Exception {
        UUID businessId = UUID.randomUUID();

        HttpDelete request = new HttpDelete(baseUrl + port + "/api/v1/registered-business/" + businessId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }
    }
}
