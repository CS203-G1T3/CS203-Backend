package CovidLoveit.Domain.InputModel;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class ClientInputModel {

    public ClientInputModel() {
    }

    public ClientInputModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public ClientInputModel(String password, String email, List<String> roleNames) {
        this.password = password;
        this.roleNames = roleNames;
        this.email = email;
    }

    private UUID clientId;

    private String password;

    private List<String> roleNames;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roleNames;
    }

    public void setRoles(List<String> roleNames) {
        this.roleNames = roleNames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
