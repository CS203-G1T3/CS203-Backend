package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.Models.Client;

import java.util.UUID;

public interface IClientService {

    Client addClient (String email, boolean isAdmin);

    Client updateClient (Client client);

    void deleteClient (UUID clientId);
}
