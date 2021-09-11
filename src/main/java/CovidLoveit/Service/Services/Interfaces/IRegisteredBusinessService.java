package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.Models.RegisteredBusiness;

import java.util.UUID;

public interface IRegisteredBusinessService {
    RegisteredBusiness addBusiness(String name, String desc);

    RegisteredBusiness updateBusiness(RegisteredBusiness registeredBusiness);

    void deleteBusiness(UUID businessId);
}
