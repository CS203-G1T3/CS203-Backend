package CovidLoveit.Domain.Models;

import javax.persistence.*;


import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "ROLE")
public class Role {
    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "RoleId")
    private Long id;

    @Column(name = "roleName")
    private String roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setName(String roleName) {
        this.roleName = roleName;
    }
}
