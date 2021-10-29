package CovidLoveit.Domain.InputModel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class GrantInputModel {

    private UUID grantId;

    @NotBlank(message = "Grant Name is required.")
    private String grantName;

    @NotBlank(message = "Provider is required.")
    private String provider;

    @NotBlank(message = "Grant Description is required.")
    private String grantDesc;

    @NotNull(message = "Value is required.")
    private int value;

    @NotBlank(message = "Eligibility Criteria is required.")
    private String eligibilityCriteria;

    private String applicationProcess;

    private String benefits;

    @NotBlank(message = "Grant Link required")
    private String grantLink;

    private List<String> industrySubtypeNames;

    public GrantInputModel() {
    }

    public GrantInputModel(String grantName, String provider, String grantDesc, int value, String eligibilityCriteria,
                           String applicationProcess, String benefits, String grantLink) {
        this.grantName = grantName;
        this.provider = provider;
        this.grantDesc = grantDesc;
        this.value = value;
        this.eligibilityCriteria = eligibilityCriteria;
        this.applicationProcess = applicationProcess;
        this.benefits = benefits;
        this.grantLink = grantLink;
    }

    public Set<ConstraintViolation<GrantInputModel>> validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(this);
    }

    public UUID getGrantId() {
        return grantId;
    }

    public void setGrantId(UUID grantId) {
        this.grantId = grantId;
    }

    public String getGrantName() {
        return grantName;
    }

    public void setGrantName(String grantName) {
        this.grantName = grantName;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getGrantDesc() {
        return grantDesc;
    }

    public void setGrantDesc(String grantDesc) {
        this.grantDesc = grantDesc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    public void setEligibilityCriteria(String eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }

    public String getApplicationProcess() {
        return applicationProcess;
    }

    public void setApplicationProcess(String applicationProcess) {
        this.applicationProcess = applicationProcess;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getGrantLink() {
        return grantLink;
    }

    public void setGrantLink(String grantLink) {
        this.grantLink = grantLink;
    }

    public List<String> getIndustrySubtypeNames() {
        return industrySubtypeNames;
    }

    public void setIndustrySubtypeNames(List<String> industrySubtypeNames) {
        this.industrySubtypeNames = industrySubtypeNames;
    }
}
