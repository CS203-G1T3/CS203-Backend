package CovidLoveit.Domain.DataTransferObjects;

import java.time.LocalDate;
import java.util.UUID;

public class EmployeeRecordDTO {

    private UUID employeeId;
    private String employeeName;
    private LocalDate dateOfBirth;
    private String vaccine;
    private LocalDate lastSwabDate;
    private String swabResult;

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
