package CovidLoveit.Controllers.DataTransferObjects;

import java.util.UUID;

// Data Transfer Object for http JSON response
public class RegisteredBusinessDTO {
    
    private UUID businessId;
    private String businessName;
    private String businessDesc;
    private UUID industryId;
    private UUID clientId;

    public UUID getBusinessId() {
        return this.businessId;
    }

    public void setBusinessId(UUID businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return this.businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessDesc() {
        return this.businessDesc;
    }

    public void setBusinessDesc(String businessDesc) {
        this.businessDesc = businessDesc;
    }

    public UUID getIndustryId() {
        return this.industryId;
    }

    public void setIndustryId(UUID industryId) {
        this.industryId = industryId;
    }

    public UUID getClientId() {
        return this.clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

}


