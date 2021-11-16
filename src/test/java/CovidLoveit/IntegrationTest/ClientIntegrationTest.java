package CovidLoveit.IntegrationTest;


import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import CovidLoveit.Repositories.Interfaces.RoleRepository;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientIntegrationTest {

    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Autowired
    private ClientRepository clientRepository;

    @AfterEach
    void teardown() {
        clientRepository.deleteAll();
    }

    private final String ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWhtYXQzMjlAZ21haWwuY29tIiwicm9sZXMiOlsiQURNSU4iXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2FwaS9sb2dpbiIsImV4cCI6MTYzODE5MDMxMn0.QFi_8BJt4RHKnbp_oEeZcF2I_AJMhF9LN_Zes1Bz7os";

    @Test
    @DisplayName("Should return 200 - a list of clients")
    public void getClients_Success() throws Exception {

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/clients");
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }

    }

    @Test
    @DisplayName("Should return 200 - return Client")
    public void getClientById_Success() throws Exception {
        Client client = new Client("123456", "TEST@gmail.com");
        clientRepository.save(client);

        Optional<Client> savedClient = clientRepository.findByEmail(client.getEmail());
        UUID clientId = savedClient.get().getClientId();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/client/" + clientId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 400 bad request - client ID not found in DB")
    public void getClientById_Fail() throws Exception {
        UUID clientId = UUID.randomUUID();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/client/" + clientId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 200 - return Client")
    public void getClientByEmail_Success() throws Exception {
        Client client = new Client("123456", "TEST@gmail.com");
        clientRepository.save(client);

        Optional<Client> savedClient = clientRepository.findById(client.getClientId());
        String email = savedClient.get().getEmail();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/client/email/" + email);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 400 - email not found in DB")
    public void getClientByEmail_Fail() throws Exception {
        String email = "TEST@gmail.com";

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/client/email/" + email);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 200 - Successfully Deleted")
    public void deleteClient_Success() throws Exception {
        Client client = new Client("123456", "TEST@gmail.com");
        clientRepository.save(client);

        Optional<Client> savedClient = clientRepository.findByEmail(client.getEmail());
        UUID clientId = savedClient.get().getClientId();

        HttpDelete request = new HttpDelete(baseUrl + port + "/api/v1/client/" + clientId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 400 - client ID not found in DB")
    public void deleteClient_Fail() throws Exception {
        UUID clientId = UUID.randomUUID();

        HttpDelete request = new HttpDelete(baseUrl + port + "/api/v1/client/" + clientId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }
    }


    @Test
    @DisplayName("Should return 400 - role not found in DB")
    public void addRoleToClient_Fail() throws Exception {
        Client savedClient = clientRepository.save(new Client("123456", "TEST@gmail.com"));

        String roleName = "TESTROLE";

        HttpPut request = new HttpPut(baseUrl + port + "/api/v1/role/" + roleName + "/" + savedClient.getEmail());
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }

    }


}
