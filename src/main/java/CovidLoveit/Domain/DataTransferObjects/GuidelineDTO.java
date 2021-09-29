package CovidLoveit.Domain.DataTransferObjects;

public class GuidelineDTO {

    private boolean canOperateOnSite;
    private String canOperateOnSiteDetails;
    private int groupSize;
    private String groupSizeDetails;
    private int covidTestingVaccinated;
    private int covidTestingUnvaccinated;
    private String covidTestingDetails;
    private String contactTracing;
    private String contactTracingDetails;
    private int operatingCapacity;
    private String operatingCapacityDetails;
    private String operationGuidelines;
    private String referenceLink;

    public boolean isCanOperateOnSite() {
        return canOperateOnSite;
    }

    public void setCanOperateOnSite(boolean canOperateOnSite) {
        this.canOperateOnSite = canOperateOnSite;
    }

    public String getCanOperateOnSiteDetails() {
        return canOperateOnSiteDetails;
    }

    public void setCanOperateOnSiteDetails(String canOperateOnSiteDetails) {
        this.canOperateOnSiteDetails = canOperateOnSiteDetails;
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

    public String getOperatingCapacityDetails() {
        return operatingCapacityDetails;
    }

    public void setOperatingCapacityDetails(String operatingCapacityDetails) {
        this.operatingCapacityDetails = operatingCapacityDetails;
    }

    public String getOperationGuidelines() {
        return operationGuidelines;
    }

    public void setOperationGuidelines(String operationGuidelines) {
        this.operationGuidelines = operationGuidelines;
    }

    public String getReferenceLink() {
        return referenceLink;
    }

    public void setReferenceLink(String referenceLink) {
        this.referenceLink = referenceLink;
    }
}
