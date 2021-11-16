package CovidLoveit.IntegrationTest;

import CovidLoveit.Domain.Models.EmployeeRecord;
import CovidLoveit.Domain.Models.RegisteredBusiness;
import CovidLoveit.Repositories.Interfaces.EmployeeRecordRepository;
import CovidLoveit.Repositories.Interfaces.RegisteredBusinessRepository;
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
public class EmployeeRecordIntegrationTest {
    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Autowired
    private RegisteredBusinessRepository registeredBusinessRepository;

    @Autowired
    private EmployeeRecordRepository employeeRecordRepository;

    @AfterEach
    void teardown() {
        registeredBusinessRepository.deleteAll();
        employeeRecordRepository.deleteAll();
    }

    private final String ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWhtYXQzMjlAZ21haWwuY29tIiwicm9sZXMiOlsiQURNSU4iXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2FwaS9sb2dpbiIsImV4cCI6MTYzODE5MDMxMn0.QFi_8BJt4RHKnbp_oEeZcF2I_AJMhF9LN_Zes1Bz7os";

    @Test
    @DisplayName("Should get 200 - Return List of Employee names from the business")
    public void getEmployeeNamesByBusinessId_Success() throws Exception {
        RegisteredBusiness business = registeredBusinessRepository.save(new RegisteredBusiness("TestCafe", "Sell tests"));
        UUID businessId = business.getBusinessId();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/employeeRecords/" + businessId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 400 - business Id not found")
    public void getEmployeeNamesByBusinessId_Fail() throws Exception {
        UUID businessId = UUID.randomUUID();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/employeeRecords/" + businessId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 200 - Return employee by Id")
    public void getEmployeeNameByEmployeeId_Success() throws Exception {
        EmployeeRecord employeeRecord = employeeRecordRepository.save(new EmployeeRecord());
        UUID employeeId = employeeRecord.getEmployeeId();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/employeeRecord/" + employeeId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 400 - Employee Id not found in DB")
    public void getEmployeeNamesByEmployeeId_Fail() throws Exception {
        UUID employeeId = UUID.randomUUID();

        HttpGet request = new HttpGet(baseUrl + port + "/api/v1/employeeRecord/" + employeeId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    @DisplayName("Should return 200 - Successfully deleted Employee")
    public void DeleteEmployeeByEmployeeId_Success() throws Exception {
        EmployeeRecord employeeRecord = employeeRecordRepository.save(new EmployeeRecord());
        UUID employeeId = employeeRecord.getEmployeeId();

        HttpDelete request = new HttpDelete(baseUrl + port + "/api/v1/employeeRecord/" + employeeId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }


    @Test
    @DisplayName("Should return 400 - Employee Id not found in DB")
    public void DeleteEmployeeByEmployeeId_Fail() throws Exception {
        UUID employeeId = UUID.randomUUID();

        HttpDelete request = new HttpDelete(baseUrl + port + "/api/v1/employeeRecord/" + employeeId);
        request.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request.addHeader("Content-Type", "application/json");

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(400, response.getStatusLine().getStatusCode());
        }
    }


}
