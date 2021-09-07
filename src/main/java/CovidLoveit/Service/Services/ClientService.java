package CovidLoveit.Service.Services;

import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Exception.ResourceNotFoundException;
import CovidLoveit.Repositories.CustomerRepository;
import CovidLoveit.Service.Services.Interfaces.ICustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CustomerService implements ICustomerService {

    // Use this logger object to log information of user's actions
    private Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private CustomerRepository customerRepository;

    // Injecting the required dependencies here
    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    public Client addCustomer(String email, String companyName, String companyDesc, boolean isAdmin) {
        var customer = new Client(email, companyName, companyDesc, isAdmin);
        var savedCustomer = customerRepository.save(customer);

        logger.info(String.format("Successfully added user %s", savedCustomer.getUserId()));

        return savedCustomer;
    }

    @Override
    public Client updateCustomer(Client client) {
        Optional<Client> customerRecord = customerRepository.findById(client.getUserId());

        if (customerRecord.isPresent()) {
            Client clientUpdate = customerRecord.get();
            clientUpdate.setEmail(client.getEmail());
            clientUpdate.setCompanyName(client.getCompanyName());
            clientUpdate.setCompanyDescription(client.getCompanyDescription());
            clientUpdate.setAdmin(client.isAdmin());

            customerRepository.save(clientUpdate);
            return clientUpdate;

        } else {
            logger.error(String.format("Customer with ID %s does not exist in database.", client.getUserId()));
            throw new ResourceNotFoundException(String.format("Customer with ID %s not found.", client.getUserId()));
        }
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        Optional<Client> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            customerRepository.delete(customerOptional.get());
        } else {
            logger.error(String.format("Customer with ID %s does not exist in database.", customerId));
            throw new ResourceNotFoundException(String.format("Customer with ID %s not found.", customerId));
        }
    }
}
