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

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "industry")
    private Industry industry;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_Id")
    private User user;

    public UUID getBusinessId() { return businessId; }

    public void setBusinessId(UUID businessId) { this.businessId = businessId; }

    public String getBusinessName() { return businessName; }

    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getBusinessDesc() { return businessDesc; }

    public void setBusinessDesc(String businessDesc) { this.businessDesc = businessDesc; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
