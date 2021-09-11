package CovidLoveit.Domain.Models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="INDUSTRY")
public class Industry {

    @Id
    @GeneratedValue
    private UUID industryId;

    private String industryName;

    private String industrySubtype;

    private String description;

    @OneToMany(mappedBy = "industry")
    private List<Guideline> guidelines = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "industry")
    private List<RegisteredBusiness> registeredBusinesses = new ArrayList<>();

    public Industry() {
    }

    public Industry(String industryName, String industrySubtype, String description) {
        this.industryName = industryName;
        this.industrySubtype = industrySubtype;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
