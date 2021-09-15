package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.Models.Industry;

import java.util.UUID;

public interface IIndustryService {

    Industry addIndustry(String industryName, String industrySubtype, String description);

    Industry updateIndustry(Industry industry);

    void deleteIndustry(UUID industryId);
}
