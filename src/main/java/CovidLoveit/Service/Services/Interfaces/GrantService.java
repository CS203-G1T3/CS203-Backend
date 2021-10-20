package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.InputModel.GrantInputModel;
import CovidLoveit.Domain.Models.Grant;

import java.util.List;
import java.util.UUID;

public interface GrantService {

    Grant addGrant(String adminId, GrantInputModel inputModel);

    Grant addIndustryToGrant(String adminId, String industrySubtypeName, UUID grantId);

    Grant updateGrant(String adminId, GrantInputModel inputModel);

    void deleteGrant(String adminId, UUID grantId);

    Grant getGrant(UUID grantId);

    List<Grant> getAllGrants();

    List<Grant> getAllGrantsByIndustry(String industrySubtypeName);
}
