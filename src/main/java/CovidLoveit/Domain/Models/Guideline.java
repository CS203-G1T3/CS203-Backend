package CovidLoveit.Domain.Models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "GUIDELINES")
public class Guideline {

    @Id
    @GeneratedValue
    private int guidelineId;

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    private boolean canOpOnSite;

    private String canOpOnSiteDetails;

    private int groupSize;

    private String groupSizeDetails;

    private int covidTestingVaccinated;

    private int covidTestingUnvaccinated;

    private String covidTestingDetails;

    private String contactTracing;

    private String contactTracingDetails;

    private int operatingCapacity;

    private String opCapacityDetails;

    private String opGuidelines;

    private String referenceLink;

    @ManyToOne
    @JoinColumn(name = "industryId")
    private Industry industry;

    public Guideline() {
    }

    public Guideline(boolean canOperateOnSite, String canOperateOnSiteDetails,
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

    public int getGuidelineId() {
        return guidelineId;
    }

    public Date getCreatedAt() {
        return createdAt;
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

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }
}
