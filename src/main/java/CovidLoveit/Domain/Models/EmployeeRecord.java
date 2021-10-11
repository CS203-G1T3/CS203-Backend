package CovidLoveit.Domain.Models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "EMPLOYEE_RECORD")
public class EmployeeRecord {

    public EmployeeRecord() {
    }

    public EmployeeRecord(UUID employeeId, String employeeName, LocalDate dateOfBirth,
                          String vaccine, LocalDate lastSwabDate, String swabResult) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.dateOfBirth = dateOfBirth;
        this.vaccine = vaccine;
        this.lastSwabDate = lastSwabDate;
        this.swabResult = swabResult;
    }

    public EmployeeRecord(String employeeName, LocalDate dateOfBirth,
                          String vaccine, LocalDate lastSwabDate, String swabResult) {
        this.employeeName = employeeName;
        this.dateOfBirth = dateOfBirth;
        this.vaccine = vaccine;
        this.lastSwabDate = lastSwabDate;
        this.swabResult = swabResult;
    }

    @Id
    @GeneratedValue
    @Column(name = "EID", unique = true, nullable = false)
    private UUID employeeId;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "DOB")
    private LocalDate dateOfBirth;

    @Column(name = "Vaccine")
    private String vaccine;

    @Column(name = "Last_swab_date")
    private LocalDate lastSwabDate;

    @Column(name = "swab_result")
    private String swabResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "businessId")
    private RegisteredBusiness business;

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

    public RegisteredBusiness getBusiness() {
        return business;
    }

    public void setBusiness(RegisteredBusiness business) {
        this.business = business;
    }
}
