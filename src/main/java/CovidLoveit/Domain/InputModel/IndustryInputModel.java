package CovidLoveit.Domain.InputModel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

public class IndustryInputModel {
    
    public IndustryInputModel(){}

    public IndustryInputModel(UUID industryId, String industryName, String industrySubtype, String industryDesc){
        this.industryId = industryId;
        this.industryName = industryName;
        this.industrySubtype = industrySubtype;
        this.industryDesc = industryDesc;
    }

    public IndustryInputModel(String industryName, String industrySubtype, String industryDesc){
        this.industryName = industryName;
        this.industrySubtype = industrySubtype;
        this.industryDesc = industryDesc;
    }

    private UUID industryId;

    @NotBlank(message = "Industry Name required.")
    private String industryName;

    @NotBlank(message = "Industry Subtype required.")
    private String industrySubtype;

    @NotNull(message = "Industry Description required.")
    private String industryDesc;

    public Set<ConstraintViolation<IndustryInputModel>> validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(this);
    }

    public UUID getIndustryId() {
        return industryId;
    }

    public void setIndustryId(UUID industryId) {
        this.industryId = industryId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getIndustrySubtype() {
        return industrySubtype;
    }

    public void setIndustrySubtype(String industrySubtype) {
        this.industrySubtype = industrySubtype;
    }

    public String getIndustryDesc() {
        return industryDesc;
    }

    public void setIndustryDesc(String industryDesc) {
        this.industryDesc = industryDesc;
    }
}
