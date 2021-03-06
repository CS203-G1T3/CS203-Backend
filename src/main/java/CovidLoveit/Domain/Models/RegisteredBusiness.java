package CovidLoveit.Domain.Models;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="REGISTERED_BUSINESS")
public class RegisteredBusiness {

    @Id
    @GeneratedValue
    @Column(name = "businessId", unique = true, nullable = false)
    private UUID businessId;

    @Column(name = "businessName", nullable = false)
    private String businessName;

    @Column(name = "businessDesc")
    private String businessDesc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industryId")
    private Industry industry;

    @OneToOne
    @JoinColumn(name = "clientId")
    private Client client;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "business")
    private List<EmployeeRecord> employeeRecords;

    public RegisteredBusiness(){

    }

    public RegisteredBusiness(String name, String desc){
        this.businessName = name;
        this.businessDesc = desc;
    }

    public RegisteredBusiness(UUID businessId, String name, String desc, Industry industry, Client client){
        this.businessId = businessId;
        this.businessName = name;
        this.businessDesc = desc;
        this.industry = industry;
        this.client = client;
    }


    public UUID getBusinessId() { return businessId; }

    public String getBusinessName() { return businessName; }

    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getBusinessDesc() { return businessDesc; }

    public void setBusinessDesc(String businessDesc) { this.businessDesc = businessDesc; }

    public Client getClient() { return client; }

    public void setClient(Client client) { this.client = client; }

    public Industry getIndustry() { return this.industry; }

    public void setIndustry(Industry industry) { this.industry = industry; }

    public List<EmployeeRecord> getEmployeeRecords() {
        return employeeRecords;
    }

    public void setEmployeeRecords(List<EmployeeRecord> employeeRecords) {
        this.employeeRecords = employeeRecords;
    }
}
