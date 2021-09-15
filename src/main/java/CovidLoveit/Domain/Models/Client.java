package CovidLoveit.Domain.Models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
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

    private boolean isAdmin = false;

    @CreationTimestamp
    @Basic(optional = false)
    @Column(name = "createdAt", nullable = false, updatable = false)
    private Date createdAt;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "client")
    private RegisteredBusiness registeredBusiness;

    public Client() {
    }

    public Client(String email){
        this.email = email;
    }

    public Client(String email, boolean isAdmin){
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public Client(String email, boolean isAdmin, RegisteredBusiness registeredBusiness) {
        this.email = email;
        this.isAdmin = isAdmin;
        this.registeredBusiness = registeredBusiness;
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
