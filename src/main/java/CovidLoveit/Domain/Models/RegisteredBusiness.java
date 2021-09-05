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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "industryId")
    private Industry industry;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customerId")
    private Customer customer;

    public UUID getBusinessId() { return businessId; }

    public void setBusinessId(UUID businessId) { this.businessId = businessId; }

    public String getBusinessName() { return businessName; }

    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getBusinessDesc() { return businessDesc; }

    public void setBusinessDesc(String businessDesc) { this.businessDesc = businessDesc; }

    public Customer getUser() { return customer; }

    public void setUser(Customer customer) { this.customer = customer; }
}
