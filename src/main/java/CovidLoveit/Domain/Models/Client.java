package CovidLoveit.Domain.Models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "CLIENT")
public class Client {

    public Client() {
    }

    public Client(String password, String email){
        this.password = password;
        this.email = email;
    }

    public Client(String password, Collection<Role> roles, String email) {
        this.password = password;
        this.roles = roles;
        this.email = email;
    }

    public Client(UUID clientId, String password, Collection<Role> roles, String email) {
        this.clientId = clientId;
        this.password = password;
        this.roles = roles;
        this.email = email;
    }

    // TODO: Remove the generated value when frontend application is up
    @Id
    @GeneratedValue
    @Column(name = "clientId", unique = true, nullable = false)
    private UUID clientId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId")
    private Collection<Role> roles = new ArrayList<>();

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @CreationTimestamp
    @Basic(optional = false)
    @Column(name = "createdAt", nullable = false, updatable = false)
    private Date createdAt;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "client")
    private RegisteredBusiness registeredBusiness;

    public UUID getClientId() {
        return clientId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public RegisteredBusiness getRegisteredBusiness() {
        return registeredBusiness;
    }

    public void setRegisteredBusiness(RegisteredBusiness registeredBusiness) {
        this.registeredBusiness = registeredBusiness;
    }
}
