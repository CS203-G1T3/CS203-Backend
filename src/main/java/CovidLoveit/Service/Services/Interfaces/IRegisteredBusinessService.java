package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.Models.RegisteredBusiness;

public interface IRegisteredBusinessService {
    RegisteredBusiness addBusiness(String name, String desc);

    RegisteredBusiness updateBusiness(RegisteredBusiness registeredBusiness);

    void deleteBusiness(RegisteredBusiness registeredBusiness);
}
