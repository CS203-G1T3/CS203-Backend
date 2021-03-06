package CovidLoveit.Domain.DataTransferObjects;


import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GuidelineDTO {

    private UUID guidelineId;
    private UUID industryId;
    private boolean canOpOnSite;
    private String canOpOnSiteDetails;
    private int groupSize;
    private String groupSizeDetails;
    private int covidTestingVaccinated;
    private int covidTestingUnvaccinated;
    private String covidTestingDetails;
    private String contactTracing;
    private String contactTracingDetails;
    private int opCapacity;
    private String opCapacityDetails;
    private String opGuidelines;
    private String referenceLink;
    private Date createdAt;

    public UUID getGuidelineId() {
        return guidelineId;
    }

    public void setGuidelineId(UUID guidelineId) {
        this.guidelineId = guidelineId;
    }

    @JsonProperty("isCanOpOnSite")
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

    public void setOpCapacity(int opCapacity) {
        this.opCapacity = opCapacity;
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

    public UUID getIndustryId() {
        return industryId;
    }

    public void setIndustryId(UUID industryId) {
        this.industryId = industryId;
    }
    
    @JsonProperty("createdAt")
    public Date getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
