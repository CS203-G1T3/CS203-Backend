package CovidLoveit.Domain.DataTransferObjects;

import java.util.UUID;
import java.util.List;

// Data Transfer Object for http JSON response
public class IndustryDTO {
    
    private UUID industryId;
    private String industryName;
    private String industrySubtype;
    private String industryDesc;
    private List<GuidelineDTO> guidelines;
    private List<RegisteredBusinessDTO> registeredBusinesses;

    public UUID getIndustryId() {
        return industryId;
    }

    public void setIndustryId(UUID industryId) { this.industryId = industryId; }

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

    public String getIndustryDesc() {
        return industryDesc;
    }

    public void setIndustryDesc(String industryDesc) {
        this.industryDesc = industryDesc;
    }

    public List<GuidelineDTO> getGuidelines() {
        return guidelines;
    }

    public void setGuidelines(List<GuidelineDTO> guidelines) {
        this.guidelines = guidelines;
    }

    public List<RegisteredBusinessDTO> getRegisteredBusinesses() {
        return registeredBusinesses;
    }

    public void setRegisteredBusinesses(List<RegisteredBusinessDTO> registeredBusinesses) {
        this.registeredBusinesses = registeredBusinesses;
    }
}


