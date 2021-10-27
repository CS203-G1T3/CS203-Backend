package CovidLoveit.Domain.DataTransferObjects;


import java.util.List;
import java.util.UUID;

public class GrantDTO {

    private UUID grantId;
    private String grantName;
    private String provider;
    private String grantDesc;
    private int value;
    private String eligibilityCriteria;
    private String applicationProcess;
    private String benefits;
    private String grantLink;
    private List<UUID> industryId;

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

    public List<UUID> getIndustryId() {
        return industryId;
    }

    public void setIndustryId(List<UUID> industries) {
        this.industryId = industries;
    }
}
