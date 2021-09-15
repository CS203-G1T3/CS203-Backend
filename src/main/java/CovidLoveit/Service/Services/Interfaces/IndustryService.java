package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.Models.Industry;

import java.util.UUID;

public interface IndustryService {

    Industry addIndustry(String industryName, String industrySubtype, String description);

    Industry updateIndustry(UUID industryId, Industry industry);

    void deleteIndustry(UUID industryId);
}
