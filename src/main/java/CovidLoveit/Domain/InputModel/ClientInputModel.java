package CovidLoveit.Domain.InputModel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;


public class ClientInputModel {

    public ClientInputModel(UUID clientId, String email, boolean isAdmin, RegisteredBusinessInputModel registeredBusinessInputModel){
        this.clientId = clientId;
        this.email = email;
        this.isAdmin = isAdmin;
        this.registeredBusiness = registeredBusinessInputModel;
    }

    public ClientInputModel(UUID clientId, String email, boolean isAdmin){
        this.clientId = clientId;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    @NotEmpty(message = "Client Id cannot be null or empty.")
    private UUID clientId;

    @Email(message = "Email should be valid.")
    private String email;

    private boolean isAdmin;

    private RegisteredBusinessInputModel registeredBusiness;

    public Set<ConstraintViolation<ClientInputModel>> validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(this);
    }
}
