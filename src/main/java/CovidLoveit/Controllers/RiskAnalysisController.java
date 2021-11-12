package CovidLoveit.Controllers;

import CovidLoveit.Domain.Constants.RiskAssessmentConstants;
import CovidLoveit.Service.Services.Interfaces.IndustryService;
import CovidLoveit.Service.Services.Interfaces.RegisteredBusinessService;
import CovidLoveit.Service.Services.Interfaces.RiskAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class RiskAnalysisController {

    private final RiskAnalysisService riskAnalysisService;
    private final RegisteredBusinessService registeredBusinessService;

    @Autowired
    public RiskAnalysisController(RiskAnalysisService riskAnalysisService, RegisteredBusinessService registeredBusinessService) {
        this.riskAnalysisService = riskAnalysisService;
        this.registeredBusinessService = registeredBusinessService;
    }

    @GetMapping("/risk-analysis-summary/{businessId}")
    public ResponseEntity<?> getRiskScore(@PathVariable String businessId) {
        var business = registeredBusinessService.getBusiness(UUID.fromString(businessId));
        var industry = business.get().getIndustry();

        int vaccinationRisk = riskAnalysisService.getVaccinationRateRiskByBusinessId(UUID.fromString(businessId));
        int covidTestingRisk = riskAnalysisService.getCovidTestingRiskByBusinessId(UUID.fromString(businessId));
        int averageAgeRisk = riskAnalysisService.getAverageAgeRiskByBusinessId(UUID.fromString(businessId));

        int industryRiskConstant = 0;
        switch(industry.getIndustryName()) {
            case "Food & Beverage":
                industryRiskConstant = RiskAssessmentConstants.FOOD_AND_BEVERAGE;
                break;

            case "Entertainment":
                industryRiskConstant = RiskAssessmentConstants.ENTERTAINMENT;
                break;

            case "Office":
                industryRiskConstant = RiskAssessmentConstants.OFFICE;
                break;

            case "Health Sector":
                industryRiskConstant = RiskAssessmentConstants.HEALTH_SECTOR;
                break;
        }

        Map<String, Integer> result = new HashMap<String, Integer>();
        result.put("IndustryExposure", industryRiskConstant);
        result.put("VaccinationRisk", vaccinationRisk);
        result.put("CovidTestingRisk", covidTestingRisk);
        result.put("AverageAgeRisk", averageAgeRisk);

        return ResponseEntity.ok(result);
    }
}
