package CovidLoveit.Domain.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Industry {

    @Id
    @GeneratedValue
    private UUID industryId;

    private String industryName;

    private String industrySubtype;

    private String description;

    // TODO: Create the mappings for industry and guidelines
    // In this case this will be a one-to-many relationship
//    private List<Guideline> guidelines = new ArrayList<>();

    // TODO: Generate the getters and setters
}
