package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.Models.RegisteredBusiness;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RegisteredBusinessService {
    RegisteredBusiness addBusiness(String name, String desc, UUID industryId, UUID clientId);

    RegisteredBusiness updateBusiness(UUID businessId, String name, String description, UUID industryId, UUID clientId);

    void deleteBusiness(UUID businessId);

    Optional<RegisteredBusiness> getBusiness(UUID businessId);

    List<RegisteredBusiness> getAllRegisteredBusinesses();

}
