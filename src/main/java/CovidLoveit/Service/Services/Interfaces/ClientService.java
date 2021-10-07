package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.InputModel.ClientInputModel;
import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Role;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    Client addClient (ClientInputModel inputModel);

    Role addRole(Role role);

    Client updateClient (UUID clientId, ClientInputModel inputModel);

    void deleteClient (UUID clientId);

    void addRoleToClient(String username, String roleName);

    Client getClient(UUID clientId);

    List<Client> getAllClients();

    Client getClientByEmail(String email);

}
