package CovidLoveit.Domain.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CLIENT")
public class Client {

    // TODO: Remove the generated value when frontend application is up
    @Id
    @GeneratedValue
    private UUID clientId;

    private String email;

    private String compName;

    private String compDesc;

    private boolean isAdmin;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "client")
    private RegisteredBusiness registeredBusiness;

    public Client() {
    }

    public Client(String email, String compName, String compDesc, boolean isAdmin) {
        this.email = email;
        this.compName = compName;
        this.compDesc = compDesc;
        this.isAdmin = isAdmin;
    }

    public UUID getClientId() {
        return clientId;
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

    public RegisteredBusiness getRegisteredBusiness() {
        return registeredBusiness;
    }

    public void setRegisteredBusiness(RegisteredBusiness registeredBusiness) {
        this.registeredBusiness = registeredBusiness;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
