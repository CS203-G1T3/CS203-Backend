package CovidLoveit.Domain.InputModel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class GuidelineInputModel {

    public GuidelineInputModel(){}

    public GuidelineInputModel(boolean canOperateOnSite, String canOperateOnSiteDetails,
                               int groupSize, String groupSizeDetails, int covidTestingVaccinated, int covidTestingUnvaccinated,
                               String covidTestingDetails, String contactTracing, String contactTracingDetails,
                               int operatingCapacity, String operatingCapacityDetails, String operationGuidelines,
                               String referenceLink) {
        this.canOpOnSite = canOperateOnSite;
        this.canOpOnSiteDetails = canOperateOnSiteDetails;
        this.groupSize = groupSize;
        this.groupSizeDetails = groupSizeDetails;
        this.covidTestingVaccinated = covidTestingVaccinated;
        this.covidTestingUnvaccinated = covidTestingUnvaccinated;
        this.covidTestingDetails = covidTestingDetails;
        this.contactTracing = contactTracing;
        this.contactTracingDetails = contactTracingDetails;
        this.operatingCapacity = operatingCapacity;
        this.opCapacityDetails = operatingCapacityDetails;
        this.opGuidelines = operationGuidelines;
        this.referenceLink = referenceLink;

    }

    public GuidelineInputModel(int guidelineId, boolean canOperateOnSite, String canOperateOnSiteDetails,
                               int groupSize, String groupSizeDetails, int covidTestingVaccinated, int covidTestingUnvaccinated,
                               String covidTestingDetails, String contactTracing, String contactTracingDetails,
                               int operatingCapacity, String operatingCapacityDetails, String operationGuidelines,
                               String referenceLink) {
        this.guidelineId = guidelineId;
        this.canOpOnSite = canOperateOnSite;
        this.canOpOnSiteDetails = canOperateOnSiteDetails;
        this.groupSize = groupSize;
        this.groupSizeDetails = groupSizeDetails;
        this.covidTestingVaccinated = covidTestingVaccinated;
        this.covidTestingUnvaccinated = covidTestingUnvaccinated;
        this.covidTestingDetails = covidTestingDetails;
        this.contactTracing = contactTracing;
        this.contactTracingDetails = contactTracingDetails;
        this.operatingCapacity = operatingCapacity;
        this.opCapacityDetails = operatingCapacityDetails;
        this.opGuidelines = operationGuidelines;
        this.referenceLink = referenceLink;

    }

    private int guidelineId;

    @NotNull(message = "Operation Status Required.")
    private boolean canOpOnSite;

    @NotBlank(message = "Operation Site Details Required. ")
    @Size(min=3, max=512)
    private String canOpOnSiteDetails;

    @NotNull(message = "Group size required.")
    private int groupSize;

    @NotBlank(message = "Group size details required.")
    @Size(min=3, max=512)
    private String groupSizeDetails;

    @NotNull(message = "Number of vaccinated employees required.")
    private int covidTestingVaccinated;

    @NotNull(message = "Number of total unvaccinated employees required.")
    private int covidTestingUnvaccinated;

    @NotBlank(message = "Covid testing details required.")
    @Size(min=3, max=512)
    private String covidTestingDetails;

    @NotBlank(message = "Contact tracing required.")
    @Size(min=3, max=512)
    private String contactTracing;

    @NotBlank(message = "Contact tracing details required.")
    @Size(min=3, max=512)
    private String contactTracingDetails;

    @NotNull(message = "Operating capacity reuired.")
    private int operatingCapacity;

    @NotBlank(message = "Operating capacity details required.")
    @Size(min=3, max=512)
    private String opCapacityDetails;

    @NotBlank(message = "Operation guidelines required.")
    @Size(min=3, max=1024)
    private String opGuidelines;

    @NotNull(message = "Reference link if applicable, else please input NIL")
    @Size(min=3, max=512)
    private String referenceLink;

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

    public int getOperatingCapacity() {
        return operatingCapacity;
    }

    public void setOperatingCapacity(int operatingCapacity) {
        this.operatingCapacity = operatingCapacity;
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

    public int getGuidelineId() {
        return guidelineId;
    }

    public void setGuidelineId(int guidelineId) {
        this.guidelineId = guidelineId;
    }
}
