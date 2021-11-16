package CovidLoveit.IntegrationTest;

import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Guideline;
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
public class GuidelineIntegrationTest {

    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GuidelineRepository guidelineRepository;

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    void teardown() {
        clientRepository.deleteAll();
        guidelineRepository.deleteAll();
        roleRepository.deleteAll();
    }

    private final String ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWhtYXQzMjlAZ21haWwuY29tIiwicm9sZXMiOlsiQURNSU4iXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2FwaS9sb2dpbiIsImV4cCI6MTYzODE5MDMxMn0.QFi_8BJt4RHKnbp_oEeZcF2I_AJMhF9LN_Zes1Bz7os";

    @Test
    @DisplayName("Should get 200 - Return List of Guidelines")
    public void getGuidelines_Success() throws Exception{
        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/guidelines");
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 200 - return guideline")
    public void getGuidelineByGuidelineId_Success() throws Exception {
        UUID guidelineId = guidelineRepository.save(new Guideline()).getGuidelineId();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/guideline/" + guidelineId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 400 - guideline Id not found in DB")
    public void getGuidelineByGuidelineId_Fail() throws Exception {
        UUID guidelineId = UUID.randomUUID();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/guideline/" + guidelineId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 200 - return Guideline")
    public void getGuidelineByIndustryId_Success() throws Exception {
        UUID industryId = industryRepository.save(new Industry("F&B", "Test Subtype", "We sell hawker food")).getIndustryId();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/industry/" + industryId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 400 - IndustryId not found")
    public void getGuidelineByIndustryId_Fail() throws Exception {
        UUID industryId = UUID.randomUUID();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/industry/" + industryId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 200 - Successfully deleted guideline")
    public void deleteGuideline_Success() throws Exception {
        Collection<Role> roleList = new ArrayList<>();
        Role role = roleRepository.save(new Role("ADMIN"));
        roleList.add(role);
        Client admin = clientRepository.save(new Client("123456",  roleList, "TEST@gmail.com"));
        UUID adminId = admin.getClientId();

        UUID guidelineId = guidelineRepository.save(new Guideline()).getGuidelineId();

        HttpDelete request = new HttpDelete(baseUrl + port + "/api/v1/guideline/" + adminId + "/" + guidelineId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }

    }

    @Test
    @DisplayName("Should return 400 - not admin")
    public void deleteGuideline_Fail_NotAdmin() throws Exception {
        UUID clientId = clientRepository.save(new Client("123456", "TEST@gmail.com")).getClientId();
        UUID guidelineId = guidelineRepository.save(new Guideline()).getGuidelineId();

        HttpDelete request = new HttpDelete(baseUrl + port + "/api/v1/guideline/" + clientId + "/" + guidelineId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 400 - guidelineId not found in DB")
    public void deleteGuideline_Fail() throws Exception {
        Collection<Role> roleList = new ArrayList<>();
        Role role = roleRepository.save(new Role("ADMIN"));
        roleList.add(role);
        Client admin = clientRepository.save(new Client("123456",  roleList, "TEST@gmail.com"));
        UUID adminId = admin.getClientId();

        UUID guidelineId = UUID.randomUUID();

        HttpDelete request = new HttpDelete(baseUrl + port + "/api/v1/guideline/" + adminId + "/" + guidelineId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }
    }

}
