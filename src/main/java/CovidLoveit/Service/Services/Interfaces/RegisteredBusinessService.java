package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.Models.RegisteredBusiness;

import java.util.UUID;

public interface RegisteredBusinessService {
    RegisteredBusiness addBusiness(String name, String desc);

    RegisteredBusiness updateBusiness(UUID businessId, RegisteredBusiness registeredBusiness);

    void deleteBusiness(UUID businessId);
}
