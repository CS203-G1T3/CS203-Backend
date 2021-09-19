package CovidLoveit.Controllers.DataTransferObjects;

import java.util.Date;
import java.util.UUID;

// Data Transfer Object for http JSON response
public class ClientDTO {
    
    private UUID clientId;
    private String email;
    private boolean isAdmin = false;
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

    public boolean isIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public RegisteredBusinessDTO getRegisteredBusiness() {
        return this.registeredBusiness;
    }

    public void setRegisteredBusiness(RegisteredBusinessDTO registeredBusinessDTO) {
        this.registeredBusiness = registeredBusinessDTO;
    }

}
