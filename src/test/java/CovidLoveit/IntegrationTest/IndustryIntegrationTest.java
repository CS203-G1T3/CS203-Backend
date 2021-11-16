package CovidLoveit.IntegrationTest;

import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Domain.Models.Role;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import CovidLoveit.Repositories.Interfaces.GuidelineRepository;
import CovidLoveit.Repositories.Interfaces.IndustryRepository;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndustryIntegrationTest {
    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    void teardown() {
        clientRepository.deleteAll();
        industryRepository.deleteAll();
        roleRepository.deleteAll();
    }

    private final String ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWhtYXQzMjlAZ21haWwuY29tIiwicm9sZXMiOlsiQURNSU4iXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2FwaS9sb2dpbiIsImV4cCI6MTYzODE5MDMxMn0.QFi_8BJt4RHKnbp_oEeZcF2I_AJMhF9LN_Zes1Bz7os";

    @Test
    @DisplayName("Should get 200 - Return List of Industry names")
    public void getIndustryNames_Success() throws Exception {
        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/industryNames");
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should get 200 - return industry subtypes")
    public void getIndustrySubtypes_Fail() throws Exception {

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/industrySubtypes");
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 200 - return Industry by Id")
    public void getIndustryById_Success() throws Exception {
        UUID industryId = industryRepository.save(new Industry("Gym", "Integration Testing on Gym", "We test Gym")).getIndustryId();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/industry/" + industryId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }


    @Test
    @DisplayName("Should return 400 - IndustryId does not exist in DB")
    public void getIndustryByIndustryId_Fail() throws Exception {
        UUID industryId = UUID.randomUUID();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/industry/" + industryId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 200 - Successfully deleted Industry")
    public void deleteIndustry_Success() throws Exception {
        Collection<Role> roleList = new ArrayList<>();
        Role role = roleRepository.save(new Role("ADMIN"));
        roleList.add(role);
        Client admin = clientRepository.save(new Client("123456",  roleList, "TEST@gmail.com"));
        UUID adminId = admin.getClientId();

        UUID industryId = industryRepository.save(new Industry("F&B", "F&B TESTER", "We test F&B")).getIndustryId();

        HttpDelete request = new HttpDelete(baseUrl + port + "/api/v1/industry/" + adminId + "/" + industryId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }

    }

    @Test
    @DisplayName("Should return 400 - Industry does not exist in DB")
    public void deleteIndustry_Fail() throws Exception {
        Collection<Role> roleList = new ArrayList<>();
        Role role = roleRepository.save(new Role("ADMIN"));
        roleList.add(role);
        Client admin = clientRepository.save(new Client("123456",  roleList, "TEST@gmail.com"));
        UUID adminId = admin.getClientId();

        UUID industryId = UUID.randomUUID();

        HttpDelete request = new HttpDelete(baseUrl + port + "/api/v1/industry/" + adminId + "/" + industryId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 400 - not admin")
    public void deleteIndustry_Fail_NotAdmin() throws Exception {
        Client client = clientRepository.save(new Client("123456", "TEST@gmail.com"));
        UUID clientId = client.getClientId();
        UUID industryId = industryRepository.save(new Industry("F&B", "TESTING F&B", "We test F&B")).getIndustryId();

        HttpDelete request = new HttpDelete(baseUrl + port + "/api/v1/industry/" + clientId + "/" + industryId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }


    }
}
