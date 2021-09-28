package CovidLoveit.Domain.InputModel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Email;
import java.util.Set;
import java.util.UUID;


public class ClientInputModel {

    public ClientInputModel() {
    }

    public ClientInputModel(UUID clientId, String email, boolean isAdmin){
        this.clientId = clientId;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public ClientInputModel(String email, boolean isAdmin){
        this.email = email;
        this.isAdmin = isAdmin;
    }

    private UUID clientId;

    @Email(message = "Email provided is invalid.")
    private String email;

    private boolean isAdmin;

    private RegisteredBusinessInputModel registeredBusiness;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
