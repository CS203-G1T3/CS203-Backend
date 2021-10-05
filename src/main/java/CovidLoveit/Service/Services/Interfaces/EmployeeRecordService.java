package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.Models.EmployeeRecord;

import java.util.List;
import java.util.UUID;

public interface EmployeeRecordService {

    EmployeeRecord addEmployee(EmployeeRecord employee);

    EmployeeRecord updateEmployee(EmployeeRecord employeeRecord);

    void deleteEmployee(String employeeId);

    EmployeeRecord getEmployeeById(String employeeId);

    List<EmployeeRecord> getAllEmployee();

    List<EmployeeRecord> getEmployeesByBusiness(UUID businessId);
}
