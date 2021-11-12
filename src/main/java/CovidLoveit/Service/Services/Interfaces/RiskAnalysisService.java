package CovidLoveit.Service.Services.Interfaces;

import java.util.UUID;

public interface RiskAnalysisService {

    int getVaccinationRateRiskByBusinessId(UUID businessId);

    int getCovidTestingRiskByBusinessId(UUID businessId);

    int getAverageAgeRiskByBusinessId(UUID businessId);
}
