package CovidLoveit.Domain.Models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "GUIDELINES")
public class Guideline {

    @Id
    @GeneratedValue
    private int guidelineId;

    private LocalDateTime createdAt;

    private boolean canOperateOnSite;

    private boolean canOperateOnSiteDetails;

    private int groupSize;

    private int covidTestingVaccinated;

    private int covidTestingUnvaccinated;

    private String covidTestingDetails;

    private String contactTracing;

    private String contactTracingDetails;

    private int operatingCapacity;

    private String operatingCapacityDetails;

    private String operationGuidelines;

    private String referenceLink;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "industryId")
    private Industry industry;

    public int getGuidelineId() {
        return guidelineId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public boolean isCanOperateOnSite() {
        return canOperateOnSite;
    }

    public void setCanOperateOnSite(boolean canOperateOnSite) {
        this.canOperateOnSite = canOperateOnSite;
    }

    public boolean isCanOperateOnSiteDetails() {
        return canOperateOnSiteDetails;
    }

    public void setCanOperateOnSiteDetails(boolean canOperateOnSiteDetails) {
        this.canOperateOnSiteDetails = canOperateOnSiteDetails;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
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
