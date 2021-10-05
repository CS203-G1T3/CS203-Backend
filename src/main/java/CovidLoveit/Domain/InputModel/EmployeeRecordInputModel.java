package CovidLoveit.Domain.InputModel;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Set;

public class EmployeeRecordInputModel {

    public EmployeeRecordInputModel() {
    }

    @NotEmpty(message = "Please provide an employee ID.")
    private String employeeId;

    @NotEmpty(message = "Please provide an employee name.")
    private String employeeName;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Past(message = "Please input a valid date of birth.")
    private LocalDate dateOfBirth;

    private String vaccine;

    private LocalDate lastSwabDate;

    private String swabResult;

    public Set<ConstraintViolation<EmployeeRecordInputModel>> validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(this);
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public LocalDate getLastSwabDate() {
        return lastSwabDate;
    }

    public void setLastSwabDate(LocalDate lastSwabDate) {
        this.lastSwabDate = lastSwabDate;
    }

    public String getSwabResult() {
        return swabResult;
    }

    public void setSwabResult(String swabResult) {
        this.swabResult = swabResult;
    }
}
