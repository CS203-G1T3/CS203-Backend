package CovidLoveit.Domain.InputModel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import java.util.Set;

public class RoleInputModel {
    public RoleInputModel() {
    }

    public RoleInputModel(String roleName) {
        this.roleName = roleName;
    }

    private Long id;

    @NotBlank(message = "Role name cannot be blank.")
    private String roleName;

    public Long getId() {
        return id;
    }

    public Set<ConstraintViolation<RoleInputModel>> validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(this);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
