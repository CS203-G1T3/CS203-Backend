package CovidLoveit.Domain.InputModel;

import CovidLoveit.Domain.Models.Role;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;


public class ClientInputModel {

    public ClientInputModel() {
    }

    private UUID clientId;

    @NotBlank(message = "Username cannot be empty")
    private String username;

    private String password;

    private Collection<Role> roles;

    @Email(message = "Please provide a valid email address.")
    private String email;

    public Set<ConstraintViolation<ClientInputModel>> validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(this);
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
