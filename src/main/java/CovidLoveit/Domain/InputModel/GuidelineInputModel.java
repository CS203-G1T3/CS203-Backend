package CovidLoveit.Domain.InputModel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

public class GuidelineInputModel {

    public GuidelineInputModel(){}

    public GuidelineInputModel(boolean canOpOnSite, 
                               String canOpOnSiteDetails,
                               int groupSize, 
                               String groupSizeDetails, 
                               int covidTestingVaccinated, 
                               int covidTestingUnvaccinated,
                               String covidTestingDetails, 
                               String contactTracing, 
                               String contactTracingDetails,
                               int opCapacity, 
                               String opCapacityDetails, 
                               String opGuidelines,
                               String referenceLink, 
                               UUID industryId) {
        this.canOpOnSite = canOpOnSite;
        this.canOpOnSiteDetails = canOpOnSiteDetails;
        this.groupSize = groupSize;
        this.groupSizeDetails = groupSizeDetails;
        this.covidTestingVaccinated = covidTestingVaccinated;
        this.covidTestingUnvaccinated = covidTestingUnvaccinated;
        this.covidTestingDetails = covidTestingDetails;
        this.contactTracing = contactTracing;
        this.contactTracingDetails = contactTracingDetails;
        this.opCapacity = opCapacity;
        this.opCapacityDetails = opCapacityDetails;
        this.opGuidelines = opGuidelines;
        this.referenceLink = referenceLink;
        this.industryId = industryId;
    }

    public GuidelineInputModel(UUID guidelineId,
                               boolean canOpOnSite, 
                               String canOpOnSiteDetails,
                               int groupSize, 
                               String groupSizeDetails, 
                               int covidTestingVaccinated, 
                               int covidTestingUnvaccinated,
                               String covidTestingDetails, 
                               String contactTracing, 
                               String contactTracingDetails,
                               int opCapacity, 
                               String opCapacityDetails, 
                               String opGuidelines,
                               String referenceLink, 
                               UUID industryId) {
        this.canOpOnSite = canOpOnSite;
        this.canOpOnSiteDetails = canOpOnSiteDetails;
        this.groupSize = groupSize;
        this.groupSizeDetails = groupSizeDetails;
        this.covidTestingVaccinated = covidTestingVaccinated;
        this.covidTestingUnvaccinated = covidTestingUnvaccinated;
        this.covidTestingDetails = covidTestingDetails;
        this.contactTracing = contactTracing;
        this.contactTracingDetails = contactTracingDetails;
        this.opCapacity = opCapacity;
        this.opCapacityDetails = opCapacityDetails;
        this.opGuidelines = opGuidelines;
        this.referenceLink = referenceLink;
        this.industryId = industryId;
    }

    private UUID guidelineId;

    @NotNull(message = "Operation Status Required.")
    private boolean canOpOnSite;

    @NotBlank(message = "Operation Site Details Required. ")
    @Size(min=3)
    private String canOpOnSiteDetails;

    @NotNull(message = "Group size required.")
    private int groupSize;

    @NotBlank(message = "Group size details required.")
    @Size(min=3)
    private String groupSizeDetails;

    @NotNull(message = "Number of vaccinated employees required.")
    private int covidTestingVaccinated;

    @NotNull(message = "Number of total unvaccinated employees required.")
    private int covidTestingUnvaccinated;

    @NotBlank(message = "Covid testing details required.")
    @Size(min=3)
    private String covidTestingDetails;

    @NotBlank(message = "Contact tracing required.")
    @Size(min=3)
    private String contactTracing;

    @NotBlank(message = "Contact tracing details required.")
    @Size(min=3)
    private String contactTracingDetails;

    @NotNull(message = "Operating capacity required.")
    private int opCapacity;

    @NotBlank(message = "Operating capacity details required.")
    @Size(min=3)
    private String opCapacityDetails;

    @NotBlank(message = "Operation guidelines required.")
    @Size(min=3)
    private String opGuidelines;

    @NotNull(message = "Reference link if applicable, else please input NIL.")
    @Size(min=3)
    private String referenceLink;

    @NotNull(message = "Please select the industry that this guideline applies to.")
    private UUID industryId;


    public Set<ConstraintViolation<GuidelineInputModel>> validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(this);
    }

    public boolean isCanOpOnSite() {
        return canOpOnSite;
    }

    public void setCanOpOnSite(boolean canOpOnSite) {
        this.canOpOnSite = canOpOnSite;
    }

    public String getCanOpOnSiteDetails() {
        return canOpOnSiteDetails;
    }

    public void setCanOpOnSiteDetails(String canOpOnSiteDetails) {
        this.canOpOnSiteDetails = canOpOnSiteDetails;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public String getGroupSizeDetails() {
        return groupSizeDetails;
    }

    public void setGroupSizeDetails(String groupSizeDetails) {
        this.groupSizeDetails = groupSizeDetails;
    }

    public int getCovidTestingVaccinated() {
        return covidTestingVaccinated;
    }

    public void setCovidTestingVaccinated(int covidTestingVaccinated) {
        this.covidTestingVaccinated = covidTestingVaccinated;
    }

    public int getCovidTestingUnvaccinated() {
        return covidTestingUnvaccinated;
    }

    public void setCovidTestingUnvaccinated(int covidTestingUnvaccinated) {
        this.covidTestingUnvaccinated = covidTestingUnvaccinated;
    }

    public String getCovidTestingDetails() {
        return covidTestingDetails;
    }

    public void setCovidTestingDetails(String covidTestingDetails) {
        this.covidTestingDetails = covidTestingDetails;
    }

    public String getContactTracing() {
        return contactTracing;
    }

    public void setContactTracing(String contactTracing) {
        this.contactTracing = contactTracing;
    }

    public String getContactTracingDetails() {
        return contactTracingDetails;
    }

    public void setContactTracingDetails(String contactTracingDetails) {
        this.contactTracingDetails = contactTracingDetails;
    }

    public int getOpCapacity() {
        return opCapacity;
    }

    public void setOpCapacity(int operatingCapacity) {
        this.opCapacity = operatingCapacity;
    }

    public String getOpCapacityDetails() {
        return opCapacityDetails;
    }

    public void setOpCapacityDetails(String opCapacityDetails) {
        this.opCapacityDetails = opCapacityDetails;
    }

    public String getOpGuidelines() {
        return opGuidelines;
    }

    public void setOpGuidelines(String opGuidelines) {
        this.opGuidelines = opGuidelines;
    }

    public String getReferenceLink() {
        return referenceLink;
    }

    public void setReferenceLink(String referenceLink) {
        this.referenceLink = referenceLink;
    }

    public UUID getGuidelineId() {
        return guidelineId;
    }

    public void setGuidelineId(UUID guidelineId) {
        this.guidelineId = guidelineId;
    }

    public UUID getIndustryId() {
        return industryId;
    }

    public void setIndustrId(UUID industryId) {
        this.industryId = industryId;
    }
}
