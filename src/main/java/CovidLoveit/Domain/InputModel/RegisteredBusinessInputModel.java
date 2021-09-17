package CovidLoveit.Domain.InputModel;

import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisteredBusinessInputModel {

    public RegisteredBusinessInputModel(){}

    public RegisteredBusinessInputModel(UUID businessId, String businessName, String businessDesc, UUID industryId, UUID clientId) {
        this.businessId = businessId;
        this.businessName = businessName;
        this.businessDesc = businessDesc;
        this.industryId = industryId;
        this.clientId = clientId;
    }

    public RegisteredBusinessInputModel(String businessName, String businessDesc, UUID industry, UUID client) {
        this.businessName = businessName;
        this.businessDesc = businessDesc;
        this.industryId = industry;
        this.clientId = client;
    }

    //    @NotNull(message = "Business Id cannot be null or empty.")
    private UUID businessId;

    @NotBlank(message = "Business Name required.")
    @Size(min=3, max=256)
    private String businessName;

    @NotBlank(message = "Business Description required.")
    @Size(min=3, max=512)
    private String businessDesc;

    @NotNull(message = "Industry ID required.")
    private UUID industryId;

    @NotNull(message = "Client ID required.")
    private UUID clientId;

    
    public Set<ConstraintViolation<RegisteredBusinessInputModel>> validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(this);
    }


    public UUID getBusinessId() {
        return this.businessId;
    }
        
    public String getBusinessName() {
        return this.businessName;
    }

    public String getBusinessDesc() {
        return this.businessDesc;
    }

    public UUID getIndustryId() {
        return this.industryId;
    }

    public UUID getClientId() {
        return this.clientId;
    }


}
