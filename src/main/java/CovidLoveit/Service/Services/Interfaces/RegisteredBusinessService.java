package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.InputModel.RegisteredBusinessInputModel;
import CovidLoveit.Domain.Models.RegisteredBusiness;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RegisteredBusinessService {

    RegisteredBusiness addBusiness(RegisteredBusinessInputModel inputModel);

    RegisteredBusiness updateBusiness(UUID businessId, RegisteredBusinessInputModel inputModel);

    void deleteBusiness(UUID businessId);

    Optional<RegisteredBusiness> getBusiness(UUID businessId);

    List<RegisteredBusiness> getAllRegisteredBusinesses();

}
