package CovidLoveit.Service.Services;

import CovidLoveit.Domain.InputModel.EmployeeRecordInputModel;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public EmployeeRecord addEmployee(EmployeeRecordInputModel inputModel) {

        var businessId = inputModel.getBusinessId();
        var registeredBusiness = registeredBusinessRepository.findById(businessId);
        registeredBusiness.orElseThrow(() -> {
            throw new RegisteredBusinessException(String.format("Business with ID {%s} not found", inputModel.getBusinessId()));
        });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dob = LocalDate.parse(inputModel.getDateOfBirth(), formatter);
        LocalDate swabDate = LocalDate.parse(inputModel.getLastSwabDate(), formatter);

        var employee = new EmployeeRecord(inputModel.getEmployeeName(), dob,
                inputModel.getVaccine(), swabDate, inputModel.getSwabResult());

        employee.setBusiness(registeredBusiness.get());

        var savedEmployee = employeeRepository.save(employee);
        logger.info(String.format("Successfully added employee with EID {%s}", inputModel.getEmployeeId()));
        return savedEmployee;
    }

    @Override
    public EmployeeRecord updateEmployee(EmployeeRecordInputModel inputModel) {
        var employeeOptional = employeeRepository.findById(inputModel.getEmployeeId());
        employeeOptional.orElseThrow(() -> {
            logger.warn(String.format("Employee with EID {%s} does not exist in DB.", inputModel.getEmployeeId()));
            throw new EmployeeRecordException(String.format("Employee with EID {%s} does not exist in DB.", inputModel.getEmployeeId()));
        });

        var businessId = inputModel.getBusinessId();
        var registeredBusiness = registeredBusinessRepository.findById(businessId);
        registeredBusiness.orElseThrow(() -> {
            throw new RegisteredBusinessException(String.format("Business with ID {%s} not found", inputModel.getBusinessId()));
        });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dob = LocalDate.parse(inputModel.getDateOfBirth(), formatter);
        LocalDate swabDate = LocalDate.parse(inputModel.getLastSwabDate(), formatter);

        var employee = employeeOptional.get();
        employee.setEmployeeName(inputModel.getEmployeeName());
        employee.setBusiness(registeredBusiness.get());
        employee.setDateOfBirth(dob);
        employee.setVaccine(inputModel.getVaccine());
        employee.setLastSwabDate(swabDate);
        employee.setSwabResult(inputModel.getSwabResult());

        var savedEmployee = employeeRepository.save(employee);

        logger.info(String.format("Successfully updated employee with ID {%s}", inputModel.getEmployeeId()));
        return savedEmployee;
    }

    @Override
    public void deleteEmployee(UUID employeeId) {
        var employeeOptional = employeeRepository.findById(employeeId);
        employeeOptional.orElseThrow(() -> {
            logger.warn(String.format("Employee with EID {%s} does not exist in DB.", employeeId));
            throw new EmployeeRecordException(String.format("Employee with EID {%s} does not exist in DB.", employeeId));

        });

        employeeRepository.deleteById(employeeId);
        logger.info(String.format("Successfully removed employee with ID {%s}", employeeId));
    }

    @Override
    public EmployeeRecord getEmployeeById(UUID employeeId) {
        var employee = employeeRepository.findById(employeeId);
        employee.orElseThrow(() -> {
            logger.warn(String.format("Employee with EID {%s} does not exist in DB.", employeeId));
            throw new EmployeeRecordException(String.format("Employee with EID {%s} does not exist in DB.", employeeId));

        });

        return employee.get();
    }

    @Override
    public List<EmployeeRecord> getEmployeesByBusiness(UUID businessId) {
        var business = registeredBusinessRepository.findById(businessId);
        business.orElseThrow(() -> {
            logger.warn(String.format("Business with ID {%s} does not exist in DB.", businessId));
            throw new RegisteredBusinessException(String.format("Business with ID {%s} does not exist in DB.", businessId));
        });

        return employeeRepository.findEmployeeRecordByBusiness(business.get());
    }
}
