package CovidLoveit.Service.Services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import CovidLoveit.Domain.InputModel.ClientInputModel;
import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.EmployeeRecord;
import CovidLoveit.Domain.Models.Guideline;
import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Domain.Models.RegisteredBusiness;
import CovidLoveit.Domain.Models.Role;
import CovidLoveit.Exception.ClientException;
import CovidLoveit.Exception.RoleException;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import CovidLoveit.Repositories.Interfaces.RoleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ClientServiceImpl.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class ClientServiceImplTest {
    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private ClientServiceImpl clientServiceImpl;

    @MockBean
    private RoleRepository roleRepository;

    @Test
    void testAddClient() {
        Industry industry = new Industry();
        industry.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry.setIndustrySubtype("Industry Subtype");
        industry.setIndustryName("Industry Name");
        industry.setIndustryDesc("Industry Desc");
        industry.setGuidelines(new ArrayList<Guideline>());

        RegisteredBusiness registeredBusiness = new RegisteredBusiness();
        registeredBusiness.setIndustry(new Industry());
        registeredBusiness.setBusinessDesc("Business Desc");
        registeredBusiness.setClient(new Client());
        registeredBusiness.setBusinessName("Business Name");
        registeredBusiness.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client = new Client();
        client.setPassword("iloveyou");
        client.setEmail("jane.doe@example.org");
        client.setRegisteredBusiness(registeredBusiness);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        client.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        client.setRoles(new ArrayList<Role>());

        RegisteredBusiness registeredBusiness1 = new RegisteredBusiness();
        registeredBusiness1.setIndustry(industry);
        registeredBusiness1.setBusinessDesc("Business Desc");
        registeredBusiness1.setClient(client);
        registeredBusiness1.setBusinessName("Business Name");
        registeredBusiness1.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client1 = new Client();
        client1.setPassword("iloveyou");
        client1.setEmail("jane.doe@example.org");
        client1.setRegisteredBusiness(registeredBusiness1);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client1.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        client1.setRoles(new ArrayList<Role>());
        Optional<Client> ofResult = Optional.<Client>of(client1);
        when(this.clientRepository.findByEmail((String) any())).thenReturn(ofResult);
        assertThrows(ClientException.class,
                () -> this.clientServiceImpl.addClient(new ClientInputModel("jane.doe@example.org", "iloveyou")));
        verify(this.clientRepository).findByEmail((String) any());
    }

    @Test
    void testAddClient2() {
        Industry industry = new Industry();
        industry.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry.setIndustrySubtype("Industry Subtype");
        industry.setIndustryName("Industry Name");
        industry.setIndustryDesc("Industry Desc");
        industry.setGuidelines(new ArrayList<Guideline>());

        Industry industry1 = new Industry();
        industry1.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry1.setIndustrySubtype("Industry Subtype");
        industry1.setIndustryName("Industry Name");
        industry1.setIndustryDesc("Industry Desc");
        industry1.setGuidelines(new ArrayList<Guideline>());

        Client client = new Client();
        client.setPassword("iloveyou");
        client.setEmail("jane.doe@example.org");
        client.setRegisteredBusiness(new RegisteredBusiness());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        client.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        client.setRoles(new ArrayList<Role>());

        RegisteredBusiness registeredBusiness = new RegisteredBusiness();
        registeredBusiness.setIndustry(industry1);
        registeredBusiness.setBusinessDesc("Business Desc");
        registeredBusiness.setClient(client);
        registeredBusiness.setBusinessName("Business Name");
        registeredBusiness.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client1 = new Client();
        client1.setPassword("iloveyou");
        client1.setEmail("jane.doe@example.org");
        client1.setRegisteredBusiness(registeredBusiness);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client1.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        client1.setRoles(new ArrayList<Role>());

        RegisteredBusiness registeredBusiness1 = new RegisteredBusiness();
        registeredBusiness1.setIndustry(industry);
        registeredBusiness1.setBusinessDesc("Business Desc");
        registeredBusiness1.setClient(client1);
        registeredBusiness1.setBusinessName("Business Name");
        registeredBusiness1.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client2 = new Client();
        client2.setPassword("iloveyou");
        client2.setEmail("jane.doe@example.org");
        client2.setRegisteredBusiness(registeredBusiness1);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client2.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        client2.setRoles(new ArrayList<Role>());
        when(this.clientRepository.save((Client) any())).thenReturn(client2);
        when(this.clientRepository.findByEmail((String) any())).thenReturn(Optional.<Client>empty());
        assertSame(client2, this.clientServiceImpl.addClient(new ClientInputModel("jane.doe@example.org", "iloveyou")));
        verify(this.clientRepository).findByEmail((String) any());
        verify(this.clientRepository).save((Client) any());
    }

    @Test
    void testAddRole() {
        Role role = new Role();
        role.setId(123L);
        role.setName("Role Name");
        Optional<Role> ofResult = Optional.<Role>of(role);
        when(this.roleRepository.findByRoleName((String) any())).thenReturn(ofResult);

        Role role1 = new Role();
        role1.setId(123L);
        role1.setName("Role Name");
        assertThrows(RoleException.class, () -> this.clientServiceImpl.addRole(role1));
        verify(this.roleRepository).findByRoleName((String) any());
    }

    @Test
    void testAddRole2() {
        Role role = new Role();
        role.setId(123L);
        role.setName("Role Name");
        when(this.roleRepository.save((Role) any())).thenReturn(role);
        when(this.roleRepository.findByRoleName((String) any())).thenReturn(Optional.<Role>empty());

        Role role1 = new Role();
        role1.setId(123L);
        role1.setName("Role Name");
        assertSame(role, this.clientServiceImpl.addRole(role1));
        verify(this.roleRepository).findByRoleName((String) any());
        verify(this.roleRepository).save((Role) any());
        assertTrue(this.clientServiceImpl.getAllClients().isEmpty());
    }

    @Test
    void testUpdateClient() {
        Industry industry = new Industry();
        industry.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry.setIndustrySubtype("Industry Subtype");
        industry.setIndustryName("Industry Name");
        industry.setIndustryDesc("Industry Desc");
        industry.setGuidelines(new ArrayList<Guideline>());

        RegisteredBusiness registeredBusiness = new RegisteredBusiness();
        registeredBusiness.setIndustry(new Industry());
        registeredBusiness.setBusinessDesc("Business Desc");
        registeredBusiness.setClient(new Client());
        registeredBusiness.setBusinessName("Business Name");
        registeredBusiness.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client = new Client();
        client.setPassword("iloveyou");
        client.setEmail("jane.doe@example.org");
        client.setRegisteredBusiness(registeredBusiness);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        client.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        client.setRoles(new ArrayList<Role>());

        RegisteredBusiness registeredBusiness1 = new RegisteredBusiness();
        registeredBusiness1.setIndustry(industry);
        registeredBusiness1.setBusinessDesc("Business Desc");
        registeredBusiness1.setClient(client);
        registeredBusiness1.setBusinessName("Business Name");
        registeredBusiness1.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client1 = new Client();
        client1.setPassword("iloveyou");
        client1.setEmail("jane.doe@example.org");
        client1.setRegisteredBusiness(registeredBusiness1);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client1.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        client1.setRoles(new ArrayList<Role>());
        Optional<Client> ofResult = Optional.<Client>of(client1);

        Industry industry1 = new Industry();
        industry1.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry1.setIndustrySubtype("Industry Subtype");
        industry1.setIndustryName("Industry Name");
        industry1.setIndustryDesc("Industry Desc");
        industry1.setGuidelines(new ArrayList<Guideline>());

        RegisteredBusiness registeredBusiness2 = new RegisteredBusiness();
        registeredBusiness2.setIndustry(new Industry());
        registeredBusiness2.setBusinessDesc("Business Desc");
        registeredBusiness2.setClient(new Client());
        registeredBusiness2.setBusinessName("Business Name");
        registeredBusiness2.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client2 = new Client();
        client2.setPassword("iloveyou");
        client2.setEmail("jane.doe@example.org");
        client2.setRegisteredBusiness(registeredBusiness2);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client2.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        client2.setRoles(new ArrayList<Role>());

        RegisteredBusiness registeredBusiness3 = new RegisteredBusiness();
        registeredBusiness3.setIndustry(industry1);
        registeredBusiness3.setBusinessDesc("Business Desc");
        registeredBusiness3.setClient(client2);
        registeredBusiness3.setBusinessName("Business Name");
        registeredBusiness3.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client3 = new Client();
        client3.setPassword("iloveyou");
        client3.setEmail("jane.doe@example.org");
        client3.setRegisteredBusiness(registeredBusiness3);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client3.setCreatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        client3.setRoles(new ArrayList<Role>());
        Optional<Client> ofResult1 = Optional.<Client>of(client3);

        Industry industry2 = new Industry();
        industry2.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry2.setIndustrySubtype("Industry Subtype");
        industry2.setIndustryName("Industry Name");
        industry2.setIndustryDesc("Industry Desc");
        industry2.setGuidelines(new ArrayList<Guideline>());

        Industry industry3 = new Industry();
        industry3.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry3.setIndustrySubtype("Industry Subtype");
        industry3.setIndustryName("Industry Name");
        industry3.setIndustryDesc("Industry Desc");
        industry3.setGuidelines(new ArrayList<Guideline>());

        Client client4 = new Client();
        client4.setPassword("iloveyou");
        client4.setEmail("jane.doe@example.org");
        client4.setRegisteredBusiness(new RegisteredBusiness());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client4.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        client4.setRoles(new ArrayList<Role>());

        RegisteredBusiness registeredBusiness4 = new RegisteredBusiness();
        registeredBusiness4.setIndustry(industry3);
        registeredBusiness4.setBusinessDesc("Business Desc");
        registeredBusiness4.setClient(client4);
        registeredBusiness4.setBusinessName("Business Name");
        registeredBusiness4.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client5 = new Client();
        client5.setPassword("iloveyou");
        client5.setEmail("jane.doe@example.org");
        client5.setRegisteredBusiness(registeredBusiness4);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client5.setCreatedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        client5.setRoles(new ArrayList<Role>());

        RegisteredBusiness registeredBusiness5 = new RegisteredBusiness();
        registeredBusiness5.setIndustry(industry2);
        registeredBusiness5.setBusinessDesc("Business Desc");
        registeredBusiness5.setClient(client5);
        registeredBusiness5.setBusinessName("Business Name");
        registeredBusiness5.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client6 = new Client();
        client6.setPassword("iloveyou");
        client6.setEmail("jane.doe@example.org");
        client6.setRegisteredBusiness(registeredBusiness5);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client6.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        client6.setRoles(new ArrayList<Role>());
        when(this.clientRepository.save((Client) any())).thenReturn(client6);
        when(this.clientRepository.findByEmail((String) any())).thenReturn(ofResult1);
        when(this.clientRepository.findById((UUID) any())).thenReturn(ofResult);
        UUID clientId = UUID.randomUUID();
        assertSame(client6,
                this.clientServiceImpl.updateClient(clientId, new ClientInputModel("jane.doe@example.org", "iloveyou")));
        verify(this.clientRepository).findByEmail((String) any());
        verify(this.clientRepository).findById((UUID) any());
        verify(this.clientRepository).save((Client) any());
    }

    @Test
    void testUpdateClient2() {
        Industry industry = new Industry();
        industry.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry.setIndustrySubtype("Industry Subtype");
        industry.setIndustryName("Industry Name");
        industry.setIndustryDesc("Industry Desc");
        industry.setGuidelines(new ArrayList<Guideline>());

        RegisteredBusiness registeredBusiness = new RegisteredBusiness();
        registeredBusiness.setIndustry(new Industry());
        registeredBusiness.setBusinessDesc("Business Desc");
        registeredBusiness.setClient(new Client());
        registeredBusiness.setBusinessName("Business Name");
        registeredBusiness.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client = new Client();
        client.setPassword("iloveyou");
        client.setEmail("jane.doe@example.org");
        client.setRegisteredBusiness(registeredBusiness);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        client.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        client.setRoles(new ArrayList<Role>());

        RegisteredBusiness registeredBusiness1 = new RegisteredBusiness();
        registeredBusiness1.setIndustry(industry);
        registeredBusiness1.setBusinessDesc("Business Desc");
        registeredBusiness1.setClient(client);
        registeredBusiness1.setBusinessName("Business Name");
        registeredBusiness1.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client1 = new Client();
        client1.setPassword("iloveyou");
        client1.setEmail("jane.doe@example.org");
        client1.setRegisteredBusiness(registeredBusiness1);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client1.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        client1.setRoles(new ArrayList<Role>());
        Optional<Client> ofResult = Optional.<Client>of(client1);

        Industry industry1 = new Industry();
        industry1.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry1.setIndustrySubtype("Industry Subtype");
        industry1.setIndustryName("Industry Name");
        industry1.setIndustryDesc("Industry Desc");
        industry1.setGuidelines(new ArrayList<Guideline>());

        RegisteredBusiness registeredBusiness2 = new RegisteredBusiness();
        registeredBusiness2.setIndustry(new Industry());
        registeredBusiness2.setBusinessDesc("Business Desc");
        registeredBusiness2.setClient(new Client());
        registeredBusiness2.setBusinessName("Business Name");
        registeredBusiness2.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client2 = new Client();
        client2.setPassword("iloveyou");
        client2.setEmail("jane.doe@example.org");
        client2.setRegisteredBusiness(registeredBusiness2);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client2.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        client2.setRoles(new ArrayList<Role>());

        RegisteredBusiness registeredBusiness3 = new RegisteredBusiness();
        registeredBusiness3.setIndustry(industry1);
        registeredBusiness3.setBusinessDesc("Business Desc");
        registeredBusiness3.setClient(client2);
        registeredBusiness3.setBusinessName("Business Name");
        registeredBusiness3.setEmployeeRecords(new ArrayList<EmployeeRecord>());
        UUID clientId = UUID.randomUUID();

        Client client3 = new Client(clientId, "iloveyou", new ArrayList<Role>(), "jane.doe@example.org");
        client3.setPassword("iloveyou");
        client3.setEmail("jane.doe@example.org");
        client3.setRegisteredBusiness(registeredBusiness3);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client3.setCreatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        client3.setRoles(new ArrayList<Role>());
        Optional<Client> ofResult1 = Optional.<Client>of(client3);

        Industry industry2 = new Industry();
        industry2.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry2.setIndustrySubtype("Industry Subtype");
        industry2.setIndustryName("Industry Name");
        industry2.setIndustryDesc("Industry Desc");
        industry2.setGuidelines(new ArrayList<Guideline>());

        Industry industry3 = new Industry();
        industry3.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry3.setIndustrySubtype("Industry Subtype");
        industry3.setIndustryName("Industry Name");
        industry3.setIndustryDesc("Industry Desc");
        industry3.setGuidelines(new ArrayList<Guideline>());

        Client client4 = new Client();
        client4.setPassword("iloveyou");
        client4.setEmail("jane.doe@example.org");
        client4.setRegisteredBusiness(new RegisteredBusiness());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client4.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        client4.setRoles(new ArrayList<Role>());

        RegisteredBusiness registeredBusiness4 = new RegisteredBusiness();
        registeredBusiness4.setIndustry(industry3);
        registeredBusiness4.setBusinessDesc("Business Desc");
        registeredBusiness4.setClient(client4);
        registeredBusiness4.setBusinessName("Business Name");
        registeredBusiness4.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client5 = new Client();
        client5.setPassword("iloveyou");
        client5.setEmail("jane.doe@example.org");
        client5.setRegisteredBusiness(registeredBusiness4);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client5.setCreatedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        client5.setRoles(new ArrayList<Role>());

        RegisteredBusiness registeredBusiness5 = new RegisteredBusiness();
        registeredBusiness5.setIndustry(industry2);
        registeredBusiness5.setBusinessDesc("Business Desc");
        registeredBusiness5.setClient(client5);
        registeredBusiness5.setBusinessName("Business Name");
        registeredBusiness5.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client6 = new Client();
        client6.setPassword("iloveyou");
        client6.setEmail("jane.doe@example.org");
        client6.setRegisteredBusiness(registeredBusiness5);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client6.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        client6.setRoles(new ArrayList<Role>());
        when(this.clientRepository.save((Client) any())).thenReturn(client6);
        when(this.clientRepository.findByEmail((String) any())).thenReturn(ofResult1);
        when(this.clientRepository.findById((UUID) any())).thenReturn(ofResult);
        UUID clientId1 = UUID.randomUUID();
        assertThrows(ClientException.class,
                () -> this.clientServiceImpl.updateClient(clientId1, new ClientInputModel("jane.doe@example.org", "iloveyou")));
        verify(this.clientRepository).findByEmail((String) any());
        verify(this.clientRepository).findById((UUID) any());
    }

    @Test
    void testDeleteClient() {
        Industry industry = new Industry();
        industry.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry.setIndustrySubtype("Industry Subtype");
        industry.setIndustryName("Industry Name");
        industry.setIndustryDesc("Industry Desc");
        industry.setGuidelines(new ArrayList<Guideline>());

        RegisteredBusiness registeredBusiness = new RegisteredBusiness();
        registeredBusiness.setIndustry(new Industry());
        registeredBusiness.setBusinessDesc("Business Desc");
        registeredBusiness.setClient(new Client());
        registeredBusiness.setBusinessName("Business Name");
        registeredBusiness.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client = new Client();
        client.setPassword("iloveyou");
        client.setEmail("jane.doe@example.org");
        client.setRegisteredBusiness(registeredBusiness);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        client.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        client.setRoles(new ArrayList<Role>());

        RegisteredBusiness registeredBusiness1 = new RegisteredBusiness();
        registeredBusiness1.setIndustry(industry);
        registeredBusiness1.setBusinessDesc("Business Desc");
        registeredBusiness1.setClient(client);
        registeredBusiness1.setBusinessName("Business Name");
        registeredBusiness1.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client1 = new Client();
        client1.setPassword("iloveyou");
        client1.setEmail("jane.doe@example.org");
        client1.setRegisteredBusiness(registeredBusiness1);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client1.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        client1.setRoles(new ArrayList<Role>());
        Optional<Client> ofResult = Optional.<Client>of(client1);
        doNothing().when(this.clientRepository).delete((Client) any());
        when(this.clientRepository.findById((UUID) any())).thenReturn(ofResult);
        this.clientServiceImpl.deleteClient(UUID.randomUUID());
        verify(this.clientRepository).delete((Client) any());
        verify(this.clientRepository).findById((UUID) any());
        assertTrue(this.clientServiceImpl.getAllClients().isEmpty());
    }

    @Test
    void testDeleteClient2() {
        doNothing().when(this.clientRepository).delete((Client) any());
        when(this.clientRepository.findById((UUID) any())).thenReturn(Optional.<Client>empty());
        assertThrows(ClientException.class, () -> this.clientServiceImpl.deleteClient(UUID.randomUUID()));
        verify(this.clientRepository).findById((UUID) any());
    }

    @Test
    void testAddRoleToClient() {
        Role role = new Role();
        role.setId(123L);
        role.setName("Role Name");
        Optional<Role> ofResult = Optional.<Role>of(role);
        when(this.roleRepository.findByRoleName((String) any())).thenReturn(ofResult);

        Industry industry = new Industry();
        industry.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry.setIndustrySubtype("Industry Subtype");
        industry.setIndustryName("Industry Name");
        industry.setIndustryDesc("Industry Desc");
        industry.setGuidelines(new ArrayList<Guideline>());

        RegisteredBusiness registeredBusiness = new RegisteredBusiness();
        registeredBusiness.setIndustry(new Industry());
        registeredBusiness.setBusinessDesc("Business Desc");
        registeredBusiness.setClient(new Client());
        registeredBusiness.setBusinessName("Business Name");
        registeredBusiness.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client = new Client();
        client.setPassword("iloveyou");
        client.setEmail("jane.doe@example.org");
        client.setRegisteredBusiness(registeredBusiness);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        client.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        client.setRoles(new ArrayList<Role>());

        RegisteredBusiness registeredBusiness1 = new RegisteredBusiness();
        registeredBusiness1.setIndustry(industry);
        registeredBusiness1.setBusinessDesc("Business Desc");
        registeredBusiness1.setClient(client);
        registeredBusiness1.setBusinessName("Business Name");
        registeredBusiness1.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client1 = new Client();
        client1.setPassword("iloveyou");
        client1.setEmail("jane.doe@example.org");
        client1.setRegisteredBusiness(registeredBusiness1);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client1.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        client1.setRoles(new ArrayList<Role>());
        Optional<Client> ofResult1 = Optional.<Client>of(client1);
        when(this.clientRepository.findByEmail((String) any())).thenReturn(ofResult1);
        this.clientServiceImpl.addRoleToClient("jane.doe@example.org", "Role Name");
        verify(this.roleRepository).findByRoleName((String) any());
        verify(this.clientRepository).findByEmail((String) any());
        assertTrue(this.clientServiceImpl.getAllClients().isEmpty());
    }

    @Test
    void testAddRoleToClient2() {
        when(this.roleRepository.findByRoleName((String) any())).thenReturn(Optional.<Role>empty());

        Industry industry = new Industry();
        industry.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry.setIndustrySubtype("Industry Subtype");
        industry.setIndustryName("Industry Name");
        industry.setIndustryDesc("Industry Desc");
        industry.setGuidelines(new ArrayList<Guideline>());

        RegisteredBusiness registeredBusiness = new RegisteredBusiness();
        registeredBusiness.setIndustry(new Industry());
        registeredBusiness.setBusinessDesc("Business Desc");
        registeredBusiness.setClient(new Client());
        registeredBusiness.setBusinessName("Business Name");
        registeredBusiness.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client = new Client();
        client.setPassword("iloveyou");
        client.setEmail("jane.doe@example.org");
        client.setRegisteredBusiness(registeredBusiness);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        client.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        client.setRoles(new ArrayList<Role>());

        RegisteredBusiness registeredBusiness1 = new RegisteredBusiness();
        registeredBusiness1.setIndustry(industry);
        registeredBusiness1.setBusinessDesc("Business Desc");
        registeredBusiness1.setClient(client);
        registeredBusiness1.setBusinessName("Business Name");
        registeredBusiness1.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client1 = new Client();
        client1.setPassword("iloveyou");
        client1.setEmail("jane.doe@example.org");
        client1.setRegisteredBusiness(registeredBusiness1);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client1.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        client1.setRoles(new ArrayList<Role>());
        Optional<Client> ofResult = Optional.<Client>of(client1);
        when(this.clientRepository.findByEmail((String) any())).thenReturn(ofResult);
        assertThrows(ClientException.class,
                () -> this.clientServiceImpl.addRoleToClient("jane.doe@example.org", "Role Name"));
        verify(this.roleRepository).findByRoleName((String) any());
        verify(this.clientRepository).findByEmail((String) any());
    }

    @Test
    void testAddRoleToClient3() {
        Role role = new Role();
        role.setId(123L);
        role.setName("Role Name");
        Optional<Role> ofResult = Optional.<Role>of(role);
        when(this.roleRepository.findByRoleName((String) any())).thenReturn(ofResult);
        when(this.clientRepository.findByEmail((String) any())).thenReturn(Optional.<Client>empty());
        assertThrows(ClientException.class,
                () -> this.clientServiceImpl.addRoleToClient("jane.doe@example.org", "Role Name"));
        verify(this.roleRepository).findByRoleName((String) any());
        verify(this.clientRepository).findByEmail((String) any());
    }

    @Test
    void testGetClient() {
        Industry industry = new Industry();
        industry.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry.setIndustrySubtype("Industry Subtype");
        industry.setIndustryName("Industry Name");
        industry.setIndustryDesc("Industry Desc");
        industry.setGuidelines(new ArrayList<Guideline>());

        RegisteredBusiness registeredBusiness = new RegisteredBusiness();
        registeredBusiness.setIndustry(new Industry());
        registeredBusiness.setBusinessDesc("Business Desc");
        registeredBusiness.setClient(new Client());
        registeredBusiness.setBusinessName("Business Name");
        registeredBusiness.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client = new Client();
        client.setPassword("iloveyou");
        client.setEmail("jane.doe@example.org");
        client.setRegisteredBusiness(registeredBusiness);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        client.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        client.setRoles(new ArrayList<Role>());

        RegisteredBusiness registeredBusiness1 = new RegisteredBusiness();
        registeredBusiness1.setIndustry(industry);
        registeredBusiness1.setBusinessDesc("Business Desc");
        registeredBusiness1.setClient(client);
        registeredBusiness1.setBusinessName("Business Name");
        registeredBusiness1.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client1 = new Client();
        client1.setPassword("iloveyou");
        client1.setEmail("jane.doe@example.org");
        client1.setRegisteredBusiness(registeredBusiness1);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client1.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        client1.setRoles(new ArrayList<Role>());
        Optional<Client> ofResult = Optional.<Client>of(client1);
        when(this.clientRepository.findById((UUID) any())).thenReturn(ofResult);
        assertSame(client1, this.clientServiceImpl.getClient(UUID.randomUUID()));
        verify(this.clientRepository).findById((UUID) any());
    }

    @Test
    void testGetClient2() {
        when(this.clientRepository.findById((UUID) any())).thenReturn(Optional.<Client>empty());
        assertThrows(ClientException.class, () -> this.clientServiceImpl.getClient(UUID.randomUUID()));
        verify(this.clientRepository).findById((UUID) any());
    }

    @Test
    void testGetAllClients() {
        ArrayList<Client> clientList = new ArrayList<Client>();
        when(this.clientRepository.findAll()).thenReturn(clientList);
        List<Client> actualAllClients = this.clientServiceImpl.getAllClients();
        assertSame(clientList, actualAllClients);
        assertTrue(actualAllClients.isEmpty());
        verify(this.clientRepository).findAll();
    }

    @Test
    void testGetClientByEmail() {
        Industry industry = new Industry();
        industry.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry.setIndustrySubtype("Industry Subtype");
        industry.setIndustryName("Industry Name");
        industry.setIndustryDesc("Industry Desc");
        industry.setGuidelines(new ArrayList<Guideline>());

        RegisteredBusiness registeredBusiness = new RegisteredBusiness();
        registeredBusiness.setIndustry(new Industry());
        registeredBusiness.setBusinessDesc("Business Desc");
        registeredBusiness.setClient(new Client());
        registeredBusiness.setBusinessName("Business Name");
        registeredBusiness.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client = new Client();
        client.setPassword("iloveyou");
        client.setEmail("jane.doe@example.org");
        client.setRegisteredBusiness(registeredBusiness);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        client.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        client.setRoles(new ArrayList<Role>());

        RegisteredBusiness registeredBusiness1 = new RegisteredBusiness();
        registeredBusiness1.setIndustry(industry);
        registeredBusiness1.setBusinessDesc("Business Desc");
        registeredBusiness1.setClient(client);
        registeredBusiness1.setBusinessName("Business Name");
        registeredBusiness1.setEmployeeRecords(new ArrayList<EmployeeRecord>());

        Client client1 = new Client();
        client1.setPassword("iloveyou");
        client1.setEmail("jane.doe@example.org");
        client1.setRegisteredBusiness(registeredBusiness1);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        client1.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        client1.setRoles(new ArrayList<Role>());
        Optional<Client> ofResult = Optional.<Client>of(client1);
        when(this.clientRepository.findByEmail((String) any())).thenReturn(ofResult);
        assertSame(client1, this.clientServiceImpl.getClientByEmail("jane.doe@example.org"));
        verify(this.clientRepository).findByEmail((String) any());
    }

    @Test
    void testGetClientByEmail2() {
        when(this.clientRepository.findByEmail((String) any())).thenReturn(Optional.<Client>empty());
        assertThrows(ClientException.class, () -> this.clientServiceImpl.getClientByEmail("jane.doe@example.org"));
        verify(this.clientRepository).findByEmail((String) any());
    }
}

