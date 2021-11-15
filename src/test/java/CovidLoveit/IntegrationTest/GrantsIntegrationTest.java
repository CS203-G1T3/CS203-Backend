package CovidLoveit.IntegrationTest;

import CovidLoveit.Domain.Models.*;
import CovidLoveit.Repositories.Interfaces.*;
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
public class GrantsIntegrationTest {
    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    private final CloseableHttpClient httpClient = HttpClients.createDefault();


    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private GrantRepository grantRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    void teardown() {
        industryRepository.deleteAll();
        clientRepository.deleteAll();
        roleRepository.deleteAll();
    }

    private final String ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWhtYXQzMjlAZ21haWwuY29tIiwicm9sZXMiOlsiQURNSU4iXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2FwaS9sb2dpbiIsImV4cCI6MTYzODE5MDMxMn0.QFi_8BJt4RHKnbp_oEeZcF2I_AJMhF9LN_Zes1Bz7os";

    @Test
    @DisplayName("Should get 200 - Return List of Grants")
    public void getAllGrants_Success() throws Exception {
        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/grants");
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should get 200 - Return List of Grants By industry Subtype name")
    public void getAllGrantsByIndustry_Success() throws Exception {
        String industrySubtype = industryRepository.save(new Industry("Gym", "Fitness", "We test Gym")).getIndustrySubtype();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/grants/industry/" + industrySubtype);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should get 400 - Industry does not exist in DB")
    public void getAllGrantsByIndustry_Fail() throws Exception {
        String industrySubtype = "TEST";

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/grants/industry/" + industrySubtype);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should get 200 - Return grant by id")
    public void getGrantByGrantId_Success() throws Exception {
        UUID grantId = grantRepository.save(new Grant()).getGrantId();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/grant/" + grantId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should get 400 - Grant not found in DB")
    public void getGrantByGrantId_Fail() throws Exception {
        UUID grantId = UUID.randomUUID();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/grant/" + grantId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 200 - Grant Successfully Deleted")
    public void deleteGrant_Success() throws Exception {
        Collection<Role> roleList = new ArrayList<>();
        Role role = roleRepository.save(new Role("ADMIN"));
        roleList.add(role);
        Client admin = clientRepository.save(new Client("123456",  roleList, "TEST@gmail.com"));
        UUID adminId = admin.getClientId();

        UUID grantId = grantRepository.save(new Grant()).getGrantId();

        HttpDelete request = new HttpDelete(baseUrl + port + "/api/v1/grant/" + adminId + "/" + grantId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }

    }

    @Test
    @DisplayName("Should return 400 - Grant id does not exist in DB")
    public void deleteGrant_Fail() throws Exception {
        Collection<Role> roleList = new ArrayList<>();
        Role role = roleRepository.save(new Role("ADMIN"));
        roleList.add(role);
        Client admin = clientRepository.save(new Client("123456",  roleList, "TEST@gmail.com"));
        UUID adminId = admin.getClientId();

        UUID grantId = UUID.randomUUID();

        HttpDelete request = new HttpDelete(baseUrl + port + "/api/v1/grant/" + adminId + "/" + grantId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }

    }

    @Test
    @DisplayName("Should return 400 - Not admin, cannot delete")
    public void deleteGrant_Fail_NotAdmin() throws Exception {
        Client client = clientRepository.save(new Client("123456", "TEST@gmail.com"));
        UUID clientId = client.getClientId();

        UUID grantId = grantRepository.save(new Grant()).getGrantId();

        HttpDelete request = new HttpDelete(baseUrl + port + "/api/v1/grant/" + clientId + "/" + grantId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }

    }

}
