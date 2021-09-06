package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.Models.Customer;

import java.util.UUID;

public interface ICustomerService {

    Customer addCustomer (String email, String companyName, String companyDesc, boolean isAdmin);

    Customer updateCustomer (Customer customer);

    void deleteCustomer (UUID customerId);
}
