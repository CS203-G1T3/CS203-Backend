package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.Models.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientService {

    Client addClient (String email, boolean isAdmin);

    Client updateClient (UUID clientId, Client client);

    void deleteClient (UUID clientId);

    Optional<Client> getClient(UUID clientId);

    List<Client> getAllClients();

}
