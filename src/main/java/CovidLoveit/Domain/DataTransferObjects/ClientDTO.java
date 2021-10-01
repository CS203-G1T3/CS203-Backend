package CovidLoveit.Domain.DataTransferObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

// Data Transfer Object for http JSON response
public class ClientDTO {
    
    private UUID clientId;
    private String email;
    private boolean isAdmin;
    private Date createdAt;
    private RegisteredBusinessDTO registeredBusiness;

    public UUID getClientId() {
        return this.clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("isAdmin")
    public boolean isAdmin() {
        return this.isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getCreatedAt() { return this.createdAt; }

    public RegisteredBusinessDTO getRegisteredBusiness() {
        return this.registeredBusiness;
    }

    public void setRegisteredBusiness(RegisteredBusinessDTO registeredBusinessDTO) {
        this.registeredBusiness = registeredBusinessDTO;
    }

}
