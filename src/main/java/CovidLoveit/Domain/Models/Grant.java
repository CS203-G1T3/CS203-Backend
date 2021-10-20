package CovidLoveit.Domain.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "COVID_GRANTS")
public class Grant {

    @Id
    @GeneratedValue
    @Column(name = "grantId")
    private UUID grantId;

    @Column(name = "grantName")
    private String grantName;

    @Column(name = "provider")
    private String provider;

    @Column(name = "grantDesc")
    private String grantDesc;

    @Column(name = "value")
    private int value;

    @Column(name = "eligibilityCriteria")
    private String eligibilityCriteria;

    @Column(name = "applicationProcess")
    private String applicationProcess;

    @Column(name = "benefits")
    private String benefits;

    @Column(name = "grantLink")
    private String grantLink;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "industryId")
    private List<Industry> industries = new ArrayList<>();

    public Grant() {
    }

    public Grant(String grantName, String provider, String grantDesc, int value, String eligibilityCriteria,
                 String applicationProcess, String benefits, String grantLink) {
        this.grantName = grantName;
        this.provider = provider;
        this.grantDesc = grantDesc;
        this.value = value;
        this.eligibilityCriteria = eligibilityCriteria;
        this.applicationProcess = applicationProcess;
        this.benefits = benefits;
        this.grantLink = grantLink;
    }

    public Grant(UUID grantId, String grantName, String provider, String grantDesc, int value,
                 String eligibilityCriteria, String applicationProcess, String benefits, String grantLink,
                 List<Industry> industries) {
        this.grantId = grantId;
        this.grantName = grantName;
        this.provider = provider;
        this.grantDesc = grantDesc;
        this.value = value;
        this.eligibilityCriteria = eligibilityCriteria;
        this.applicationProcess = applicationProcess;
        this.benefits = benefits;
        this.grantLink = grantLink;
        this.industries = industries;
    }

    public Grant(String grantName, String provider, String grantDesc, int value, String eligibilityCriteria,
                 String applicationProcess, String benefits, String grantLink, List<Industry> industries) {
        this.grantName = grantName;
        this.provider = provider;
        this.grantDesc = grantDesc;
        this.value = value;
        this.eligibilityCriteria = eligibilityCriteria;
        this.applicationProcess = applicationProcess;
        this.benefits = benefits;
        this.grantLink = grantLink;
        this.industries = industries;
    }

    public UUID getGrantId() {
        return grantId;
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

    public List<Industry> getIndustries() {
        return industries;
    }

    public void setIndustries(List<Industry> industries) {
        this.industries = industries;
    }
}
