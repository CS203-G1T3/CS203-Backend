package CovidLoveit.Domain.Models;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "GUIDELINES")
public class Guideline {

    @Id
    @GeneratedValue
    private int guidelineId;

    private LocalDateTime createdAt;

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

    @ManyToOne
    @JoinColumn(name = "industryId")
    private Industry industry;

    public Guideline(LocalDateTime createdAt, boolean canOperateOnSite, String canOperateOnSiteDetails, 
                     int groupSize, String groupSizeDetails, int covidTestingVaccinated, int covidTestingUnvaccinated, 
                     String covidTestingDetails, String contactTracing, String contactTracingDetails, 
                     int operatingCapacity, String operatingCapacityDetails, String operationGuidelines,
                     String referenceLink) {
        this.createdAt = createdAt;
        this.canOperateOnSite = canOperateOnSite;
        this.canOperateOnSiteDetails = canOperateOnSiteDetails;
        this.groupSize = groupSize;
        this.groupSizeDetails = groupSizeDetails;
        this.covidTestingVaccinated = covidTestingVaccinated;
        this.covidTestingUnvaccinated = covidTestingUnvaccinated;
        this.covidTestingDetails = covidTestingDetails;
        this.contactTracing = contactTracing;
        this.contactTracingDetails = contactTracingDetails;
        this.operatingCapacity = operatingCapacity;
        this.operatingCapacityDetails = operatingCapacityDetails;
        this.operationGuidelines = operationGuidelines;
        this.referenceLink = referenceLink;
    }

    public int getGuidelineId() {
        return guidelineId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isCanOperateOnSite() {
        return canOperateOnSite;
    }

    public void setCanOperateOnSite(boolean canOperateOnSite) {
        this.canOperateOnSite = canOperateOnSite;
    }

    public String isCanOperateOnSiteDetails() {
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
