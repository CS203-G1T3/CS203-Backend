package CovidLoveit.IntegrationTest;

import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Role;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import CovidLoveit.Repositories.Interfaces.RoleRepository;
import CovidLoveit.Utils.Security.SecurityConfiguration;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.h2.util.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = {"security.basic.enabled=false", "management.security.enabled=false"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientIntegrationTest {

    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired private TestRestTemplate template;

    @Autowired
    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;
//
//    @Autowired
//    private WebApplicationContext wac;

//    @Before
//    public void setup() {
//        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac)
//                .addFilter(springSecurityFilterChain).build();
//    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .defaultRequest(get("/").with(user("tester").roles("ADMIN")))
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    void tearDown() {
        clientRepository.deleteAll();
        roleRepository.deleteAll();
    }



    @Test
    @WithMockUser(roles="ADMIN")
    public void getBook_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/clients");
        Client savedClient = clientRepository.save(new Client("123456", "testEmail@gmail.com"));

        mvc.perform(get("/").with(httpBasic("testEmail@gmail.com","123456")));
        mvc.perform(formLogin("/api").user("admin").password("pass"));

//        ObjectNode loginRequest = mapper.createObjectNode();
//        loginRequest.put("email", savedClient.getEmail());
//        loginRequest.put("password",savedClient.getPassword());
//        JsonNode loginResponse = template.postForObject(baseUrl + port + "/api", loginRequest.toString(), JsonNode.class);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
////        headers.add("X-Authorization", "Bearer " + loginResponse.get("token").textValue());
//        headers.add("Content-Type", "application/json");
//        HttpEntity<Client> entity =  new HttpEntity<>(null, headers);
//
//        HttpEntity request = restTemplate.getForEntity(uri, Client.class);
//        ResponseEntity response = template.exchange("/get",
//                HttpMethod.GET,
//                request,
//                new ParameterizedTypeReference<List<Client>>() {});


        ResponseEntity<Client> result = restTemplate.getForEntity(uri, Client.class);
        List<Client> clients = clientRepository.findAll();

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(1, clients.size());
    }


}
