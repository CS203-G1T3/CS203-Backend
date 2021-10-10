package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.InputModel.EmployeeRecordInputModel;
import CovidLoveit.Domain.Models.EmployeeRecord;

import java.util.List;
import java.util.UUID;

public interface EmployeeRecordService {

    EmployeeRecord addEmployee(EmployeeRecordInputModel employee);

    EmployeeRecord updateEmployee(EmployeeRecordInputModel employeeRecord);

    void deleteEmployee(UUID employeeId);

    EmployeeRecord getEmployeeById(UUID employeeId);

    List<EmployeeRecord> getEmployeesByBusiness(UUID businessId);
}
