package CovidLoveit.Domain.DataTransferObjects;

import CovidLoveit.Domain.Models.Role;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

// Data Transfer Object for http JSON response
public class ClientDTO {
    
    private UUID clientId;
    private Collection<Role> roles;
    private String email;
    private Date createdAt;
    private RegisteredBusinessDTO registeredBusiness;

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
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

    public RegisteredBusinessDTO getRegisteredBusiness() {
        return registeredBusiness;
    }

    public void setRegisteredBusiness(RegisteredBusinessDTO registeredBusiness) {
        this.registeredBusiness = registeredBusiness;
    }
}
