package CovidLoveit.Domain.Models;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="USER")
public class User {

    // TODO: Remove the generated value when frontend application is up
    @Id
    @GeneratedValue
    private UUID userId;

    private String email;

    private String companyName;

    private String companyDescription;

    @OneToOne(mappedBy="user")
    private Industry industry;


    // TODO: Finalise the database design
//    @OneToMany(mappedBy="user")
//    private List<IndustrySubtype> industrySubtype;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }
}
