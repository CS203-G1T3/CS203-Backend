package CovidLoveit.Domain.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="INDUSTRY")
public class Industry {

    @Id
    @GeneratedValue
    @Column(name = "industryId", unique = true, nullable = false)
    private UUID industryId;

    @Column(name = "industryName", nullable = false)
    private String industryName;

    @Column(name = "industrySubtype", unique = true, nullable = false)
    private String industrySubtype;

    @Column(name = "industryDesc")
    private String industryDesc;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "industry")
    private List<Guideline> guidelines;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "industry")
    private List<RegisteredBusiness> registeredBusinesses = new ArrayList<>();

    @ManyToMany(mappedBy = "industries")
    private List<Grant> grants = new ArrayList<>();

    public Industry(String industryName, String industrySubtype, String industryDesc) {
        this.industryName = industryName;
        this.industrySubtype = industrySubtype;
        this.industryDesc = industryDesc;
    }

    public Industry(UUID industryId, String industryName, String industrySubtype, String industryDesc) {
        this.industryId = industryId;
        this.industryName = industryName;
        this.industrySubtype = industrySubtype;
        this.industryDesc = industryDesc;
    }

    public Industry() {
    }

    public UUID getIndustryId() {
        return industryId;
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

    public List<Guideline> getGuidelines() {
        return guidelines;
    }

    public void setGuidelines(List<Guideline> guidelines) {
        this.guidelines = guidelines;
    }

    public List<RegisteredBusiness> getRegisteredBusinesses() {
        return registeredBusinesses;
    }

    public void setRegisteredBusinesses(List<RegisteredBusiness> registeredBusinesses) {
        this.registeredBusinesses = registeredBusinesses;
    }

    public List<Grant> getGrants() {
        return grants;
    }

    public void setGrants(List<Grant> grants) {
        this.grants = grants;
    }
}
