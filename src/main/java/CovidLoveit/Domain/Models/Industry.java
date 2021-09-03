package CovidLoveit.Domain.Models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="INDUSTRY")
public class Industry {

    @Id
    private int industryId;

    private String name;

    private String description;
}
