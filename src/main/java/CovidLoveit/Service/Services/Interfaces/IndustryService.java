package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Domain.InputModel.IndustryInputModel;

import java.util.UUID;
import java.util.Optional;
import java.util.List;

public interface IndustryService {

    Industry addIndustry(String clientId, IndustryInputModel inputModel);

    Industry updateIndustry(String clientId, IndustryInputModel inputModel);

    void deleteIndustry(String clientId, UUID industryId);

    Optional<Industry> getIndustry(UUID industryId);

    List<String> getAllIndustrySubtypes();

    List<String> getAllIndustries();

    List<String> getIndustrySubtypesByIndustry(String industryName);
}
