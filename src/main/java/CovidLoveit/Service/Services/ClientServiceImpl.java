package CovidLoveit.Service.Services;

import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Exception.ClientNotFoundException;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import CovidLoveit.Service.Services.Interfaces.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    // Use this logger object to log information of user's actions
    private Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
    private ClientRepository clientRepository;

    // Injecting the required dependencies here
    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public Client addClient(String email, boolean isAdmin) {
        var client = new Client(email, isAdmin);
        var savedClient = clientRepository.save(client);

        logger.info(String.format("Successfully added client {%s}", savedClient.getClientId()));

        return savedClient;
    }

    @Override
    public Client updateClient(UUID clientId, Client client) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);

        clientOptional.orElseThrow(() -> {
            logger.warn(String.format("Client with ID {%s} does not exist in DB.", clientId));
            throw new ClientNotFoundException(String.format("Client with ID {%s} not found.", clientId));
        });

        logger.info(String.format("Successfully updated client with ID {%s}", clientId));
        return clientRepository.save(client);
    }

    @Override
    public void deleteClient(UUID clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);

        clientOptional.orElseThrow(() -> {
            logger.warn(String.format("Client with ID {%s} does not exist in DB.", clientId));
            throw new ClientNotFoundException(String.format("Client with ID {%s} not found.", clientId));
        });

        clientRepository.delete(clientOptional.get());
        logger.info(String.format("Successfully removed client with ID {%s}", clientId));
    }

    @Override
    public Optional<Client> getClient(UUID clientId){
        return clientRepository.findById(clientId);
    }
}
