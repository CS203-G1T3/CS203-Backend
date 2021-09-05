package CovidLoveit.Domain.Models;

import org.hibernate.mapping.Set;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="USER")
public class User {

    // TODO: Remove the generated value when frontend application is up
    @Id
    @GeneratedValue
    private UUID userId;

    private String email;

    private String compName;

    private String compDesc;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<RegisteredBusiness> registeredBusinesses = new ArrayList<RegisteredBusiness>();

    public UUID getUserId() { return userId; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return compName;
    }

    public void setCompanyName(String companyName) {
        this.compName = companyName;
    }

    public String getCompanyDescription() {
        return compDesc;
    }

    public void setCompanyDescription(String companyDescription) {
        this.compDesc = companyDescription;
    }
}
