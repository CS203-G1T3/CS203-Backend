package CovidLoveit.IntegrationTest;

import CovidLoveit.Domain.Models.*;
import CovidLoveit.Repositories.Interfaces.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
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
public class NotificationIntegrationTest {
    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    void teardown() {
        clientRepository.deleteAll();
        roleRepository.deleteAll();
        notificationRepository.deleteAll();
    }

    private final String ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWhtYXQzMjlAZ21haWwuY29tIiwicm9sZXMiOlsiQURNSU4iXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2FwaS9sb2dpbiIsImV4cCI6MTYzODE5MDMxMn0.QFi_8BJt4RHKnbp_oEeZcF2I_AJMhF9LN_Zes1Bz7os";

    @Test
    @DisplayName("Should return 200 - Return Unacked notifications by client id")
    public void getUnAckNotificationsByClientId_Success() throws Exception {
        UUID clientId = clientRepository.save(new Client("123456", "TEST@gmail.com")).getClientId();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/unack-notification/" + clientId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 400 - Client Id does not exist in DB")
    public void getUnAckNotificationsByClientId_Fail() throws Exception {
        UUID clientId = UUID.randomUUID();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/unack-notification/" + clientId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 200 - Return All notifications by client id")
    public void getAllNotificationsByClientId_Success() throws Exception {
        UUID clientId = clientRepository.save(new Client("123456", "TEST@gmail.com")).getClientId();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/notifications/" + clientId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 400 - Client Id does not exist in DB")
    public void getAllNotificationsByClientId_Fail() throws Exception {
        UUID clientId = UUID.randomUUID();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/notifications/" + clientId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 200 - return notification by notification id")
    public void getNotification_Success() throws Exception {
        Client client = clientRepository.save(new Client("123456", "TEST@gmail.com"));
        UUID notificationId = notificationRepository.save(new Notification("msg", false, client)).getNotifId();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/notification/" + notificationId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 400 - Notification Id not found in DB")
    public void getNotification_Fail() throws Exception {
        UUID notificationId = UUID.randomUUID();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/notification/" + notificationId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 200 - notification deleted successfully")
    public void deleteNotification_Success() throws Exception {
        Collection<Role> roleList = new ArrayList<>();
        Role role = roleRepository.save(new Role("ADMIN"));
        roleList.add(role);
        Client admin = clientRepository.save(new Client("123456",  roleList, "TEST@gmail.com"));
        UUID adminId = admin.getClientId();

        UUID notificationId = notificationRepository.save(new Notification("msg", false, admin)).getNotifId();

        HttpDelete request = new HttpDelete(baseUrl + port + "/api/v1/notification/" + adminId + "/" + notificationId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }

    }

    @Test
    @DisplayName("Should return 400 - notification id does not exist in DB")
    public void deleteNotification_Fail() throws Exception {
        Collection<Role> roleList = new ArrayList<>();
        Role role = roleRepository.save(new Role("ADMIN"));
        roleList.add(role);
        Client admin = clientRepository.save(new Client("123456",  roleList, "TEST@gmail.com"));
        UUID adminId = admin.getClientId();

        UUID notificationId = UUID.randomUUID();

        HttpDelete request = new HttpDelete(baseUrl + port + "/api/v1/notification/" + adminId + "/" + notificationId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }

    }

}
