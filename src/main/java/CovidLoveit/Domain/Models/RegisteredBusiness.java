package CovidLoveit.Domain.Models;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="REGISTERED_BUSINESS")
public class RegisteredBusiness {

    @Id
    @GeneratedValue
    private UUID businessId;

    private String businessName;

    private String businessDesc;

    @ManyToOne
    @JoinColumn(name = "industryId")
    private Industry industry;

    @OneToOne
    @JoinColumn(name = "clientId")
    private Client client;

    public RegisteredBusiness(){

    }

    public RegisteredBusiness(String name, String desc){
        this.businessName = name;
        this.businessDesc = desc;
    }


    public UUID getBusinessId() { return businessId; }

    public void setBusinessId(UUID businessId) { this.businessId = businessId; }

    public String getBusinessName() { return businessName; }

    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getBusinessDesc() { return businessDesc; }

    public void setBusinessDesc(String businessDesc) { this.businessDesc = businessDesc; }

    public Client getClient() { return client; }

    public void setClient(Client client) { this.client = client; }
}
