package CovidLoveit.Service.Services;

import CovidLoveit.Domain.Models.EmployeeRecord;
import CovidLoveit.Exception.EmployeeRecordException;
import CovidLoveit.Exception.RegisteredBusinessException;
import CovidLoveit.Repositories.Interfaces.EmployeeRecordRepository;
import CovidLoveit.Repositories.Interfaces.RegisteredBusinessRepository;
import CovidLoveit.Service.Services.Interfaces.EmployeeRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EmployeeRecordServiceImpl implements EmployeeRecordService {

    private Logger logger = LoggerFactory.getLogger(EmployeeRecordServiceImpl.class);
    private final EmployeeRecordRepository employeeRepository;
    private final RegisteredBusinessRepository registeredBusinessRepository;

    @Autowired
    public EmployeeRecordServiceImpl(EmployeeRecordRepository employeeRepository, RegisteredBusinessRepository registeredBusinessRepository) {
        this.employeeRepository = employeeRepository;
        this.registeredBusinessRepository = registeredBusinessRepository;
    }

    @Override
    public EmployeeRecord addEmployee(EmployeeRecord employeeRecord) {
        var employee = employeeRepository.getById(employeeRecord.getEmployeeId());
        if (employee != null) {
            logger.warn(String.format("Employee with EID {%s} already exist in DB.", employeeRecord.getEmployeeId()));
            throw new EmployeeRecordException(String.format("Employee with EID {%s} already exist in DB.", employeeRecord.getEmployeeId()));
        }

        var savedEmployee = employeeRepository.save(employeeRecord);
        logger.info(String.format("Successfully added employee with EID {%s}", employeeRecord.getEmployeeId()));
        return savedEmployee;
    }

    @Override
    public EmployeeRecord updateEmployee(EmployeeRecord employeeRecord) {
        var employee = employeeRepository.getById(employeeRecord.getEmployeeId());
        if (employee == null) {
            logger.warn(String.format("Employee with EID {%s} does not exist in DB.", employeeRecord.getEmployeeId()));
            throw new EmployeeRecordException(String.format("Employee with EID {%s} does not exist in DB.", employeeRecord.getEmployeeId()));
        }

        employee.setEmployeeName(employeeRecord.getEmployeeName());
        employee.setBusiness(employeeRecord.getBusiness());
        employee.setDateOfBirth(employeeRecord.getDateOfBirth());
        employee.setVaccine(employeeRecord.getVaccine());
        employee.setLastSwabDate(employeeRecord.getLastSwabDate());
        employee.setSwabResult(employeeRecord.getSwabResult());

        var savedEmployee = employeeRepository.save(employee);

        logger.info(String.format("Successfully updated employee with ID {%s}", employeeRecord.getEmployeeId()));
        return savedEmployee;
    }

    @Override
    public void deleteEmployee(String employeeId) {
        var employee = employeeRepository.getById(employeeId);
        if (employee == null) {
            logger.warn(String.format("Employee with EID {%s} does not exist in DB.", employeeId));
            throw new EmployeeRecordException(String.format("Employee with EID {%s} does not exist in DB.", employeeId));
        }

        employeeRepository.deleteById(employeeId);
        logger.info(String.format("Successfully removed employee with ID {%s}", employeeId));
    }

    @Override
    public EmployeeRecord getEmployeeById(String employeeId) {
        var employee = employeeRepository.getById(employeeId);
        if (employee == null) {
            logger.warn(String.format("Employee with EID {%s} does not exist in DB.", employeeId));
            throw new EmployeeRecordException(String.format("Employee with EID {%s} does not exist in DB.", employeeId));
        }

        return employee;
    }

    @Override
    public List<EmployeeRecord> getEmployeesByBusiness(UUID businessId) {
        var business = registeredBusinessRepository.getById(businessId);
        if (business == null) {
            logger.warn(String.format("Business with ID {%s} does not exist in DB.", businessId));
            throw new RegisteredBusinessException(String.format("Business with ID {%s} does not exist in DB.", businessId));
        }

        return employeeRepository.getEmployeeByBusiness(businessId.toString());
    }
}
