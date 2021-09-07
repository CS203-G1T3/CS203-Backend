package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.Models.Client;

import java.util.UUID;

public interface ICustomerService {

    Client addCustomer (String email, String companyName, String companyDesc, boolean isAdmin);

    Client updateCustomer (Client client);

    void deleteCustomer (UUID customerId);
}
