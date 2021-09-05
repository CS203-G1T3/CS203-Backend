package CovidLoveit.Domain.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CUSTOMER")
public class Customer {

    // TODO: Remove the generated value when frontend application is up
    @Id
    @GeneratedValue
    private UUID customerId;

    private String email;

    private String compName;

    private String compDesc;

    private boolean isAdmin;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<RegisteredBusiness> registeredBusinesses = new ArrayList<RegisteredBusiness>();

    public UUID getUserId() {
        return customerId;
    }

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

    public List<RegisteredBusiness> getRegisteredBusinesses() {
        return registeredBusinesses;
    }

    public void setRegisteredBusinesses(List<RegisteredBusiness> registeredBusinesses) {
        this.registeredBusinesses = registeredBusinesses;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
