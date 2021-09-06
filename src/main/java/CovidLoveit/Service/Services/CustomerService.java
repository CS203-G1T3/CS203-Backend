package CovidLoveit.Service.Services;

import CovidLoveit.Domain.Models.Customer;
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

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer addCustomer(String email, String companyName, String companyDesc, boolean isAdmin) {
        var customer = new Customer(email, companyName, companyDesc, isAdmin);
        var savedCustomer = customerRepository.save(customer);

        logger.info(String.format("Successfully added user %s", savedCustomer.getUserId()));

        return savedCustomer;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Optional<Customer> customerRecord = customerRepository.findById(customer.getUserId());

        if (customerRecord.isPresent()) {
            Customer customerUpdate = customerRecord.get();
            customerUpdate.setEmail(customer.getEmail());
            customerUpdate.setCompanyName(customer.getCompanyName());
            customerUpdate.setCompanyDescription(customer.getCompanyDescription());
            customerUpdate.setAdmin(customer.isAdmin());

            customerRepository.save(customerUpdate);
            return customerUpdate;

        } else {
            logger.error(String.format("Customer with ID %s does not exist in database.", customer.getUserId()));
            throw new ResourceNotFoundException(String.format("Customer with ID %s not found.", customer.getUserId()));
        }
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            customerRepository.delete(customerOptional.get());
        } else {
            logger.error(String.format("Customer with ID %s does not exist in database.", customerId));
            throw new ResourceNotFoundException(String.format("Customer with ID %s not found.", customerId));
        }
    }
}
