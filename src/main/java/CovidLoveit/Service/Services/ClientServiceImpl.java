package CovidLoveit.Service.Services;

import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Exception.ResourceNotFoundException;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ClientServiceImpl implements CovidLoveit.Service.Services.Interfaces.ClientService {

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
    public Client updateClient(Client client) {
        Optional<Client> clientRecord = clientRepository.findById(client.getClientId());

        if (clientRecord.isPresent()) {
            Client clientUpdate = clientRecord.get();
            clientUpdate.setEmail(client.getEmail());
            clientUpdate.setAdmin(client.isAdmin());

            clientRepository.save(clientUpdate);
            logger.info(String.format("Successfully updated client with ID {%s}", client.getClientId()));
            return clientUpdate;

        } else {
            logger.error(String.format("Client with ID {%s} does not exist in database.", client.getClientId()));
            throw new ResourceNotFoundException(String.format("Client with ID {%s} not found.", client.getClientId()));
        }
    }

    @Override
    public void deleteClient(UUID clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);

        if (clientOptional.isPresent()) {
            clientRepository.delete(clientOptional.get());
        } else {
            logger.error(String.format("Client with ID {%s} does not exist in database.", clientId));
            throw new ResourceNotFoundException(String.format("Client with ID {%s} not found.", clientId));
        }
    }
}
