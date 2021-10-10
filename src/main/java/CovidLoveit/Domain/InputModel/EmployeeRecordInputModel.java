package CovidLoveit.Domain.InputModel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

public class EmployeeRecordInputModel {

    public EmployeeRecordInputModel() {
    }

    public EmployeeRecordInputModel(String employeeName, String dateOfBirth, String vaccine, String lastSwabDate, String swabResult, UUID businessId) {
        this.employeeName = employeeName;
        this.dateOfBirth = dateOfBirth;
        this.vaccine = vaccine;
        this.lastSwabDate = lastSwabDate;
        this.swabResult = swabResult;
        this.businessId = businessId;
    }

    public EmployeeRecordInputModel(UUID employeeId, String employeeName, String dateOfBirth, String vaccine, String lastSwabDate, String swabResult, UUID businessId) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.dateOfBirth = dateOfBirth;
        this.vaccine = vaccine;
        this.lastSwabDate = lastSwabDate;
        this.swabResult = swabResult;
        this.businessId = businessId;
    }


    private UUID employeeId;

    @NotEmpty(message = "Please provide an employee name.")
    private String employeeName;

    @NotEmpty(message = "Please input a valid date of birth format dd/MM/yyyy")
    private String dateOfBirth;

    private String vaccine;

    @NotEmpty(message = "Please input a valid date format dd/MM/yyyy")
    private String lastSwabDate;

    private String swabResult;

    @NotNull(message = "Company ID required.")
    private UUID businessId;

    public Set<ConstraintViolation<EmployeeRecordInputModel>> validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(this);
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getLastSwabDate() {
        return lastSwabDate;
    }

    public void setLastSwabDate(String lastSwabDate) {
        this.lastSwabDate = lastSwabDate;
    }

    public String getSwabResult() {
        return swabResult;
    }

    public void setSwabResult(String swabResult) {
        this.swabResult = swabResult;
    }

    public UUID getBusinessId() {
        return businessId;
    }

    public void setBusinessId(UUID businessId) {
        this.businessId = businessId;
    }
}
