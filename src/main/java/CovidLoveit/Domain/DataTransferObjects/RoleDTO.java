package CovidLoveit.Domain.DataTransferObjects;

import CovidLoveit.Domain.Models.Client;

import java.util.List;

public class RoleDTO {
    private Long id;

    private String roleName;

    private List<Client> client;

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

    public List<Client> getClients() {
        return client;
    }

    public void setClients(List<Client> client) {
        this.client = client;
    }
}
